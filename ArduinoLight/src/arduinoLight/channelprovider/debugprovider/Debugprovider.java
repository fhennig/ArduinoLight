package arduinoLight.channelprovider.debugprovider;

import arduinoLight.channelprovider.Channelprovider;
import arduinoLight.channelprovider.threading.IterationFinishedListener;

public class Debugprovider extends Channelprovider implements IterationFinishedListener
{

	private DebugCalculationThread _thread;
	
	@Override
	protected boolean activate() {
		_thread = new DebugCalculationThread(_channels);
		_thread.addIterationFinishedListener(this);
		_thread.start();
		return true; //return that activating was successful.
	}

	@Override
	protected boolean deactivate() {
		_thread.removeIterationFinishedListener(this);
		_thread.interrupt();
		try {
			_thread.join(1500);
		} catch (InterruptedException e) {
			//The Thread was not terminated after 1500ms!
			// TODO InterruptedExceptions ???
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void iterationFinished() {
		this.fireChannelcolorsUpdatedEvent(); //Forward the event thrown by our thread.
	}

}
