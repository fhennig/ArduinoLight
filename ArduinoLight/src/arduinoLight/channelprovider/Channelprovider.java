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
	
	private List<ChannelsUpdatedListener> _listeners = new ArrayList<>();
	
	//--------------------------------------------------
	//TODO wo wird jetzt die anzahl der farben bestimmt?
	//--------------------------------------------------
	
	public void addChannel(IChannel channel)
	{
		_channels.add(channel);
	}
	
	public void removeChannel(IChannel channel)
	{
		_channels.remove(channel);
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
		//TODO probably add event to show that active state changed.
		return true;
	}
	
	/**
	 * This method should be called after multiple changes to the colors took place, not after every single color change.
	 * Keep in mind that this event is likely to trigger transmission.
	 */
	protected void fireChannelsUpdatedEvent()
	{
		for (ChannelsUpdatedListener l : _listeners)
		{
			l.channelsUpdated(_channels);
		}
	}
	
	public void addChannelsUpdatedListener(ChannelsUpdatedListener listener)
	{
		_listeners.add(listener);
	}
	
	public void removeChannelsUpdatedListener(ChannelsUpdatedListener listener)
	{
		_listeners.remove(listener);
		//TODO add possible feature: set deactive this colorprovider if no listeners are subscribed
	}
}
