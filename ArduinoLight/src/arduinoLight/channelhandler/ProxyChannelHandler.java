package arduinoLight.channelhandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import arduinoLight.util.Color;
import arduinoLight.util.IChannel;

public class ProxyChannelHandler
{
	private static ProxyChannelHandler _instance = null;
	private Set<IChannel> _channels = new HashSet<>();
	private List<IChannel> _changes = Collections.synchronizedList(new ArrayList<IChannel>());
	
	private ProxyChannelHandler()
	{}
	
	public static ProxyChannelHandler getInstance()
	{
		if (_instance == null)
		{
			_instance = new ProxyChannelHandler();
		}
		return _instance;
	}
	
//	public IChannel getChannel(int id)
//	{ //getChannel should not be supported here, this is only for efficient events!
//		
//	}
	
	private void raiseChannelChangedEvent(ChannelChangedEventArgs args)
	{
		
	}
	
	private class ChannelChangeWorkerThread extends Thread
	{
		public void run()
		{
			List<IChannel> updatedChannels;
			
			while(true) //TODO insert proper condition
			{
				synchronized(_changes)
				{
					updatedChannels = new ArrayList<IChannel>(_changes);
					_changes.clear();
				}
				
				for (IChannel newChannel : updatedChannels)
				{
					
				}
				//Über changes iterieren
				//raiseChangeEvent
				
			}
		}
	}
	
	
}
