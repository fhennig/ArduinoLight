package arduinoLight.channelprovider.generator.ambientlight;

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
import arduinoLight.channelholder.ChannelholderListener;
import arduinoLight.channelholder.ChannelsChangedEventArgs;
import arduinoLight.channelholder.ModifiableChannelholder;
import arduinoLight.util.DebugConsole;
import arduinoLight.util.Util;

/**
 * This class contains channels and screen selections.
 * If it is started, it refreshes the channel-colors periodically,
 * using the screen selection for each channel and and applying them to a taken screenshot. 
 */
public class Ambientlight implements ModifiableChannelholder
{
	//TODO thread safety?
	public static final int MAX_FREQUENCY = 100;
	private final Map<Channel, Areaselection> _map = new ConcurrentHashMap<Channel, Areaselection>();
	private ScheduledExecutorService _executor;
	private final List<ChannelholderListener> _listeners = new CopyOnWriteArrayList<>();

	/** Adds a new Channel with a default 2x2 Screenselection that has no selected Parts. */
	@Override
	public void addChannel(Channel channel)
	{
		addChannel(channel, new Areaselection(2, 2));
	}
	
	public void addChannel(Channel channel, Areaselection selection)
	{
		if (channel == null || selection == null)
			throw new IllegalArgumentException("A given argument was null.");
		if (_map.containsKey(channel))
			throw new IllegalArgumentException("The channel is already in use, cannot add it again!");
		
		_map.put(channel, selection);
		fireChannelsChangedEvent(new ChannelsChangedEventArgs(this, null, channel));
	}
	
	/** If the specified Channel is currently in use, it is removed */
	@Override
	public void removeChannel(Channel channel)
	{
		Object value = _map.remove(channel);
		if (value != null)
			fireChannelsChangedEvent(new ChannelsChangedEventArgs(this, channel, null));
	}
	
	/**
	 * Returns the Screenselection assigned to that Channel.
	 * If this class does not use the given Channel, null is returned.
	 */
	public Areaselection getScreenselection(Channel channel)
	{
		return _map.get(channel);
	}
	
	public synchronized void start(int frequency)
	{
		Runnable colorSetLoop = new Runnable()
		{
			public void run()
			{
				try
				{
					DebugConsole.print("Ambientlight", "colorsetloop", "Taking screenshot");
					Image screenshot = null;
					try {
						screenshot = ScreenshotHelper.getScreenshot();
					} catch (Exception e) { e.printStackTrace();}
					DebugConsole.print("Ambientlight", "colorsetloop", "Screenshot taken");
					AverageColorGetter avgGetter = new AverageColorGetter(screenshot);
					Iterator<Channel> channels = _map.keySet().iterator();
					while (channels.hasNext())
					{
						Channel channel;
						Areaselection selection;
						synchronized (_map)
						{
							channel = channels.next();
							selection = _map.get(channel);
						}
						channel.setColor(avgGetter.getAverageColor(selection));
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		long period = Util.getPeriod(frequency, MAX_FREQUENCY);
		_executor = Executors.newSingleThreadScheduledExecutor();
		_executor.scheduleAtFixedRate(colorSetLoop, 0, period, TimeUnit.NANOSECONDS);
	}
	
	public synchronized void stop()
	{
		_executor.shutdown();
		_executor = null;
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
		return "Ambientlight-Channels";
	}
	
	private void fireChannelsChangedEvent(ChannelsChangedEventArgs e)
	{
		for (ChannelholderListener l : _listeners)
			l.channelsChanged(e);
	}

	@Override
	public void addChannelholderListener(ChannelholderListener listener)
	{
		_listeners.add(listener);		
	}

	@Override
	public void removeChannelholderListener(ChannelholderListener listener)
	{
		_listeners.add(listener);
	}
}
