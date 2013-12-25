package arduinoLight.interfaces;

/**
 * This interface should be implemented by every class that wants to take actions before the application is closed.
 * For instance, open ports and connections should be closed before the application quits, therefore the 
 * SerialConnection-class implements this interface.
 * @author Felix
 */
public interface Closeable
{
	/**
	 * This method should be called by the UI before the application closes.
	 */
	public void onCloseEvent();
}
