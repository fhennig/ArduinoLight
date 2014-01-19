package arduinoLight.arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.*;

import arduinoLight.channel.IChannel;
import arduinoLight.channelprovider.ChannelcolorsListener;
import arduinoLight.channelprovider.generator.Channelgenerator;
import arduinoLight.interfaces.Closeable;
import arduinoLight.interfaces.propertyListeners.ActiveListener;
import arduinoLight.interfaces.propertyListeners.SpeedListener;
import arduinoLight.util.DebugConsole;
import arduinoLight.util.RGBColor;
import arduinoLight.util.SpeedCounter;
/**
 * This class provides a framework to set up a serialconnection. Subclasses need to implement the transmission protocol.
 * The Speed of the connection is observable via the SpeedListener-Interface.
 * Is observable by any class that implements SerialConnectionListener.
 * @author Felix
 */
public abstract class SerialConnection implements SpeedListener, ChannelcolorsListener, Closeable
{
	private static final int TIME_OUT = 2000; //TODO Understand this ...
	
	protected Channelgenerator _channelprovider;
	protected SerialPort _serialPort;
	protected BufferedOutputStream _serialOutputStream;
	
	protected boolean _connectionActive = false;
	
	private final List<SpeedListener> _speedListeners = new ArrayList<SpeedListener>();
	private final List<ActiveListener> _activeListeners = new ArrayList<ActiveListener>();
	
	/** SpeedCounter to measure the amount of packages sent per second */
	private final SpeedCounter _ppsCounter = new SpeedCounter();
	private int _pps;
	
	
			
	
	public SerialConnection(Channelgenerator channelprovider)
	{
		_ppsCounter.addSpeedChangeListener(this);
		setColorprovider(channelprovider);
	}
	
	
	
	/**
	 * Gives an Enumeration of CommPortIdentifiers from which one can be used as a parameter in the 'connect'-method.
	 */
	public Enumeration<CommPortIdentifier> getAvailablePorts()
	{
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		return portEnum;
	}
	
