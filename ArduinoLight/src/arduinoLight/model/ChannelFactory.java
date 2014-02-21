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
 * Can be used to get new Channel objects.
 * Stores all created Channels internally.
 * threadsafety-policy: make most methods 'synchronized' and delegate to thread-safe objects (CopyOnWriteArrayList).
 * Is thread-safe (2014-02-21)
 */
public class ChannelFactory implements Channelholder
{
	/** Used to generate IDs */
    private static int _instances = 0;
	private Set<Channel> _createdChannels = new HashSet<>();
	private List<ChannelholderListener> _listeners = new CopyOnWriteArrayList<>();
	

	
	public synchronized Channel newChannel()
	{
		return newChannel(null);
	}
	
	public synchronized Channel newChannel(String name)
	{
		//The synchronization here may be too much, but performance is not an issue.
		Channel newChannel = new ThreadingChannel(_instances);
		_instances++;
		_createdChannels.add(newChannel);
		if (name != null)
			newChannel.setName(name);
		
		fireChannelsChangedEvent(newChannel); //fires concurrently
		return newChannel;
	}
	

	//---------- Channelholder-Interface -----------------------
	/** @see arduinoLight.channelholder.Channelholder#getChannels() */
	@Override
	public Set<Channel> getChannels()
	{
		return Collections.unmodifiableSet(_createdChannels);
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
	public String getChannelsDescription()
	{
		return "All Channels";
		//TODO this is not all channels, just the channels of this factory.
		//	   add a channelcontainer in the model that holds all the channels
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
	
	//---------- overridden from object ------------------------
	@Override
	public String toString()
	{
		return "ChannelFactory";
	}
}
