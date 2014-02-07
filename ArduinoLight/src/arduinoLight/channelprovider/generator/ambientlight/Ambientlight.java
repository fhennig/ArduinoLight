package arduinoLight.channelprovider.generator.ambientlight;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import arduinoLight.channel.IChannel;
import arduinoLight.channelwriter.Channelholder;
import arduinoLight.model.Model;
import arduinoLight.util.Color;
import arduinoLight.util.Util;

public class Ambientlight implements Channelholder
{
	//TODO thread safety?
	private static final int MAX_FREQUENCY = 100;
	private Map<IChannel, Screenselection> _map = new ConcurrentHashMap<IChannel, Screenselection>();
	private ScheduledExecutorService _executor;
	private ScreenshotGetter _screenGetter = new ScreenshotGetter();

	public void addChannel()
	{
		IChannel newChannel = Model.getInstance().getChannelFactory().newChannel();
		Screenselection selection = new Screenselection(2, 2);
		_map.put(newChannel, selection);
	}
	
	public void removeChannel(IChannel channel)
	{
		_map.remove(channel);
	}
	
	/**
	 * Returns the Screenselection assigned to that Channel.
	 * If this class does not use the given Channel, null is returned.
	 */
	public Screenselection getScreenselection(IChannel channel)
	{
		return _map.get(channel);
	}
	
	public synchronized void start(int frequency)
	{
		_executor = Executors.newSingleThreadScheduledExecutor();
		long period = Util.getPeriod(frequency, MAX_FREQUENCY);
		Runnable colorSetLoop = new Runnable()
		{
			public void run()
			{
				Color[][] screenshot = _screenGetter.getScreenshot();
				Iterator<IChannel> map = _map.keySet().iterator();
				while (map.hasNext())
				{
					IChannel channel;
					Screenselection selection;
					synchronized (_map)
					{
						channel = map.next();
						selection = _map.get(channel);
					}
					channel.setColor(_screenGetter.getAverageColor(screenshot, selection));
				}
			}
		};
		_executor.scheduleAtFixedRate(colorSetLoop, 0, period, TimeUnit.NANOSECONDS);
	}
	
	public synchronized void stop()
	{
		_executor.shutdown();
		_executor = null;
	}

	/** @see arduinoLight.channelwriter.Channelwriter#getChannels() */
	@Override
	public Set<IChannel> getChannels()
	{
		return Collections.unmodifiableSet(_map.keySet());
	}
}
