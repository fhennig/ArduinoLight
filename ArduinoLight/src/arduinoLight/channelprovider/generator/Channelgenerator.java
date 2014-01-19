package arduinoLight.channelprovider.generator;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.channel.IChannel;
import arduinoLight.channelprovider.ChannelFactory;
import arduinoLight.channelprovider.ChannelcolorsListener;
import arduinoLight.channelprovider.ChannelcompositionListener;
import arduinoLight.channelprovider.ChannellistProvider;
import arduinoLight.channelprovider.Channelprovider;
import arduinoLight.interfaces.Activatable;
import arduinoLight.interfaces.propertyListeners.ActiveListener;
import arduinoLight.util.*;

/**
 * This class serves as a parent for concrete implementations of a Channelprovider.
 * It implements some basic functionality and defines some abstract methods that need to be implemented.
 * @author Felix
 */
public abstract class Channelgenerator extends Channelprovider implements ChannellistProvider, Activatable
{

	private boolean _active;
	
	private List<ChannelcompositionListener> _channellistListeners = new ArrayList<>();
	private List<ActiveListener> _activeStateListeners = new ArrayList<>();
	private List<ChannelcolorsListener> _channelcolorsListeners = new ArrayList<>();
		
	public void addChannel()
	{
		//TODO currently the channel object is created here and no name is given. think about if this is good (probably not).
		_channels.add(ChannelFactory.getNewChannel());
		fireChannelsChangedEvent();
	}
	
	public void removeChannel(IChannel channel)
	{
		if (!_channels.contains(channel))
		{
			DebugConsole.print("Channelprovider", "removeChannel", "The Channel that should be removed does not exist.");
			throw new IllegalArgumentException("The Channel that should be removed does not exist.");
		}
		
		//Set Channel to Black, notify the listeners, then remove the channel:
		channel.setColor(new Color(0, 0, 0, 0));
		fireChannelcolorsUpdatedEvent();
		
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
	 * Tries to change the active Value and returns true if setting it succeeded.
	 */
	@Override
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
	 * Notifies listeners that the active state of this channelprovider has changed.
	 */
	private void fireActiveChangedEvent()
	{
		for (ActiveListener l : _activeStateListeners)
		{
			l.activeChanged(this, _active);
		}
	}
	
	/**
	 * This method notifies every listener, that a channel was added or removed.
	 */
	private void fireChannelsChangedEvent()
	{
		for (ChannelcompositionListener l : _channellistListeners)
		{
			l.channellistChanged(this, _channels);
		}
	}
	
	/**
	 * This method should be called after multiple changes to the colors took place, not after every single color change.
	 * Keep in mind that this event is likely to trigger transmission.
	 */
	protected void fireChannelcolorsUpdatedEvent()
	{
		for (ChannelcolorsListener l : _channelcolorsListeners)
		{
			l.channelcolorsUpdated(this, _channels);
		}
	}
	
	public void addChannelcolorsListener(ChannelcolorsListener listener)
	{
		_channelcolorsListeners.add(listener);
	}
	
	public void removeChannelcolorsListener(ChannelcolorsListener listener)
	{
		_channelcolorsListeners.remove(listener);
	}
	
	@Override
	public void addChannellistListener(ChannelcompositionListener listener)
	{
		_channellistListeners.add(listener);
	}
	
	@Override
	public void removeChannellistListener(ChannelcompositionListener listener)
	{
		_channellistListeners.remove(listener);
	}

	@Override
	public void addActiveStateListener(ActiveListener listener)
	{
		_activeStateListeners.add(listener);
	}

	@Override
	public void removeActiveStateListener(ActiveListener listener)
	{
		_activeStateListeners.remove(listener);
	}
}
