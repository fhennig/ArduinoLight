package arduinoLight.colorprovider;

/**
 * This interface should be implemented by the ArduinoConnection, that can then react to the colorsChangedEvents and sent the colors.
 * This interface is part of an implementation of the "Observer Pattern".
 * @author Felix
 */
public interface ColorsChangedListener
{
	public void colorsChanged();
}
