package arduinoLight.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import arduinoLight.channel.Channel;
import arduinoLight.channel.ThreadingChannel;
import arduinoLight.channelholder.Channelholder;
import arduinoLight.channelholder.ChannelholderListener;
import arduinoLight.channelholder.ChannelsChangedEventArgs;
import arduinoLight.events.Event;
import arduinoLight.events.EventDispatchHandler;

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
	private List<ChannelholderListener> _listeners = new CopyOnWriteArrayList<>();
	
	public ChannelFactory()
	{
		
	}
	
	public synchronized Channel newChannel()
	{
		return newChannel(null);
	}
	
	public synchronized Channel newChannel(String name)
	{
		Channel newChannel = new ThreadingChannel(_instances);
		_instances++;
		_createdChannels.add(newChannel);
		if (name != null)
			newChannel.setName(name);
		
		fireChannelsChangedEvent(newChannel); //fires concurrently
		return newChannel;
	}

	/**
	 * @see arduinoLight.channelholder.Channelholder#getChannels()
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

	@Override
	public String getChannelsDescription()
	{
		return "All Channels";
	}
	
	/** concurrent event-firing */
	private void fireChannelsChangedEvent(Channel addedChannel)
	{
		final ChannelsChangedEventArgs e = new ChannelsChangedEventArgs(this, null, addedChannel);
		EventDispatchHandler.getInstance().dispatch(new Event(this, "ChannelsChanged")
		{
			@Override
			public void notifyListeners()
			{
				for (ChannelholderListener l : _listeners)
					l.channelsChanged(e);
			}
		});
	}

	@Override
	public void addChannelholderListener(ChannelholderListener listener)
	{
		_listeners.add(listener);
	}

	@Override
	public void removeChannelholderListener(ChannelholderListener listener)
	{
		_listeners.remove(listener);
	}
}
