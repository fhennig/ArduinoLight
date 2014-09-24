package arduinoLight.arduino;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import arduinoLight.channel.Channel;
import arduinoLight.framework.ShutdownHandler;
import arduinoLight.framework.ShutdownListener;
import arduinoLight.util.Color;
import arduinoLight.util.DebugConsole;
import arduinoLight.util.RGBColor;
import arduinoLight.util.Util;

/**
 * This class transmits colors from a PortMap through a SerialConnection.
 * If the transmission is active, the colors of the channels are transmitted.
 * To encode the colors, the amblone protocol is used (http://amblone.com). <br>
 * thread-safety: In part, thread-safety is delegated to the ConcurrentMap, other methods are synchronized.
 */
public class Transmission implements ShutdownListener
{
	private static final int SUPPORTED_CHANNELS = 4;
	public static final int MAX_REFRESHRATE = 240;
	private SerialConnection _connection;
	private ScheduledExecutorService _executor;
	private volatile boolean _active = false;
	private PortMap _map = new PortMap(SUPPORTED_CHANNELS);
	
		
	
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
	public synchronized PortMap start(SerialConnection connection, int refreshRate, final PackageFactory packageFactory)
	{
		if (connection.isOpen() == false)
			throw new IllegalArgumentException("the connection must be open!");

		_map = new PortMap(packageFactory.getMaxPackageSize());
		_connection = connection;
		_executor = Executors.newSingleThreadScheduledExecutor();
		refreshRate = Math.min(refreshRate, MAX_REFRESHRATE);
		long period = Util.getPeriod(refreshRate);
		Runnable transmission = new Runnable()
		{
			private int currentlySetPortsAtArduino = SUPPORTED_CHANNELS;
			public void run()
			{
				int currentlySetPortsInMap = getAmountPortsUsed();
				int currentlyUsedPorts = Math.max(currentlySetPortsInMap, currentlySetPortsAtArduino);
				if (currentlyUsedPorts < 1)
					return; //If there are no ports in use, there is nothing to transmit.
				
				List<RGBColor> colorsForTransmission = getColorsForTransmission(currentlyUsedPorts);
				_connection.transmit(packageFactory.createPackage(colorsForTransmission));
				currentlySetPortsAtArduino = currentlySetPortsInMap;
			}
		};
		//TODO uncaughtexceptionhandler
		_executor.scheduleAtFixedRate(transmission, 0, period, TimeUnit.NANOSECONDS);
		_active = true;
		ShutdownHandler.getInstance().addShutdownListener(this);
		DebugConsole.print("AmbloneTransmission", "start", "starting successful! Frequency: " + refreshRate);
		return _map;
	}
	
	/**
	 * This method stops the transmission.
	 * the connection that was used is passed gets returned.
	 * If the connection is already stopped, null is returned.
	 * @returns  the used connection or null if transmission was already stopped.
	 */
	public synchronized SerialConnection stop()
	{
		if (!_active)
			return null;
		
		_active = false;
		ShutdownHandler.getInstance().removeShutdownListener(this);
		_executor.shutdown();
		_executor = null;
		SerialConnection c = _connection;
		_connection = null;
		DebugConsole.print("AmbloneTransmission", "stopTransmission", "stopping successful");
		return c;
	}
	
	/**
	 * Searches for the highest port that is currently set. <br>
	 * Example: If port 0 is not set, but 1 is set, 2 is returned.
	 */
	private int getAmountPortsUsed()
	{
		int portsUsed = 0;
		for (int i = SUPPORTED_CHANNELS - 1; i >= 0; i--)
		{
			if (_map.getChannel(i) != null)
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
			Channel channel = _map.getChannel(i);
			
			if (channel != null)
				result.add(channel.getColor());
			else
				result.add(Color.BLACK); //Add black for every output that is not in use.
		}
		
		return result;
	}

	/**
	 * Is called from the ShutdownHook to stop the transmission and shutdown the executor.
	 */
	@Override
	public void onShutdown()
	{
		ShutdownHandler.getInstance().verifyShutdown();
		stop();
	}
	
	//----------------------------------------------------------
	@Override
	public String toString()
	{
		return "AmbloneTransmission";
	}
}
