package arduinoLight.arduino.amblone;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import arduinoLight.channel.Channel;
import arduinoLight.util.DebugConsole;

/**
 * This class maps Channels to Ports which are represented by positive Integers. 
 * By default, every Port is mapped to null. 
 */
public class PortMap
{
	private final ConcurrentMap<Integer, Channel> _map = new ConcurrentHashMap<>();
	
		
	
	/**
	 * @param port  an integer specifying an output port. 0 <= port
	 * @param channel  a channel that should be mapped to this output.
	 * If channel == null, the selected port is cleared.
	 */
	public void setPort(int port, Channel channel)
	{
		validatePort(port);
		
		if (channel == null) //if channel == null, the key is removed (every key should have a valid channel)
			clearOutput(port);
		else
			_map.put(port, channel);
		DebugConsole.print("PortMap", "setOutput", "Port " + port + " set to " + channel);
	}
	
	/** Stops output on the specified port */
	public void clearOutput(int port)
	{
		_map.remove(port);
		DebugConsole.print("AmbloneTransmission", "clearOutput", "Port " + port + " cleared.");
	}
	
	/**
	 * Returns the Channel that is mapped to the given port,
	 * or null if currently no channel is mapped to the port.
	 */
	public Channel getChannel(int port)
	{
		validatePort(port);
		
		return _map.get((Integer)port);
	}
	
	/** Throws IllegalArgumentException if the given port number is not supported by the protocol */
	private void validatePort(int port)
	{
		if (port < 0)
			throw new IllegalArgumentException("Port '" + port + "' not supported. " + 
											   "Must be greater than or equal to 0.");
	}
}
