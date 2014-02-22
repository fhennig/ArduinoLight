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
	private ConcurrentLinkedDeque<Runnable> _shutdownHooks = new ConcurrentLinkedDeque<>();
	
	
	
	private ShutdownHandler()
	{
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
	 * Pushes the given shutdownHook on the Stack.
	 * @param hook  the Runnable that should be executed on shutdown.
	 */
	public void pushShutdownHook(Runnable hook)
	{
		_shutdownHooks.push(hook);
		DebugConsole.print("ShutdownHandler", "pushShutdownHook", "ShutdownHook pushed: " + hook.toString());
	}	
	
	
	
	/**
	 * Removes the first occurrence of the given shutdownHook,
	 * starting search from the first shutdownHook that was pushed on the Stack.
	 */
	public void removeShutdownHook(Runnable hook)
	{
		_shutdownHooks.remove(hook);
		DebugConsole.print("ShutdownHandler", "removeShutdownHook", "ShutdownHook removed: " + hook.toString());
		
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
			DebugConsole.print("ShutdownHandler", "actualShutdown", "shutting down!");
			synchronized (_shutdownHooks)
			{
				while (!_shutdownHooks.isEmpty())
				{
					Runnable hook = _shutdownHooks.pop();
					try
					{
						
						hook.run();
						DebugConsole.print("ShutdownHandler", "actualShutdown", "successful: " + hook);
						
					}
					catch (Exception e)
					{
						DebugConsole.print("ShutdownHandler", "actualShutdown", "Exception in: " + hook);
						e.printStackTrace();
					}
				}
			}
			DebugConsole.print("ShutdownHandler", "actualShutdown", "shutdown completed.");
		}
	}
}
