package arduinoLight.threading;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Refreshqueue
{
	private Queue<Refreshable> _queuedItems = new ConcurrentLinkedQueue<>();
	private RefreshWorkerThread _workerThread = new RefreshWorkerThread();
	
	public Refreshqueue()
	{
		_workerThread.start();
	}

	public void queueItem(Refreshable item)
	{
		_queuedItems.add(item);
	}
	
	private class RefreshWorkerThread extends Thread
	{
		public void run()
		{			
			while(true) //TODO insert proper condition
			{
				Refreshable nextItem = _queuedItems.poll();
				if (nextItem != null)
					nextItem.refresh();
			}
		}
	}
}
