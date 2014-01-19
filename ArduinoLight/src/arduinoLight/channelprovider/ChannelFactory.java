package arduinoLight.channelprovider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import arduinoLight.ArduinoLight;
import arduinoLight.channel.IChannel;
import arduinoLight.channel.ThreadingChannel;

/**
 * This has also some kind of "blackboard" functionality
 * @author Felix
 *
 */
public class ChannelFactory
{
    private static final AtomicInteger _instances = new AtomicInteger(0); //Used to generate Ids.
	private List<IChannel> _registeredChannels = new ArrayList<>();
	
	public IChannel getChannel()
	{
		IChannel newChannel = new ThreadingChannel(_instances.getAndIncrement(), ArduinoLight.getRefreshQueue());
		_registeredChannels.add(newChannel);
		return newChannel;
	}
	
	public IChannel getChannel(int id)
	{
		for (IChannel channel : _registeredChannels)
		{
			if (channel.getId() == id)
				return channel;
		}
		
		return null;
	}
}
