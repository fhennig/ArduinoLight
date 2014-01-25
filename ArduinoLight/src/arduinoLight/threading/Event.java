package arduinoLight.threading;

/**
 * Abstract Event-class.
 * This class can be easily implemented as an anonymous class in a typical 'raise...Event()'-method.
 * @author Felix
 */
public abstract class Event
{
	private final int _hashCode;
	
	/**
	 * The constructor takes a source object and a string as parameters.
	 * From these parameters, a unique hashCode for this object and the event raised is generated.
	 * This way, 'Event'-objects for the same source object and event can be recognized as equal.
	 * @param source The object that fired the event.
	 * @param eventName The name of the event.
	 */
	public Event(Object source, String eventName)
	{
		_hashCode = source.hashCode() + eventName.hashCode();
	}
	
	public abstract void notifyListeners();
	
	@Override
	public int hashCode()
	{
		return _hashCode;
	}
}
