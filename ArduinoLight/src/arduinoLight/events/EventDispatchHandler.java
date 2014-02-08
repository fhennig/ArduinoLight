package arduinoLight.events;

import java.util.concurrent.*;


/**
 * This class is a singleton and provides global access to dispatch Events. 
 * A ThreadPool is used to fire dispatched Events. Events are fired FIFO.
 */
public class EventDispatchHandler
{
	private static EventDispatchHandler _instance;
	private ExecutorService _executor = Executors.newCachedThreadPool();
	
	/**
	 * private constructor because this is a singleton.
	 */
	private EventDispatchHandler() { }
	
	/**
	 * Returns the instance of this Singleton.
	 */
	public static EventDispatchHandler getInstance()
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
		//DebugConsole.print("EventDispatchHandler", "dispatch", "dispatching Event: " + event.toString());
		_executor.execute(event);
	}
}
