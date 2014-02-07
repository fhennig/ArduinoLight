package arduinoLight.channelprovider.generator.ambientlight;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

import arduinoLight.arduino.amblone.AmblonePackage;
import arduinoLight.channel.IChannel;
import arduinoLight.channelwriter.Channelholder;
import arduinoLight.model.Model;
import arduinoLight.util.Util;

public class Ambientlight implements Channelholder, ScreenselectionListener
{
	private static final int MAX_FREQUENCY = 100;
	private Map<IChannel, Screenselection> _map = new ConcurrentHashMap<IChannel, Screenselection>();
	private ScheduledExecutorService _executor;

	/** @see arduinoLight.channelwriter.Channelwriter#getChannels() */
	@Override
	public Set<IChannel> getChannels()
	{
		return Collections.unmodifiableSet(_map.keySet());
	}

	public void addChannel()
	{
		IChannel newChannel = Model.getInstance().getChannelFactory().newChannel();
		Screenselection selection = new Screenselection(2, 2);
		_map.put(newChannel, selection);
		selection.addListener(this);
	}
	
	@Override
	public void changed(Screenselection source)
	{
		// TODO why did I implement that?
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
				// TODO
			}
		};
		_executor.scheduleAtFixedRate(colorSetLoop, 0, period, TimeUnit.NANOSECONDS);
	}
	
	
	//TODO add thread
}
