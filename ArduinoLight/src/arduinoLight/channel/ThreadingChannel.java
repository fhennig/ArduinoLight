package arduinoLight.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import arduinoLight.interfaces.Refreshable;
import arduinoLight.interfaces.propertyListeners.ColorListener;
import arduinoLight.interfaces.propertyListeners.NameListener;
import arduinoLight.util.Color;

public class ThreadingChannel implements IChannel, Refreshable
{
	private Queue<Refreshable> _queue;
	private final int _id;
	
	private ThreadingChannelState _currentState = new ThreadingChannelState();
	private ThreadingChannelState _futureState = new ThreadingChannelState();
	private List<ColorListener> _colorListeners = new ArrayList<>();
	private List<NameListener> _nameListeners = new ArrayList<>();
	
	public ThreadingChannel(int id, Queue<Refreshable> refreshQueue)
	{
		_id = id;
		_queue = refreshQueue;
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
	
	@Override
	public String getName()
	{
		return _currentState.getName();
	}
	
	@Override
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
		_queue.add(this);
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
		raiseEvents(_currentState);
	}
	
	private void raiseEvents(ThreadingChannelState state)
	{
		if (state.getChangedProperties().contains("Color"))
			raiseColorChangedEvent();
		if (state.getChangedProperties().contains("Name"))
			raiseNameChangedEvent();
	}
	
	private void raiseColorChangedEvent()
	{
		for (ColorListener listener : _colorListeners)
			listener.colorChanged(this, getColor());
	}
	
	private void raiseNameChangedEvent()
	{
		for (NameListener listener : _nameListeners)
			listener.nameChanged(this, getName());
	}

	@Override
	public void addColorListener(ColorListener listener)
	{
		_colorListeners.add(listener);
	}

	@Override
	public void removeColorListener(ColorListener listener)
	{
		_colorListeners.remove(listener);
	}

	@Override
	public void addNameListener(NameListener listener)
	{
		_nameListeners.add(listener);
	}

	@Override
	public void removeNameListener(NameListener listener)
	{
		_nameListeners.remove(listener);		
	}
	
	@Override
	public int hashCode()
	{
		return _id;
	}
	
	@Override
	public String toString()
	{
		return getName() + " " + _id;
	}
}