package arduinoLight.channelprovider;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import arduinoLight.util.*;

/**
 * This class serves as a parent for concrete implementations of a Channelprovider.
 * It implements some basic functionality and defines some abstract methods that need to be implemented.
 * PropertyChangeSupport for: "Active", "Channels"
 * @author Felix
 */
public abstract class Channelprovider
{
	private boolean _active;
	private List<IChannel> _channels = new ArrayList<>();
	
	private List<ChannelsUpdatedListener> _listeners = new ArrayList<>();
	/** PropertyChangeSupport implemented to connect to the UI. */
	private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
	
	//--------------------------------------------------
	//TODO wo wird jetzt die anzahl der farben bestimmt?
	//--------------------------------------------------
	
	public boolean IsActive()
	{
		return _active;
	}
	
	/**
	 * Tries to activate the Colorprovider.
	 * Is abstract, so that Subclasses are forced to react.
	 * @return true, if the change succeeded, else false
	 */
	public abstract boolean activate();
	
	/**
	 * Tries to deactivate the Colorprovider.
	 * Is abstract, so that Subclasses are forced to react.
	 * @return true, if the change succeeded, else false
	 */
	public abstract boolean deactivate();
	
	/**
	 * Changes the active value and fires a PropertyChangeEvent for "Active".
	 */
	protected void setActive(boolean value)
	{
		boolean oldValue = _active;
		_active = value;
		_propertyChangeSupport.firePropertyChange("Active", oldValue, _active);
	}
	
	/**
	 * This method should be called after multiple changes to the colors took place, not after every single color change.
	 * Keep in mind that this event is likely to trigger transmission.
	 */
	protected void fireChannelsUpdatedEvent()
	{
		_propertyChangeSupport.firePropertyChange("Channels", null, _channels);
		
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
	
	
	//PropertyChangeSupport:
	public void addPropertyChangedListener(PropertyChangeListener listener)
	{
		_propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangedListener(PropertyChangeListener listener)
	{
		_propertyChangeSupport.removePropertyChangeListener(listener);
	}
}