	/**
	 * Tries to establish a serialconnection with the given portId and baudRate and set the _serialOutputStream.
	 * If the connection could be established, _transmissionActive is set to true.
	 * @param portId a CommPortIdentifier-object, used for identifying and connecting to a port.
	 * @param baudRate This has to match the settings in the arduino-code. Recommended value: 155200.
	 * @throws PortInUseException, IllegalArgumentException
	 * @throws IllegalStateException if there is already a connection active
	 */
	public void connect(CommPortIdentifier portId, int baudRate) throws PortInUseException
	{
		if (_connectionActive)
		{
			//'connect' not possible, there is already a connection set up.
			throw new IllegalStateException("There is already a connection established");
		}
		
		try
		{
			_serialPort = (SerialPort) portId.open("ArduinoLight", TIME_OUT); //"ArduinoLight" is the appName //throws PortInUse
			_serialPort.setSerialPortParams(baudRate,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			_serialOutputStream = new BufferedOutputStream(_serialPort.getOutputStream());
			
			setActive(true); //using setActive to trigger event-firing
			_ppsCounter.reset();
		}
		catch (UnsupportedCommOperationException | IOException ex)
		{
			throw new IllegalArgumentException(ex);
		}
		
		debugprint("connect", "Connecting successful!");
	}
	
	/**
	 * Closes the connection.
	 */
	public void disconnect()
	{
		if (_serialPort != null)
		{
			_serialPort.close();
		}
		setActive(false);
		_ppsCounter.reset();
		debugprint("disconnect", "Disconnecting successful!");
	}
	
	/**
	 * private setActive method that fires a PropertyChangeEvent and gets used in the 'connect' and 'disconnect' methods.
	 */
	private void setActive(boolean value)
	{
		if (value != _connectionActive)
		{
			_connectionActive = value;
			fireActiveChangedEvent(_connectionActive);
		}
	}
	
	public void setColorprovider(Channelgenerator channelprovider)
	{
		if (channelprovider == null)
			throw new IllegalArgumentException();
		
		if (_channelprovider != null)
		{
			_channelprovider.removeChannelcolorsListener(this);
		}
		_channelprovider = channelprovider;
		_channelprovider.addChannelcolorsListener(this);
		_ppsCounter.reset();
	}
	
	/**
	 * Here, subclasses need to implement their concrete logic of getting bytes out of the list of colors.
	 * @return a byte[] that can be transmitted via a serial connection.
	 */
	protected abstract byte[] getBytesToTransmit(List<RGBColor> colors);
	
	protected synchronized void transmit(byte[] bytes)
	{
		debugprint("transmit", "transmit-method reached.");
		if (!_connectionActive)
		{
			debugprint("transmit", "connectionActive = false! transmission not possible.");
			throw new IllegalStateException("There is no connection established for transmission!");
		}
		
		try
		{
			_serialOutputStream.write(bytes);
			_serialOutputStream.flush();
			_ppsCounter.tick();
		}
		catch(IOException ex)
		{
			debugprint("transmit", "IOException" + ex.toString());
			//Convert checked Exception in unchecked Exception, as there is currently no way to recover from the exception. possibly TODO ...
			throw new IllegalStateException(ex);
		}
		debugprint("transmit", "transmission successful!");
	}
	
	//---------- Getters ---------------------------------------
	public String getPortName()
	{
		return _serialPort.getName();
	}
	
	public int getBaudRate()
	{
		return _serialPort.getBaudRate();
	}
	
	public int getSpeed()
	{
		return _pps;
	}
	
	public boolean IsActive()
	{
		return _connectionActive;
	}

	//---------- Event-Notify-Methods --------------------------
	/**
	 * Gets called by the Channelprovider that is subscribed.
	 * The colors of the channels that get passed are then transmitted.
	 */
	@Override
	public void channelcolorsUpdated(Object source, List<IChannel> refreshedChannellist)
	{
		if (!_connectionActive)
			return; //if the connection is not active, take no action.
		if (source != _channelprovider)
			return; //if the event was called by someone else than our subscribed channelprovider --> do nothing.
		if (refreshedChannellist == null || refreshedChannellist.size() == 0)
			return; //If the list is empty, there is nothing to transmit.
		
		int channelCount = refreshedChannellist.size();
		List<RGBColor> colors = new ArrayList<>(channelCount);
		
		for (IChannel channel : refreshedChannellist)
		{
			colors.add(channel.getColor());
		}
		
		byte[] bytes = getBytesToTransmit(colors);
		transmit(bytes);
	}

	/**
	 * Gets called by the _ppsCounter if a new Speed is calculated.
	 * The 'event' is then 'forwarded' to all Listeners.
	 */
	@Override
	public void speedChanged(Object source, int newSpeed)
	{
		_pps = newSpeed;
		fireSpeedChangedEvent(_pps);
	}
	
	//---------- Event-Firing ----------------------------------
	private void fireSpeedChangedEvent(int newSpeed)
	{
		for (SpeedListener listener : _speedListeners)
		{
			listener.speedChanged(this, newSpeed);
		}
	}
	
	public void fireActiveChangedEvent(boolean newActive)
	{
		for (ActiveListener listener : _activeListeners)
		{
			listener.activeChanged(this, newActive);
		}
	}
	
	
	//---------- Listener add/remove-methods -------------------
	public void addActiveListener(ActiveListener listener)
	{
		_activeListeners.add(listener);
	}
	
	public void removeActiveListener(ActiveListener listener)
	{
		_activeListeners.remove(listener);
	}
	
	public void addSpeedListener(SpeedListener listener)
	{
		_speedListeners.add(listener);
	}
	
	public void removeSpeedListener(SpeedListener listener)
	{
		_speedListeners.remove(listener);
	}
	
	//---------- Closeable-Interface ---------------------------
	@Override
	public void onCloseEvent()
	{
		disconnect();
	}
	
	//---------- Debug-Console-printing ------------------------
	/**
	 * prints, uses the DebugConsole.
	 * 'containingClass' is already preset.
	 */
	private void debugprint(String method, String message)
	{
		DebugConsole.print("SerialConnection", method, message);
	}
}
