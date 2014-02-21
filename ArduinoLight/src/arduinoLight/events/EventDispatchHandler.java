package arduinoLight.events;

import java.util.concurrent.*;


/**
 * This class is a singleton and provides global access to dispatch Events. 
 * A ThreadPool is used to fire dispatched Events.
 * Listeners to events are notified from the threads in the thread pool, 
 * not from the thread where the event originated. This leads to faster execution in
 * performance-critcal code sections.
 * Events are fired FIFO.
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
