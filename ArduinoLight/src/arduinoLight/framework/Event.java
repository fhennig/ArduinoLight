package arduinoLight.framework;



/**
 * Abstract Event-class.
 * This class can be easily implemented as an anonymous class in a typical 'raise...Event()'-method.
 * Subclasses have to implement the notifyListeners method that gets raised if the Event is dispatched
 * to the EventDispatchHandler.
 */
public abstract class Event implements Runnable
{
	//TODO event: hashcode has no use currently.
	private final int _hashCode;
	private final String _eventDescription;
	
	
	
	/**
	 * The constructor takes a source object and a string as parameters.
	 * From these parameters, a unique hashCode for this object and the event raised is generated.
	 * This way, 'Event'-objects for the same source object and event can be recognized as equal.
	 * 
	 * @param source  The object that fired the event.
	 * @param eventName  The name of the event.
	 */
	public Event(Object source, String eventName)
	{
		_hashCode = source.hashCode() + eventName.hashCode();
		_eventDescription = eventName + " in: '" + source.toString() + "'";
	}
	
	
	
	/**
	 * This method is usually implemented with a foreach loop where
	 * all the subscribers of the given Event are notified.
	 */
	public abstract void notifyListeners();

	
	
	/**
	 * Implemented for Runnable, redirects to {@link #notifyListeners()}
	 */
	@Override
	public void run()
	{
		notifyListeners();
	}
	
	
	
	@Override
	public int hashCode()
	{
		return _hashCode;
	}
	
	/**
	 * For debugging
	 */
	@Override
	public String toString()
	{
		return _eventDescription;
	}
}
