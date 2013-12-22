package arduinoLight.mixer;

import java.util.*;

import arduinoLight.util.RGBColor;

/**
 * This abstract class serves as a parent for all Mixer-classes. Arduino-connections need a Colorprovider.
 * @author Felix
 */
public abstract class Colorprovider
{
	private List<ColorsUpdatedListener> _listeners = new ArrayList<>();
	
	public void addColorsUpdatedListener(ColorsUpdatedListener listener)
	{
		_listeners.add(listener);
	}
	
	public void removeColorsUpdatedListener(ColorsUpdatedListener listener)
	{
		_listeners.remove(listener);
	}
	
	/**
	 * This notifies all the listeners. Should be called if the colors changed.
	 */
	protected void fireColorsUpdatedEvent(List<RGBColor> newColors)
	{
		for (ColorsUpdatedListener listener : _listeners)
		{
			listener.colorsUpdated(newColors);
		}
	}
}
