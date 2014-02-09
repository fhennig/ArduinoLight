package arduinoLight.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import arduinoLight.channel.Channel;
import arduinoLight.channel.ThreadingChannel;
import arduinoLight.channelwriter.Channelholder;

/**
 * This class is a Singleton. //TODO UPDATE
 * This has also some kind of "blackboard" functionality (Needs a lot of work).
 * Could probably be a singleton (Or should we be able to have multiple factories? Think of the ids!). TODO
 * threadsafety-policy: make all methods 'synchronized'. Simple and safe.
 */
public class ChannelFactory implements Channelholder
{
	//TODO thread safety?
	/** Used to generate IDs */
    private static int _instances = 0;
	private Set<Channel> _createdChannels = new HashSet<>();
	
	public ChannelFactory()
	{
		
	}
	
	public synchronized Channel newChannel()
	{
		Channel newChannel = new ThreadingChannel(_instances);
		_instances++;
		_createdChannels.add(newChannel);
		return newChannel;
	}

	/**
	 * @see arduinoLight.channelwriter.Channelholder#getChannels()
	 */
	@Override
	public Set<Channel> getChannels()
	{
		return Collections.unmodifiableSet(_createdChannels);
	}
	
	@Override
	public String toString()
	{
		return "ChannelFactory";
	}
}
