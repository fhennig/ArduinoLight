package arduinoLight.channelprovider;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines some fundamental properties of a Thread that is used to constantly recalculate values.
 * The Thread should be used as a always-running-'server'-thread (The Thread is a Daemon-Thread).
 * @author Felix
 */
public abstract class CalculationThread extends Thread
{
	private List<IterationFinishedListener> _listeners = new ArrayList<>();
	
	public CalculationThread()
	{
		this.setDaemon(true);
	}
	
	public void addIterationFinishedListener(IterationFinishedListener listener)
	{
		_listeners.add(listener);
	}
	
	public void removeIterationFinishedListener(IterationFinishedListener listener)
	{
		_listeners.remove(listener);
	}
	
	protected void fireIterationFinishedEvent()
	{
		for (IterationFinishedListener listener : _listeners)
		{
			listener.iterationFinished();
		}
	}
	
	@Override
	public abstract void run();
}
