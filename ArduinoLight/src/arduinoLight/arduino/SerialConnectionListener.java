package arduinoLight.arduino;

import arduinoLight.mixer.Colorprovider;

public interface SerialConnectionListener
{
	public void activeChanged(Object source, boolean newActive);
	public void speedChanged(Object source, int newSpeed);
	public void colorproviderChanged(Object source, Colorprovider newColorprovider);
}
