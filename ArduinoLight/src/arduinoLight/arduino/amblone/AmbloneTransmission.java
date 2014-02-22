package arduinoLight.arduino.amblone;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import arduinoLight.arduino.SerialConnection;
import arduinoLight.channel.Channel;
import arduinoLight.util.Color;
import arduinoLight.util.DebugConsole;
import arduinoLight.util.RGBColor;
import arduinoLight.util.Util;

/**
 * This class maps channels to output ports. If the transmission is active, the colors of
 * the channels are transmitted through a given connection periodically.
 * to encode the colors, the amblone protocol is used (http://amblone.com). <br>
 * thread-safety: In part, thread-safety is delegated to the ConcurrentMap, other methods are synchronized.
 */
public class AmbloneTransmission
{
	public static final int MAX_REFRESHRATE = 240;
	private static final int _SUPPORTED_CHANNELS = 4;
	private final ConcurrentMap<Integer, Channel> _map = new ConcurrentHashMap<>(_SUPPORTED_CHANNELS);
	private SerialConnection _connection;
	private ScheduledExecutorService _executor;
	private volatile boolean _active = false;
	

	
	/**
	 * @param port  an integer specifying an output port. 0 <= port < 4
	 * @param channel  a channel that should be mapped to this output.
	 * If channel == null, the selected port is cleared.
	 */
	public void setOutput(int port, Channel channel)
	{
		validatePort(port);
		
		if (channel == null) //if channel == null, the key is removed (every key should have a valid channel)
			clearOutput(port);
		else
			_map.put(port, channel);
		DebugConsole.print("AmbloneTransmission", "setOutput", "Port " + port + " set to " + channel);
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
	
	/** Indicates if transmission is currently active. */
	public boolean isActive()
	{
		return _active;
	}
	
	/**
	 * This method expects an already opened connection.
	 * Configuring a SerialConnection is not the purpose of this class.
	 * @param connection  an open connection
	 * @param refreshRate  the amount of refreshes per second (Hz)
 	 * If the given refreshRate is greater than MAX_REFRESHRATE, MAX_REFRESHRATE is used instead.
	 */
	public synchronized void start(SerialConnection connection, int refreshRate)
	{
		if (connection.isOpen() == false)
			throw new IllegalArgumentException("the connection must be open!");

		_connection = connection;
		_executor = Executors.newSingleThreadScheduledExecutor();
		refreshRate = Math.min(refreshRate, MAX_REFRESHRATE);
		long period = Util.getPeriod(refreshRate);
		Runnable transmission = new Runnable()
		{
			private int currentlySetPortsAtArduino = _SUPPORTED_CHANNELS;
			public void run()
			{
				int currentlySetPortsInMap = getAmountPortsUsed();
				int currentlyUsedPorts = Math.max(currentlySetPortsInMap, currentlySetPortsAtArduino);
				if (currentlyUsedPorts < 1)
					return; //If there are no ports in use, there is nothing to transmit.
				
				List<RGBColor> colorsForTransmission = getColorsForTransmission(currentlyUsedPorts);
				AmblonePackage p = new AmblonePackage(colorsForTransmission);
				_connection.transmit(p.toByteArray());
				currentlySetPortsAtArduino = currentlySetPortsInMap;
			}
		};
		//TODO uncaughtexceptionhandler
		//TODO shutdownhook
		_executor.scheduleAtFixedRate(transmission, 0, period, TimeUnit.NANOSECONDS);
		_active = true;
		DebugConsole.print("AmbloneTransmission", "start", "starting successful! Frequency: " + refreshRate);
	}
	
	/**
	 * This method stops the transmission and returns the connection that
	 * was passed in with 'start(...)'.
	 */
	public synchronized SerialConnection stop()
	{
		if (!_active)
			throw new IllegalStateException("The Transmission could not be stopped, because it is not active!");
		
		_active = false;
		_executor.shutdown();
		_executor = null;
		SerialConnection c = _connection;
		_connection = null;
		
		DebugConsole.print("AmbloneTransmission", "stopTransmission", "stopping successful");
		return c;
	}
	
	/**
	 * Searches for the highest port that is currently set.
	 * Example: If port 0 ist not set, but 1 is set, 2 is returned.
	 */
	private int getAmountPortsUsed()
	{
		int portsUsed = 0;
		for (int i = _SUPPORTED_CHANNELS - 1; i >= 0; i--)
		{
			if (_map.get(i) != null)
			{
				portsUsed = i + 1;
				break;
			}
		}
		return portsUsed;
	}
	
	/**
	 * Returns a list of the colors of the channels that are currently mapped to the output ports.
	 * The list is used for transmission. For every output port that is unmapped, black is added to the list.
	 * @return  a list of colors taken from the currently mapped channels
	 */
	private List<RGBColor> getColorsForTransmission(int usedPorts)
	{		
		List<RGBColor> result = new ArrayList<>(usedPorts);
		for (int i = 0; i < usedPorts; i++)
		{
			Channel channel = _map.get(i);
			
			if (channel != null)
				result.add(channel.getColor());
			else
				result.add(Color.BLACK); //Add black for every output that is not in use.
		}
		
		return result;
	}
	
	/**
	 * static helper-method that returns a LinkedHashSet containing Integers
	 * that represent the possible ports.
	 * The Integers are returned in an ascending order, if the set is iterated.
	 * The Set is generated each time this method is called.
	 */
	public static Set<Integer> getPossiblePorts()
	{
		Set<Integer> possiblePorts = new LinkedHashSet<>();
		
		for (int i = 0; i < _SUPPORTED_CHANNELS; i++)
			possiblePorts.add(i);
		
		return possiblePorts;
	}
	
	/** Throws IllegalArgumentException if the given port number is not supported by the protocol */
	private void validatePort(int port)
	{
		if (port < 0 || port >= _SUPPORTED_CHANNELS)
			throw new IllegalArgumentException("Port '" + port + "' not supported. " + 
											   "Must be between 0 and " + _SUPPORTED_CHANNELS + ".");
	}
}
