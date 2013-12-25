package arduinoLight.channelprovider.generator.debugprovider;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.channelprovider.generator.threading.CalculationThread;
import arduinoLight.util.*;

/**
 * This Thread sets every channel to red, green, blue within a 1-second-interval.
 * @author Felix
 *
 */
public class DebugCalculationThread extends CalculationThread {

	private static final Color RED = new Color(255, 255, 0, 0);
	private static final Color GREEN = new Color(255, 0, 255, 0);
	private static final Color BLUE = new Color(255, 0, 0, 255);
	
	private volatile List<IChannel> _channels;
	
	
	public DebugCalculationThread(List<IChannel> channels)
	{
		_channels = channels;
	}
	
	public void setChannels(List<IChannel> channels)
	{
		_channels = channels;
	}
	
	
	@Override
	public void run()
	{		
		int i = 0;
		Color currentColor = new Color(255, 255, 255, 255);
		while(!isInterrupted())
		{
			List<IChannel> channelsCopy = new ArrayList<IChannel>(_channels);
			DebugConsole.print("TestCalculationThread", "run", "currentColor: " + currentColor.toString());
			
			
			
			switch(i)
			{
			case 0: currentColor = RED; break;
			case 1: currentColor = GREEN; break;
			case 2: currentColor = BLUE; break;
			}
			
			for (IChannel ch : channelsCopy)
			{
				ch.setColor(currentColor);
			}
			
			i = (i + 1) % 3;
			DebugConsole.print("TestCalculationThread", "run", "i = " + i);
			fireIterationFinishedEvent();
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO What to do here? [InterruptedException]
				//e.printStackTrace();
				break;
			}
		}
	}

}
