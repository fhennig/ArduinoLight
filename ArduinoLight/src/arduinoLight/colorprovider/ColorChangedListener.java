package arduinoLight.colorprovider;

/**
 * This interface should be implemented by the ArduinoConnection, that can then react to the colorChangedEvents and sent the color.
 * This interface is part of an implementation of the "Observer Pattern".
 * @author Felix
 */
public interface ColorChangedListener
{
	public void colorChanged(int index);
}
