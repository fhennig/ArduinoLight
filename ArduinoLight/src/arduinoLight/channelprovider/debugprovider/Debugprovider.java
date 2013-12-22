package arduinoLight.channelprovider.debugprovider;

import arduinoLight.channelprovider.Channelprovider;
import arduinoLight.channelprovider.IterationFinishedListener;

public class Debugprovider extends Channelprovider implements IterationFinishedListener
{

	private TestCalculationThread _thread;
	
	
	@Override
	public boolean activate() {
		_thread = new TestCalculationThread(_channels);
		_thread.addIterationFinishedListener(this);
		_thread.start();
		return true; //return that activating was successfull //TODO make this a javadoc comment
	}

	@Override
	public boolean deactivate() {
		_thread.interrupt();
		try {
			_thread.join(1500);
		} catch (InterruptedException e) {
			// TODO InterruptedExceptions ???
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void iterationFinished() {
		this.fireChannelsUpdatedEvent();
	}

}
