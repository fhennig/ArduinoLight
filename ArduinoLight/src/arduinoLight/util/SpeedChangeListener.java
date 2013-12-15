package arduinoLight.util;

/**
 * Observerpattern implementation. Classes that implement this interface can listen to a 'SpeedCounter'.
 * @author Felix
 */
public interface SpeedChangeListener
{
	public void speedChanged(int newSpeed);
}
