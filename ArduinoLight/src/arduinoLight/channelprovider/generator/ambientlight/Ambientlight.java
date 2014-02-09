package arduinoLight.channelprovider.generator.ambientlight;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import arduinoLight.channel.Channel;
import arduinoLight.channelwriter.ModifiableChannelholder;
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
	private static final int MAX_FREQUENCY = 100;
	private Map<Channel, Areaselection> _map = new ConcurrentHashMap<Channel, Areaselection>();
	private ScheduledExecutorService _executor;

	/** Adds a new Channel with a default 2x2 Screenselection that has no selected Parts. */
	@Override
	public void addChannel(Channel channel)
	{
		addChannel(channel, new Areaselection(2, 2));
	}
	
	public void addChannel(Channel channel, Areaselection selection)
	{
		if (channel == null || selection == null)
			throw new IllegalArgumentException();
		_map.put(channel, selection);
	}
	
	/** If the specified Channel is currently in use, it is removed */
	@Override
	public void removeChannel(Channel channel)
	{
		_map.remove(channel);
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
					Iterator<Channel> map = _map.keySet().iterator();
					while (map.hasNext())
					{
						Channel channel;
						Areaselection selection;
						synchronized (_map)
						{
							channel = map.next();
							selection = _map.get(channel);
						}
						channel.setColor(screenshot.getAverageColor(selection));
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
}
