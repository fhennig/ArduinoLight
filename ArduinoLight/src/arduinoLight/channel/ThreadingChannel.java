package arduinoLight.channel;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.events.Event;
import arduinoLight.events.EventDispatchHandler;
import arduinoLight.interfaces.propertyListeners.ColorListener;
import arduinoLight.interfaces.propertyListeners.NameListener;
import arduinoLight.util.Color;

/**
 * This implementation of the IChannel interface uses a queue in a special thread to fire its events.
 * This way, the swing-thread is blocked for shorter amount of times.
 * @author Felix
 */
public class ThreadingChannel implements IChannel
{
	/** _id needs to be unique, it is used for the hashCode */
	private final int _id;
	private Color _color = new Color();
	private String _name = "Channel";
	
	private List<ColorListener> _colorListeners = new ArrayList<>();
	private List<NameListener> _nameListeners = new ArrayList<>();
	
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
		raiseColorChangedEvent();
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
		raiseNameChangedEvent();
	}

	@Override
	public int getId()
	{
		return _id;
	}
	
	//---------- Events ----------------------------------------
	private void raiseColorChangedEvent()
	{
		final Color color = _color; //Get final reference to the current color, to use in anonymous class
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
	
	private void raiseNameChangedEvent()
	{
		final String name = _name;
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
		return getName() + "[" + _id + "]";
	}
}