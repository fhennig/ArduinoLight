package arduinoLight.arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
 * Provides PropertyChangeSupport for: "Colorprovider", "Active", "Speed"
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
	
	PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
	//TODO remove propertychangesupport
	
	public SerialConnection(Colorprovider colorprovider)
	{
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
		int i = 0;
		for (RGBColor color : newColors)
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
		_propertyChangeSupport.firePropertyChange("Colorprovider", _colorprovider, colorprovider);
		_colorprovider = colorprovider;
		_colorprovider.addColorsUpdatedListener(this);
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
			boolean oldValue = _connectionActive;
			_connectionActive = value;
			_propertyChangeSupport.firePropertyChange("Active", oldValue, value);
		}
	}

	/**
	 * Gets called by the _ppsCounter if a new Speed is calculated.
	 * The 'event' is then 'forwarded' to all PropertyChangeListeners.
	 */
	@Override
	public void speedChanged(int newSpeed) {
		int oldSpeed = _pps;
		_pps = newSpeed;
		_propertyChangeSupport.firePropertyChange("Speed", oldSpeed, _pps);
	}
	
	
	
	//PropertyChangeSupport:
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		_propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		_propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	private void debugprint(String method, String message)
	{
		DebugConsole.print("SerialConnection", method, message);
	}
}
