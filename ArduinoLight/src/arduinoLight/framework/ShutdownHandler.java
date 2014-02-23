package arduinoLight.framework;

import java.util.concurrent.ConcurrentLinkedDeque;

import arduinoLight.util.DebugConsole;

/**
 * More sophisticated ShutdownHook utility that uses a Stack.
 * That means that ShutdownHooks are executed in LIFO order. <br>
 * thread-safety: yes, various synchronized blocks.
 */
public class ShutdownHandler
{
	private static ShutdownHandler _instance = null;
	private ConcurrentLinkedDeque<ShutdownListener> _shutdownListeners = new ConcurrentLinkedDeque<>();
	private volatile boolean _shuttingDown = false;
	
	
	private ShutdownHandler()
	{
		DebugConsole.printh("ShutdownHandler", "<init>", "initializing ShutdownHandler");
		Runtime.getRuntime().addShutdownHook(new ActualShutdownHook());
	}
	
	
	
	public static synchronized ShutdownHandler getInstance()
	{
		if (_instance == null)
		{
			_instance = new ShutdownHandler();
		}
		
		return _instance;
	}
	
	
	
	/**
	 * @throws IllegalStateException  if the application is currently not shutting down.
	 */
	public void verifyShutdown()
	{
		if (!_shuttingDown)
			throw new IllegalStateException("Currently not shutting down!");
	}
	
	
	
	/**
	 * Pushes the given shutdownHook on the Stack.
	 * @param hook  the Runnable that should be executed on shutdown.
	 */
	public void addShutdownListener(ShutdownListener listener)
	{
		_shutdownListeners.push(listener);
		DebugConsole.print("ShutdownHandler", "pushShutdownListener", "ShutdownListener pushed: " + listener.toString());
	}	
	
	
	
	/**
	 * Removes the first occurrence of the given shutdownHook,
	 * starting search from the first shutdownHook that was pushed on the Stack.
	 */
	public void removeShutdownListener(ShutdownListener listener)
	{
		if (_shutdownListeners.remove(listener))
			DebugConsole.print("ShutdownHandler", "removeShutdownHook", "ShutdownListener removed: " + listener.toString());
		
	}
	
	
	
	/**
	 * An instance of this class is passed to the JVM Runtime to be executed on shutdown.
	 */
	private class ActualShutdownHook extends Thread
	{
		/**
		 * Runs all the shutdownHooks on the Stack, starting with the
		 * element that was pushed last.
		 */
		@Override
		public void run()
		{
			_shuttingDown = true;
			DebugConsole.printh("ShutdownHandler", "actualShutdown", "shutdown started");
			synchronized (_shutdownListeners)
			{
				while (!_shutdownListeners.isEmpty())
				{
					ShutdownListener listener = _shutdownListeners.pop();
					try
					{
						DebugConsole.print("ShutdownHandler", "actualShutdown", "shutting down '" + listener + "'");
						listener.onShutdown();
						DebugConsole.print("ShutdownHandler", "actualShutdown", "successful!");
						
					}
					catch (Exception e) //Catch all Exceptions to ensure that the shutdown thread does not terminate unexpectedly
					{
						DebugConsole.print("ShutdownHandler", "actualShutdown", "Exception in: " + listener);
						e.printStackTrace();
					}
				}
			}
			DebugConsole.printh("ShutdownHandler", "actualShutdown", "shutdown completed");
		}
	}
}
