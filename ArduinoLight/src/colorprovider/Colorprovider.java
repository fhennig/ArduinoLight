package colorprovider;

import java.util.ArrayList;
import java.util.List;

import util.IRGBColor;

/**
 * This class serves as a parent for concrete implementations of a colorprovider.
 * It already implements events that should be raised by any concrete implementation 
 * @author Felix
 */
public abstract class Colorprovider
{
	protected List<IRGBColor> _colors;
	protected List<ColorChangedListener> _colorChangedListeners;
	protected List<ColorsChangedListener> _colorsChangedListeners;
	
	public Colorprovider()
	{
		_colors = new ArrayList<>();
		_colorChangedListeners = new ArrayList<>(); 
		_colorsChangedListeners = new ArrayList<>();
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
	
	/**
	 * This method should be called after a color was changed (And you wish to let other objects know).
	 * @param index The index of the color in the _colors-List.
	 */
	protected void raiseColorChangedEvent(int index)
	{
		for (ColorChangedListener l : _colorChangedListeners)
		{
			l.colorChanged(index);
		}
	}
	
	/**
	 * This method should be called after multiple changes to the colors took place, not after every single color change.
	 * Keep in mind that these events will trigger transmission and therefore should not be raised unnecessary often.
	 */
	protected void raiseColorsChangedEvent()
	{
		for (ColorsChangedListener l : _colorsChangedListeners)
		{
			l.colorsChanged();
		}
	}
	
	public void addColorChangedListener(ColorChangedListener listener)
	{
		_colorChangedListeners.add(listener);
	}
	
	public void addColorsChangedListener(ColorsChangedListener listener)
	{
		_colorsChangedListeners.add(listener);
	}
}