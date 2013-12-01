package colorprovider;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import util.IRGBColor;

public class Colorprovider
{
	protected List<IRGBColor> _colors;
	protected PropertyChangeSupport _propChangeSupport;
	
	public Colorprovider()
	{
		_colors = new ArrayList<>();
		_propChangeSupport = new PropertyChangeSupport(this);
	}
	
	public List<IRGBColor> getColors()
	{
		return _colors;
	}
	
	public IRGBColor getColor(int index)
	{
		IRGBColor color;
		color = _colors.get(index);
		if (color == null)
		{
			throw new IndexOutOfBoundsException();
		}
		return color;
	}
	
	protected void raiseColorsChangedEvent()
	{
		
		_propChangeSupport.firePropertyChange("Colors", null, _colors);
	}
	
	protected void raiseColorChangedEvent(int color)
	{
		_propChangeSupport.fireIndexedPropertyChange("Color", color, null, _colors.get(color));
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		this._propChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		this._propChangeSupport.removePropertyChangeListener(listener);
	}
}
