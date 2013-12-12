package arduinoLight.colorprovider;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.util.IRGBColor;

/**
 * This class serves as a parent for concrete implementations of a colorprovider.
 * It already implements events that should be raised by any concrete implementation 
 * @author Felix
 */
public abstract class Colorprovider
{
	protected boolean _active;
	protected List<IRGBColor> _colors;
	protected List<ColorsUpdatedListener> _colorsUpdatedListeners;
	//TODO wo wird jetzt die anzahl der farben bestimmt?
	
	public Colorprovider()
	{
		_colors = new ArrayList<>();
		_colorsUpdatedListeners = new ArrayList<>();
	}
	
	public boolean IsActive()
	{
		return _active;
	}
	
	public void setActive(boolean newActiveState)
	{
		_active = newActiveState;
	}
	
	public List<IRGBColor> getColors()
	{
		return _colors;
	}
	
	/* Currently not neccessary; commented out for clarity
	public int getChannelCount()
	{
		return _colors.size();
	}
	
	public IRGBColor getColor(int index)
	{
		IRGBColor color;
		color = _colors.get(index);
		return color;
	}
	*/
	
	/**
	 * This method should be called after multiple changes to the colors took place, not after every single color change.
	 * Keep in mind that these events will trigger transmission.
	 */
	protected void raiseColorsUpdatedEvent()
	{
		for (ColorsUpdatedListener l : _colorsUpdatedListeners)
		{
			l.colorsChanged(_colors);
		}
	}
	
	public void addColorsUpdatedListener(ColorsUpdatedListener listener)
	{
		_colorsUpdatedListeners.add(listener);
		//TODO maybe add variable "IsAutoActiveActivated" and only do this _active = true if variable is true;
		_active = true;
	}
	
	public void removeColorsUpdatedListener(ColorsUpdatedListener listener)
	{
		_colorsUpdatedListeners.remove(listener);
		if (_colorsUpdatedListeners.size() < 1)
		{
			_active = false;
		}
	}
	
	//TODO potentially add getListenersCount() to display it in the GUI.
}
