package arduinoLight.channelprovider;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines some fundamental properties of a Thread that is used to constantly recalculate values.
 * The Thread should be used as a always-running-'server'-thread (The Thread is a Daemon-Thread).
 * It is observable by any class that implements IterationFinishedListener.
 * @author Felix
 */
public abstract class CalculationThread extends Thread
{
	private List<IterationFinishedListener> _listeners = new ArrayList<>();
	
	public CalculationThread()
	{
		this.setDaemon(true); //Set Daemon to true, so the thread gets stopped if the application closes.
	}
	
	public void addIterationFinishedListener(IterationFinishedListener listener)
	{
		_listeners.add(listener);
	}
	
	public void removeIterationFinishedListener(IterationFinishedListener listener)
	{
		_listeners.remove(listener);
	}
	
	/**
	 * This method should be called from the while-loop inside the run method.
	 */
	protected void fireIterationFinishedEvent()
	{
		for (IterationFinishedListener listener : _listeners)
		{
			listener.iterationFinished();
		}
		//TODO remove this and implement a better notification-mechanism.
	}
	
	@Override
	public abstract void run();
}
