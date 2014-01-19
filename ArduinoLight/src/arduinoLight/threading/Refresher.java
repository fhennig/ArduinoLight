package arduinoLight.threading;

import java.util.Queue;

import arduinoLight.interfaces.Refreshable;

public class Refresher
{
	private static Queue<Refreshable> _queuedItems;
	private RefreshWorkerThread _workerThread = new RefreshWorkerThread();
	
	public Refresher(Queue<Refreshable> queueSource)
	{
		_queuedItems = queueSource;
		_workerThread.start();
	}
	
	private class RefreshWorkerThread extends Thread
	{
		public void run()
		{		
			this.setDaemon(true);
			while(true) //TODO insert proper condition
			{
				Refreshable nextItem = _queuedItems.poll();
				if (nextItem != null)
					nextItem.refresh();
			}
		}
	}
}
