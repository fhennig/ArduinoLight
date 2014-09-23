package arduinoLight.channelholder.ambientlight;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import arduinoLight.channel.Channel;
import arduinoLight.channelholder.ChannelsChangedListener;
import arduinoLight.channelholder.ChannelsChangedEventArgs;
import arduinoLight.channelholder.ModifiableChannelholder;
import arduinoLight.framework.Event;
import arduinoLight.framework.EventDispatchHandler;
import arduinoLight.framework.ShutdownHandler;
import arduinoLight.framework.ShutdownListener;
import arduinoLight.util.DebugConsole;
import arduinoLight.util.Util;

/**
 * This class contains channels and screen selections which are mapped to the channels.
 * If it is started, it refreshes the channel-colors periodically,
 * using the screen selection for each channel and and applying them to a taken screenshot. <br>
 * thread-safety: yes, though this class is not intended to be used concurrently.
 */
public class Ambientlight implements ModifiableChannelholder, ShutdownListener
{
	//TODO thread safety?
	public static final int MAX_REFRESHRATE = 100;
	private final Map<Channel, Areaselection> _map = new ConcurrentHashMap<Channel, Areaselection>();
	private ScheduledExecutorService _executor;
	private final List<ChannelsChangedListener> _channelholderListeners = new CopyOnWriteArrayList<>();
	private final List<ActiveListener> _activeListeners = new CopyOnWriteArrayList<>();
	private volatile boolean _active;

	
	
	/** Adds a new Channel with a default 2x1 Screenselection that has no selected Parts. */
	@Override
	public void addChannel(Channel channel)
	{
		addChannel(channel, new Areaselection(2, 1));
	}
	
	
	
	/** @param channel  a channel that is not already added */
	public void addChannel(Channel channel, Areaselection selection)
	{
		if (channel == null || selection == null)
			throw new IllegalArgumentException("A given argument was null.");
		if (_map.containsKey(channel))
			throw new IllegalArgumentException("The channel is already in use, cannot add it again!");
		
		_map.put(channel, selection);
		fireChannelsChangedEvent(new ChannelsChangedEventArgs(this, null, channel));
	}
	
	
	
	/** If the specified Channel is currently in use, it is removed. */
	@Override
	public void removeChannel(Channel channel)
	{
		Object value = _map.remove(channel);
		if (value != null)
			fireChannelsChangedEvent(new ChannelsChangedEventArgs(this, channel, null));
	}
	
	
	
	/**
	 * Returns the Screenselection assigned to the given Channel.
	 * If the given Channel is not used, null is returned.
	 */
	public Areaselection getScreenselection(Channel channel)
	{
		return _map.get(channel);
	}
	
	
	
	/**
	 * Starts the periodic recalculation of the Channel Colors.
	 * @param refreshRate  the frequency of refreshing in Hz.
	 * @throws SecurityException  if it is not permitted to take a screenshot. 
	 */
	public synchronized void start(int refreshRate)
	{
		if (_active)
			throw new IllegalStateException("Already active!");
		
		ScreenshotHelper.checkScreenshotPermission();
		
		Runnable colorSetLoop = new Runnable() //TODO add possiblity to interrupt
		{
			private ColorSetter _setter = new ColorSetter(_map);
			public void run()
			{
				_setter.setColors(ScreenshotHelper.getBufferedImageScreenshot());
//				try
//				{
//					Image screenshot = null;
//					try {
//						//TODO somewhere before, check if permission to take screenshot is given
//						screenshot = ScreenshotHelper.getScreenshot();
//					} catch (Exception e) { e.printStackTrace();}
//					AverageColorGetter avgGetter = new AverageColorGetter(screenshot);
//					Iterator<Channel> channels = _map.keySet().iterator();
//					while (channels.hasNext())
//					{
//						Channel channel;
//						Areaselection selection;
//						synchronized (_map)
//						{
//							channel = channels.next();
//							selection = _map.get(channel);
//						}
//						channel.setColor(avgGetter.getAverageColor(selection));
//					}
//				}
//				catch (Exception e)
//				{
//					synchronized (Ambientlight.this)
//					{
//						_active = false;
//						fireActiveChangedEvent(_active);
//					}
//				}
			}
		};
		refreshRate = Math.min(refreshRate, MAX_REFRESHRATE);
		long period = Util.getPeriod(refreshRate);
		_executor = Executors.newSingleThreadScheduledExecutor();
		_executor.scheduleAtFixedRate(colorSetLoop, 0, period, TimeUnit.NANOSECONDS);
		_active = true;
		DebugConsole.print("Ambientlight", "start", "starting successful");
		ShutdownHandler.getInstance().addShutdownListener(this);
		fireActiveChangedEvent(_active);
	}
	
	
	
	/**
	 * Stops the periodic recalculation of the Channel Colors, if it is active.
	 */
	public synchronized void stop()
	{
		if (!_active)
			return;
		_executor.shutdown();
		_executor = null;
		_active = false;
		DebugConsole.print("Ambientlight", "stop", "stopping successful");
		ShutdownHandler.getInstance().removeShutdownListener(this);
		fireActiveChangedEvent(_active);
	}
	
	
	
	public boolean isActive()
	{
		return _active;
	}

	
	
	/** @see arduinoLight.channelwriter.Channelwriter#getChannels() */
	@Override
	public Set<Channel> getChannels()
	{
		return Collections.unmodifiableSet(_map.keySet());
	}

	
	
	@Override
	public String getChannelsDescription()
	{
		return "Ambientlight Channels";
	}
	
	
	
	/** Event is fired concurrently */
	private void fireActiveChangedEvent(final boolean newActive)
	{
		EventDispatchHandler.getInstance().dispatch(new Event(this, "ActiveChanged")
		{
			@Override
			public void notifyListeners()
			{
				for (ActiveListener l : _activeListeners)
					l.activeChanged(Ambientlight.this, newActive);
			}
		});
	}
	
	
	
	/** Event is not fired concurrently */
	private void fireChannelsChangedEvent(ChannelsChangedEventArgs e)
	{
		for (ChannelsChangedListener l : _channelholderListeners)
			l.channelsChanged(e);
	}

	
	
	//---------- Event-subscribing -----------------------------
	@Override
	public void addChannelsChangedListener(ChannelsChangedListener listener)
	{
		_channelholderListeners.add(listener);		
	}

	@Override
	public void removeChannelsChangedListener(ChannelsChangedListener listener)
	{
		_channelholderListeners.add(listener);
	}
	
	public void addActiveListener(ActiveListener listener)
	{
		_activeListeners.add(listener);
	}
	
	public void removeActiveListener(ActiveListener listener)
	{
		_activeListeners.remove(listener);
	}
	
	//---------- Shutdown --------------------------------------
	@Override
	public void onShutdown()
	{
		stop();
	}
	
	//----------------------------------------------------------
	@Override
	public String toString()
	{
		return "Ambientlight";
	}
}
