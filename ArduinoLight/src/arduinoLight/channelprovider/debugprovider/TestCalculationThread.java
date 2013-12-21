package arduinoLight.channelprovider.debugprovider;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.channelprovider.CalculationThread;
import arduinoLight.util.*;

public class TestCalculationThread extends CalculationThread {

	private static final Color RED = new Color(255, 255, 0, 0);
	private static final Color GREEN = new Color(255, 0, 255, 0);
	private static final Color BLUE = new Color(255, 0, 0, 255);
	
	private volatile List<IChannel> _channels;
	
	
	public TestCalculationThread(List<IChannel> channels)
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
		while(!isInterrupted())
		{
			int i = 0;
			List<IChannel> channelsCopy = new ArrayList<IChannel>(_channels);
			
			for (IChannel ch : channelsCopy)
			{
				switch(i)
				{
				case 0: ch.setColor(RED);
				case 1: ch.setColor(GREEN);
				case 2: ch.setColor(BLUE);
				}
			}
			
			i++;
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
