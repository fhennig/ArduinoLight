package arduinoLight.arduino.amblone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import arduinoLight.arduino.*;
import arduinoLight.channel.Channel;
import arduinoLight.util.Color;
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
	
	public AmbloneTransmission()
	{
		initMap();
	}
	
	private void initMap()
	{
		_map = new ConcurrentHashMap<>();
		for (int i = 0; i < SUPPORTED_CHANNELS; i++)
			_map.put(i, null);
	}
	
	public Set<Integer> getPossiblePorts()
	{
		return Collections.unmodifiableSet(_map.keySet());
	}
	
	/**
	 * @param port  an integer specifying an output port. 0 <= port < 4
	 * @param channel  a channel that should be mapped to this output.
	 */
	public void setOutput(int port, Channel channel)
	{
		if (!_map.containsKey(port))
		{
			throw new IllegalArgumentException("Illegal Value for 'port' given: " + port);
		}
		if (channel == null) //if channel == null, the key is removed (every key should have a valid channel)
			clearOutput(port);
		else
			_map.put(port, channel);
	}
	
	/** Stops output on the specified port */
	public void clearOutput(int port)
	{
		_map.put(port, null);
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
				AmblonePackage p = new AmblonePackage(getCurrentColors());
				_connection.transmit(p.toByteArray());
			}
		};
		_executor.scheduleAtFixedRate(transmission, 0, period, TimeUnit.NANOSECONDS);
	}
	
	/**
	 * This method stops the transission and returns the connection that
	 * was passed in with 'start(...)'.
	 */
	public synchronized SerialConnection stopTransmission()
	{
		_executor.shutdown();
		_executor = null;
		SerialConnection c = _connection;
		_connection = null;
		
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
}
