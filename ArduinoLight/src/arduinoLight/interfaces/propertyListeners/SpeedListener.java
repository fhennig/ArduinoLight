package arduinoLight.interfaces.propertyListeners;

/**
 * Observerpattern implementation. Classes that implement this interface can listen to a 'SpeedCounter'.
 * @author Felix
 */
public interface SpeedListener
{
	public void speedChanged(Object source, int newSpeed);
}
