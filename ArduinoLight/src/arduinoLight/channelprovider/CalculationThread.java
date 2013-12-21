package arduinoLight.channelprovider;

/**
 * This class defines some fundamental properties of a Thread that is used to constantly recalculate values.
 * The Thread should be used as a always-running-'server'-thread (The Thread is a Daemon-Thread).
 * @author Felix
 */
public abstract class CalculationThread extends Thread
{
	public CalculationThread()
	{
		this.setDaemon(true);
	}
	
	@Override
	public abstract void run();
}
