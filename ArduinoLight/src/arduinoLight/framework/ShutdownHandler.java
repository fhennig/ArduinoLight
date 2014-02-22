package arduinoLight.framework;

import java.util.ArrayDeque;
import java.util.Deque;

import arduinoLight.util.DebugConsole;

public class ShutdownHandler
{
	private static ShutdownHandler _instance = null;
	private Deque<Runnable> _shutdownHooks = new ArrayDeque<>();
	
	
	
	private ShutdownHandler()
	{
		Runtime.getRuntime().addShutdownHook(new ActualShutdownHook());
	}
	
	
	
	public static ShutdownHandler getInstance()
	{
		if (_instance == null)
			_instance = new ShutdownHandler();
		
		return _instance;
	}
	
	
	
	public void addShutdownHook(Runnable hook)
	{
		_shutdownHooks.push(hook);
	}
	
	
	
	public void removeShutdownHook(Runnable hook)
	{
		_shutdownHooks.remove(hook);
	}
	
	
	
	private class ActualShutdownHook extends Thread
	{
		@Override
		public void run()
		{
			DebugConsole.print("ShutdownHandler", "actualShutdown", "shutting down!");
			while (_shutdownHooks.size() > 0)
			{
				_shutdownHooks.pop().run();
			}
			DebugConsole.print("ShutdownHandler", "actualShutdown", "shutdown completed.");
		}
	}
}
