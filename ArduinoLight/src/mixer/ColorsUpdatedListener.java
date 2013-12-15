package mixer;

import java.util.List;

import arduinoLight.util.RGBColor;

/**
 * Observerpattern implementation. Classes that implement this interface can listen to a 'Colorprovider'.
 * @author Felix
 */
public interface ColorsUpdatedListener
{
	public void colorsUpdated(List<RGBColor> newColors);
}
