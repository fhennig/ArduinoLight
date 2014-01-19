package arduinoLight.channel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import arduinoLight.interfaces.propertyListeners.PropertiesListener;
import arduinoLight.threading.Refreshable;
import arduinoLight.threading.Refreshqueue;
import arduinoLight.util.Color;

public class ThreadingChannel implements IChannel, Refreshable
{
	private static final Refreshqueue _queue = new Refreshqueue();
	private static final AtomicInteger _instances = new AtomicInteger(0); //Used to generate Ids.
	
	private final int _id = _instances.getAndIncrement();
	
	private ThreadingChannelState _currentState = new ThreadingChannelState();
	private ThreadingChannelState _futureState = new ThreadingChannelState();
	private List<PropertiesListener> _changeListeners;
	
	public ThreadingChannel()
	{
		this(new Color());
	}
	
	public ThreadingChannel(Color color)
	{
		setColor(color);
	}
	
	@Override
	public Color getColor()
	{
		return _currentState.getColor();
	}

	@Override
	public void setColor(Color color)
	{
		_futureState.setColor(color);
		queue();
	}
	
	public String getName()
	{
		return _currentState.getName();
	}
	
	public void setName(String name)
	{
		_futureState.setName(name);
		queue();
	}

	@Override
	public int getId()
	{
		return _id;
	}
	
	private void queue()
	{
		_queue.queueItem(this);
	}
	
	/**
	 * This method applies every change made to the current state.
	 * This leads to event-firing.
	 * This method is intended to get called from a special change-handling-thread.
	 */
	public void refresh()
	{
		if (!_futureState.hasChanged())
			return; //If nothing changed, we do nothing.
		
		synchronized (_futureState) //Copying and afterward cleaning the futureState is an atomic action.
		{
			_currentState = new ThreadingChannelState(_futureState);
			_futureState.clearChangetrack();
		}
		raisePropertiesChangedEvent(_currentState);
	}
	
	private void raisePropertiesChangedEvent(ThreadingChannelState newState)
	{
		for (PropertiesListener listener : _changeListeners)
		{
			listener.propertiesChanged(newState.getChangedProperties());
		}
	}
	
	public void addPropertiesChangedListener(PropertiesListener listener)
	{
		_changeListeners.add(listener);
	}
	
	public void removePropertiesChangedListener(PropertiesListener listener)
	{
		_changeListeners.remove(listener);
	}
	
}