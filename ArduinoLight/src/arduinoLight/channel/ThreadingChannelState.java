package arduinoLight.channel;

import java.util.HashSet;
import java.util.Set;

import arduinoLight.util.Color;

/**
 * This class encapsules the state of a ProxyChannel.
 * It provides a way to keep track of which properties have been changed.
 * @author Felix
 *
 */
public class ThreadingChannelState
{
	private Set<String> _changedProperties = new HashSet<>();
	private Color _color = new Color();
	private String _name = "";
	
	
	
	public ThreadingChannelState() { };
	
	public ThreadingChannelState(ThreadingChannelState original)
	{
		this._changedProperties = original._changedProperties;
		this._color = original._color;
		this._name = original._name;
	}

	
	
	public Color getColor()
	{
		return _color;
	}

	public void setColor(Color color)
	{
		this._color = color;
		_changedProperties.add("Color");
	}

	public String getName()
	{
		return _name;
	}

	public void setName(String name)
	{
		this._name = name;
		_changedProperties.add("Name");
	}
	
	
	public boolean hasChanged()
	{
		return !_changedProperties.isEmpty();
	}
	
	
	public Set<String> getChangedProperties()
	{
		return new HashSet<String>(_changedProperties);
	}
	
	public void clearChangetrack()
	{
		_changedProperties.clear();
	}
}