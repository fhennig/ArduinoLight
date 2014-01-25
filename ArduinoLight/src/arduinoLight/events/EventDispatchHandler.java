package arduinoLight.events;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import arduinoLight.util.DebugConsole;

/**
 * This class has its own Thread. It manages a queue, where Items are placed that need to refresh
 * properties and fire events. The event-firing takes place in this thread and is therefore less time consuming.
 * On Extension, there should be added more threads to this class, not more instances of this class. 
 * This class is a singleton.
 * @author Felix
 */
public class EventDispatchHandler
{
	private static EventDispatchHandler _instance;
	private static Queue<Event> _queuedEvents = new ConcurrentLinkedQueue<>();
	private EventFiringThread _workerThread = new EventFiringThread();
	
	private EventDispatchHandler()
	{
		_workerThread.start();
	}
	
	public static EventDispatchHandler getInstance()
	{
		if (_instance == null) //Lazy Initialize.
		{
			_instance = new EventDispatchHandler();
		}
		return _instance;
	}
	
	public void dispatch(Event event)
	{
		_queuedEvents.add(event);
				
		if (_queuedEvents.size() > 20)
			throw new IllegalStateException("There are more than 20 Elements queued, implementation has to be rethought!");
	}
	
	/**
	 * This Thread is a daemon thread with an infinite loop.
	 * It acts as a consumer on the event queue. It takes events and calls 'notifyListeners'.
	 * @author Felix
	 */
	private class EventFiringThread extends Thread
	{
		public EventFiringThread()
		{
			this.setDaemon(true); //daemon thread. Is killed if the application is closed.
		}
		
		public void run()
		{		
			while(true) //while(true) is viable here, as this is a daemon thread.
			{
				Event nextEvent = _queuedEvents.poll();
				if (nextEvent != null)
				{
					nextEvent.notifyListeners();
					DebugConsole.print("EventFiringThread", "run", "Item refreshed.");
					DebugConsole.print("EventFiringThread", "run", "Remaining Items: " + _queuedEvents.size());
				}
			}
		}
	}
}
