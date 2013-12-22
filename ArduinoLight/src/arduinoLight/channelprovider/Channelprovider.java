package arduinoLight.channelprovider;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.util.*;

/**
 * This class serves as a parent for concrete implementations of a Channelprovider.
 * It implements some basic functionality and defines some abstract methods that need to be implemented.
 * @author Felix
 */
public abstract class Channelprovider
{
	private boolean _active;
	protected List<IChannel> _channels = new ArrayList<>();
	
	private List<ChannelproviderListener> _listeners = new ArrayList<>();
		
	public void addChannel()
	{
		//TODO currently the channel object is created here and no name is given. think about if this is good (probably not).
		_channels.add(new Channel());
		fireChannelsChangedEvent();
	}
	
	public void removeChannel(IChannel channel)
	{
		_channels.remove(channel);
		fireChannelsChangedEvent();
	}
	
	public List<IChannel> getChannels()
	{
		return _channels;
	}
	
	public boolean IsActive()
	{
		return _active;
	}
	
	/**
	 * Tries to activate the Colorprovider.
	 * Is abstract, so that Subclasses are forced to react.
	 * @return true, if the change succeeded, else false
	 */
	protected abstract boolean activate();
	
	/**
	 * Tries to deactivate the Colorprovider.
	 * Is abstract, so that Subclasses are forced to react.
	 * @return true, if the change succeeded, else false
	 */
	protected abstract boolean deactivate();
	
	/**
	 * Tries to change the active Value and returns true if setting it succeeded.
	 */
	public boolean setActive(boolean value)
	{
		boolean successfull = false;
		
		if (value != _active)
		{
			if (value == true)
			{
				successfull = activate();
			}
			else
			{
				successfull = deactivate();
			}
		}
		
		if (!successfull)
			return false;
		
		_active = value;
		fireActiveChangedEvent();
		
		if (IsActive())
			fireChannelcolorsUpdatedEvent();
		
		return true;
	}
		
	private void fireActiveChangedEvent()
	{
		for (ChannelproviderListener l : _listeners)
		{
			l.activeStateChanged(this, _active);
		}
	}
	
	/**
	 * This method should be called after multiple changes to the colors took place, not after every single color change.
	 * Keep in mind that this event is likely to trigger transmission.
	 */
	protected void fireChannelcolorsUpdatedEvent()
	{
		for (ChannelproviderListener l : _listeners)
		{
			l.channelcolorsUpdated(this, _channels);
		}
	}
	
	/**
	 * This method notifies every listener, that a channel was added or removed.
	 */
	private void fireChannelsChangedEvent()
	{
		for (ChannelproviderListener l : _listeners)
		{
			l.channelsChanged(this, _channels);
		}
	}
	
	public void addChannelproviderListener(ChannelproviderListener listener)
	{
		_listeners.add(listener);
	}
	
	public void removeChannelproviderListener(ChannelproviderListener listener)
	{
		_listeners.remove(listener);
		//TODO add possible feature: set deactive this colorprovider if no listeners are subscribed
	}
}
