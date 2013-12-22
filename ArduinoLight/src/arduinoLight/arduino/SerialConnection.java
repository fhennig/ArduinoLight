package arduinoLight.arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.*;

import arduinoLight.mixer.Colorprovider;
import arduinoLight.mixer.ColorsUpdatedListener;
import arduinoLight.util.DebugConsole;
import arduinoLight.util.RGBColor;
import arduinoLight.util.SpeedChangeListener;
import arduinoLight.util.SpeedCounter;
/**
 * This class provides a framework to set up a serialconnection. Subclasses need to implement the transmission protocol.
 * Is observable by any class that implements SerialConnectionListener.
 * @author Felix
 */
public abstract class SerialConnection implements SpeedChangeListener, ColorsUpdatedListener
{
	
	protected Colorprovider _colorprovider;
	
	private static final int TIME_OUT = 2000; //TODO Understand this ...
	protected SerialPort _serialPort;
	protected BufferedOutputStream _serialOutputStream;
	
	protected boolean _connectionActive = false;
	/** SpeedCounter to measure the amount of packages sent per second */
	private SpeedCounter _ppsCounter;
	private int _pps;
	
	private List<SerialConnectionListener> _listeners;
	
			
	
	public SerialConnection(Colorprovider colorprovider)
	{
		_listeners = new ArrayList<>();
		_ppsCounter = new SpeedCounter();
		setColorprovider(colorprovider);
	}
	
	
	
	/**
	 * Gives an enum of CommPortIdentifiers from which one can be used as a parameter in the 'connect'-method.
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
			setActive(true); //using setActive to trigger 'propertyChanged'
			_ppsCounter.reset();
		}
		catch (UnsupportedCommOperationException | IOException ex)
		{
			throw new IllegalArgumentException(ex);
		}
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
	}

	/**
	 * Gets called by the Colorprovider that is subscribed.
	 * As a reaction, the new colors are transmitted.
	 */
	@Override
	public void colorsUpdated(List<RGBColor> newColors)
	{
		//int i = 0;
		for (int i = 0; i < newColors.size(); i++)
		{
			debugprint("colorsUpdated", "new Color from Colorprovider:" + i + ": " + newColors.get(i).toString());
			i++;
		}
		byte[] bytes = getBytesToTransmit(newColors);
		transmit(bytes);
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
		debugprint("transmit", "transmission successfull! ###");
	}
	
	public void setColorprovider(Colorprovider colorprovider)
	{
		if (_colorprovider != null)
		{
			_colorprovider.removeColorsUpdatedListener(this);
		}
		_colorprovider = colorprovider;
		_colorprovider.addColorsUpdatedListener(this);
		fireColorproviderChangedEvent(_colorprovider);
		_ppsCounter.reset();
	}
	
	public Colorprovider getColorprovider()
	{
		return _colorprovider;
	}
	
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

	/**
	 * Gets called by the _ppsCounter if a new Speed is calculated.
	 * The 'event' is then 'forwarded' to all Listeners.
	 */
	@Override
	public void speedChanged(int newSpeed) {
		_pps = newSpeed;
		fireSpeedChangedEvent(_pps);
	}
	
	private void fireSpeedChangedEvent(int newSpeed)
	{
		for (SerialConnectionListener listener : _listeners)
		{
			listener.speedChanged(this, newSpeed);
		}
	}
	
	public void fireActiveChangedEvent(boolean newActive)
	{
		for (SerialConnectionListener listener : _listeners)
		{
			listener.activeChanged(this, newActive);
		}
	}
	
	public void fireColorproviderChangedEvent(Colorprovider cp)
	{
		for (SerialConnectionListener listener : _listeners)
		{
			listener.colorproviderChanged(this, cp);
		}
	}
	
	
	//PropertyChangeSupport:
	public void addSerialConnectionListener(SerialConnectionListener listener)
	{
		_listeners.add(listener);
	}
	
	public void removeSerialConnectionListener(SerialConnectionListener listener)
	{
		_listeners.remove(listener);
	}
	
	/**
	 * prints, uses the DebugConsole.
	 * 'containingClass' is already preset.
	 */
	private void debugprint(String method, String message)
	{
		DebugConsole.print("SerialConnection", method, message);
	}
}
