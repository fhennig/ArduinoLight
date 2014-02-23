package arduinoLight.framework;

/**
 * Classes that need to act upon shutdown, for example to close connections or clean up resources
 * should implement this interface and add themselves to the ShutdownHandler.
 */
public interface ShutdownListener
{
	/**
	 * Gets called if the application is shutting down.
	 * Obviously, should not be called of the application is not shutting down.
	 */
	public void onShutdown();
}
