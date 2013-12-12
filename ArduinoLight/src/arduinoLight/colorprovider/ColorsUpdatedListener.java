package arduinoLight.colorprovider;

import java.util.List;

import arduinoLight.util.*;

/**
 * This interface should be implemented by the ArduinoConnection, that can then react to the colorsChangedEvents and sent the colors.
 * This interface is part of an implementation of the "Observer Pattern".
 * @author Felix
 */
public interface ColorsUpdatedListener
{
	public void colorsChanged(List<IRGBColor> newColors);
}
