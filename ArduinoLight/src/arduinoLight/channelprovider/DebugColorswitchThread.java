package arduinoLight.channelprovider;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import arduinoLight.channel.IChannel;
import arduinoLight.util.*;

/**
 * This Thread holds a List of Channels and changes their Colors every second.
 * Colors are RED, GREEN, BLUE. 
 */
public class DebugColorswitchThread extends Thread
{
	private final List<IChannel> _channels = new CopyOnWriteArrayList<>();
	
	public DebugColorswitchThread(Collection<IChannel> collection)
	{
		_channels.addAll(collection);
	}
	
	public List<IChannel> getChannels()
	{
		return _channels;
	}
	
	
	@Override
	public void run()
	{		
		int i = 0; //variable to switch through colors
		Color currentColor = new Color(255, 255, 255, 255);
		while(!isInterrupted())
		{
			DebugConsole.print("TestCalculationThread", "run", "currentColor: " + currentColor.toString());
			
			switch(i)
			{
			case 0: currentColor = Color.RED; break;
			case 1: currentColor = Color.GREEN; break;
			case 2: currentColor = Color.BLUE; break;
			}
			
			for (IChannel ch : _channels)
			{
				ch.setColor(currentColor);
			}
			
			i = (i + 1) % 3;
			try
			{
				sleep(1000);
			}
			catch (InterruptedException e)
			{
				break; //break --> ends thread
			}
		}
		DebugConsole.print("TestCalculationThread", "run", "terminating Thread!");
	}

}
