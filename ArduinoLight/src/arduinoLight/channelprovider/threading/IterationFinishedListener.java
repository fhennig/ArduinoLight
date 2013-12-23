package arduinoLight.channelprovider;

/**
 * The CalculationThread uses this Interface.
 * Implementors can react to the 'iterationFinished'-Event.
 * @author Felix
 *
 */
public interface IterationFinishedListener
{
	//TODO Using the observer pattern with threads is a bad idea.
	
	/**
	 * Gets called by a Thread if a Calculationcycle is finished.
	 */
	public void iterationFinished();
}
