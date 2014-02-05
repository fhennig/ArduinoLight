package arduinoLight.channel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import arduinoLight.events.Event;
import arduinoLight.events.EventDispatchHandler;
import arduinoLight.interfaces.propertyListeners.ColorListener;
import arduinoLight.interfaces.propertyListeners.NameListener;
import arduinoLight.util.Color;

/**
 * This class is threadsafe!
 * This implementation of the IChannel interface delegates Event firing to a different Thread.
 * This way, the swing-thread is blocked for shorter amount of times.
 */
public class ThreadingChannel implements IChannel
{
	/** unique id, used for hashCode. Is threadsafe because final and primitive */
	private final int _id;
	
	/** immutable objects + volatile used to ensure visibility of changes across all threads */
	private volatile Color _color = new Color();
	private volatile String _name = "Channel";
	
	/** final CopyOnWriteArrayList used for save concurrent access / thread-safety */
	private final List<ColorListener> _colorListeners = new CopyOnWriteArrayList<>();
	private final List<NameListener> _nameListeners = new CopyOnWriteArrayList<>();
	
	/**
	 * @param id  a unique integer.
	 */
	public ThreadingChannel(int id)
	{
		_id = id;
	}
	
	//---------- IChannel: Getters / Setters -------------------
	@Override
	public Color getColor()
	{
		return _color;
	}

	@Override
	public void setColor(Color color)
	{
		_color = color;
		raiseColorChangedEvent(color);
	}
	
	@Override
	public String getName()
	{
		return _name;
	}
	
	@Override
	public void setName(String name)
	{
		_name = name;
		raiseNameChangedEvent(name);
	}

	@Override
	public int getId()
	{
		return _id;
	}
	
	//---------- Events ----------------------------------------
	/**
	 * Color needs to get passed in as a parameter, to ensure that the correct color
	 * is sent to the listeners.
	 */
	private void raiseColorChangedEvent(final Color color)
	{
		EventDispatchHandler.getInstance().dispatch(new Event(this, "ColorChanged")
		{
			@Override
			public void notifyListeners()
			{
				for (ColorListener listener : _colorListeners)
					listener.colorChanged(this, color);
			}
		});
	}
	
	private void raiseNameChangedEvent(final String name)
	{
		EventDispatchHandler.getInstance().dispatch(new Event(this, "NameChanged")
		{
			@Override
			public void notifyListeners()
			{
				for (NameListener listener : _nameListeners)
					listener.nameChanged(this, name);
			}
		});
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
	
	//---------- overridden from object ------------------------
	@Override
	public int hashCode()
	{
		return _id * 57;
	}
	
	@Override
	public String toString()
	{
		return _name + "[" + _id + "]";
	}
}