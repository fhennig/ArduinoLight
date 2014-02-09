package arduinoLight.channelprovider;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.channel.Channel;
import arduinoLight.interfaces.propertyListeners.ChannelsListener;

public abstract class Channelprovider
{
	protected final List<Channel> _channels = new ArrayList<Channel>();
	private final List<ChannelsListener> _channelsListeners = new ArrayList<ChannelsListener>();
	
	public List<Channel> getChannels()
	{
		return new ArrayList<Channel>(_channels);
	}
	
	//---------- Event-Firing ----------------------------------
	/**
	 * This method should be called after multiple changes to the colors took place, not after every single color change.
	 * Keep in mind that this event is likely to trigger transmission.
	 */
	protected void fireChannelsUpdatedEvent()
	{
		for (ChannelsListener l : _channelsListeners)
		{
			l.channelsChanged(this, new ArrayList<Channel>(_channels));
		}
	}
	
	//---------- Event-subscribing-methods----------------------
	public void addChannelcolorsListener(ChannelsListener listener)
	{
		_channelsListeners.add(listener);
	}
	
	public void removeChannelcolorsListener(ChannelsListener listener)
	{
		_channelsListeners.remove(listener);
	}
}
