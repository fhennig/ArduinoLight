package arduinoLight.framework;

import java.util.concurrent.*;

import arduinoLight.util.DebugConsole;


/**
 * This class is a singleton and provides global access to dispatch Events. 
 * A thread pool is used to fire dispatched Events.
 * Listeners to events are notified from the threads in the thread pool, 
 * not from the thread where the event originated. This leads to faster execution in
 * performance-critcal code sections.
 * Events are fired FIFO.
 * thread-safety: This class is thread-safe through delegation.
 */
public class EventDispatchHandler implements ShutdownListener
{
	private static EventDispatchHandler _instance;
	private final ExecutorService _executor = Executors.newCachedThreadPool();
	
	
	
	/**
	 * private constructor because this is a singleton.
	 */
	private EventDispatchHandler()
	{
		DebugConsole.printh("EventDispatchHandler", "<init>", "initializing EventDispatchHandler");
		ShutdownHandler.getInstance().addShutdownListener(this);
	}
	
	
	
	/**
	 * Returns the instance of this Singleton.
	 */
	public synchronized static EventDispatchHandler getInstance()
	{
		if (_instance == null) //Lazy Initialize.
		{
			_instance = new EventDispatchHandler();
		}
		return _instance;
	}
	
	
	
	/**
	 * Dispatches a given Event for later concurrent execution. 
	 */
	public void dispatch(Event event)
	{
		_executor.execute(event);
	}
	
	
	
	@Override
	public synchronized void onShutdown()
	{		
		ShutdownHandler.getInstance().verifyShutdown();
		
		_executor.shutdown();
		_instance = null;
	}
	
	//----------------------------------------------------------
	@Override
	public String toString()
	{
		return "EventDispatchHandler";
	}
}
