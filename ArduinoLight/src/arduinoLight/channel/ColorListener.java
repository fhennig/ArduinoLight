package arduinoLight.channel;

import arduinoLight.util.Color;

/**
 * Classes that implement this interface can listen to changes to the Color of a Channel.
 */
public interface ColorListener
{
	/**
	 * This method is called by a subscribed Channel if its Color changed.
	 */
	public void colorChanged(Object sender, Color newColor);
}
