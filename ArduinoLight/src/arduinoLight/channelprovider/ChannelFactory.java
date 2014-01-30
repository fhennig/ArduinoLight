package arduinoLight.channelprovider;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.channel.IChannel;
import arduinoLight.channel.ThreadingChannel;

/**
 * This class is a Singleton.
 * This has also some kind of "blackboard" functionality (Needs a lot of work).
 * Could probably be a singleton (Or should we be able to have multiple factories? Think of the ids!). TODO
 * threadsafety-policy: make all methods 'synchronized'. Simple and safe.
 */
public class ChannelFactory
{
	private static ChannelFactory _instance = null;
	
	/** Used to generate IDs */
    private int _instances = 0;
	private List<IChannel> _registeredChannels = new ArrayList<>();
	
	/** private constructor because Singleton */
	private ChannelFactory() { }
	
	/** typical Singleton.getInstance() method */
	public static ChannelFactory getInstance()
	{
		if(_instance == null)
			_instance = new ChannelFactory();
		
		return _instance;
	}
	
	public synchronized IChannel getChannel()
	{
		IChannel newChannel = new ThreadingChannel(_instances);
		_instances++;
		_registeredChannels.add(newChannel);
		return newChannel;
	}
	
	//TODO where is this used, why is this used, how is this used?
	//TODO look at _registeredChannels...
	public synchronized IChannel getChannel(int id)
	{
		for (IChannel channel : _registeredChannels)
		{
			if (channel.getId() == id)
				return channel;
		}
		
		return null;
	}
}
