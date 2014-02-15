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
 * This class handles the periodic transmission of colors through a serial connection
 * using the amblone protocol. 
 */
public class AmbloneTransmission
{
	public static final int MAX_FREQUENCY = 240;
	private static final int SUPPORTED_CHANNELS = 4;
	private ConcurrentMap<Integer, Channel> _map;
	private SerialConnection _connection;
	private ScheduledExecutorService _executor;
	private boolean _active = false;
	
	public AmbloneTransmission()
	{
		_map = new ConcurrentHashMap<>();
	}
	
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
	 * Returns the Channel that is mapped to the given port, or null
	 */
	public Channel getChannel(int port)
	{
		validatePort(port);
		
		return _map.get((Integer)port);
	}
	
	public boolean isActive()
	{
		return _active;
	}
	
	/**
	 * This method expects an already opened connection.
	 * Configuring a SerialConnection is not the purpose of this class.
	 * @param connection  an open connection
	 * @param frequency  the amount of refreshes per second (Hz)
	 */
	public synchronized void start(SerialConnection connection, int frequency)
	{
		if (connection.isOpen() == false)
			throw new IllegalArgumentException("the connection must be open!");

		_connection = connection;
		_executor = Executors.newSingleThreadScheduledExecutor();
		long period = Util.getPeriod(frequency, MAX_FREQUENCY);
		Runnable transmission = new Runnable()
		{
			public void run()
			{
				List<RGBColor> currentColors = getCurrentColors();
				if (currentColors.size() < 1)
				{
					currentColors = new ArrayList<>(); //If there are currently no colors,
					currentColors.add(Color.BLACK);	   //we transmit black, to reset all the colors that might be set.
				}
				AmblonePackage p = new AmblonePackage(currentColors);
				_connection.transmit(p.toByteArray());
			}
		};
		_executor.scheduleAtFixedRate(transmission, 0, period, TimeUnit.NANOSECONDS);
		_active = true;
		DebugConsole.print("AmbloneTransmission", "start", "starting successful! Frequency: " + frequency);
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
	 * Returns a list of colors. //TODO write proper documentation
	 * @return  a list of colors taken from the currently mapped channels
	 */
	private List<RGBColor> getCurrentColors()
	{
		//Find out how much channels are in use:
		int channelsUsed = 0;
		for (int i = SUPPORTED_CHANNELS - 1; i >= 0; i--)
		{
			if (_map.get(i) != null)
			{
				channelsUsed = i + 1;
				break;
			}
		}
		
		List<RGBColor> result = new ArrayList<>(channelsUsed);
		for (int i = 0; i < channelsUsed; i++)
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
	 * The Set is generated each time this method is called.
	 */
	public static Set<Integer> getPossiblePorts()
	{
		Set<Integer> possiblePorts = new LinkedHashSet<>();
		
		for (int i = 0; i < SUPPORTED_CHANNELS; i++)
			possiblePorts.add(i);
		
		return possiblePorts;
	}
	
	private void validatePort(int port)
	{
		if (port < 0 || port >= SUPPORTED_CHANNELS)
			throw new IllegalArgumentException();
	}
}
