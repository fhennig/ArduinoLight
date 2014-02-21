package arduinoLight.arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import arduinoLight.util.DebugConsole;


/**
 * This class encapsulates a serial connection. It provides a simple interface (open, close, transmit). 
 */
public class SerialConnection
{
	private static final int TIME_OUT = 2000; //TODO Understand this ...
	
	protected SerialPort _serialPort;
	protected BufferedOutputStream _serialOutputStream;
	protected boolean _open = false;
	
	/**
	 * Gives an Enumeration of CommPortIdentifiers from which one can be used as a parameter in the 'connect'-method.
	 */
	public static Enumeration<CommPortIdentifier> getAvailablePorts()
	{
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		return portEnum;
	}
	
	
	/**
	 * Tries to establish a serialconnection with the given portId and baudRate and set the _serialOutputStream.
	 * If the connection could be established, _active is set to true.
	 * @param portId a CommPortIdentifier-object, used for identifying and connecting to a port.
	 * @param baudRate This has to match the settings in the arduino-code. Recommended value: 256000.
	 * @throws PortInUseException, IllegalArgumentException
	 * @throws IllegalStateException if there is already a connection active
	 */
	public void open(CommPortIdentifier portId, int baudRate) throws PortInUseException
	{
		if (_open)
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
			_open = true;
			DebugConsole.print("SerialConnection", "connect", "Connecting successful!");
		}
		catch (UnsupportedCommOperationException | IOException ex)
		{
			throw new IllegalArgumentException(ex);
		}
		
	}
	
	/** Closes the connection. */
	public void close()
	{
		if (!_open)
			return; //the connection is already closed.
		
		_serialPort.close();
		_serialPort = null;
		try { _serialOutputStream.close(); } catch (IOException ignored) { ignored.printStackTrace(); }
		_serialOutputStream = null;
		_open = false;
		DebugConsole.print("SerialConnection", "disconnect", "Disconnecting successful!");
	}
	
	/**
	 * If the connection is open, the given bytes are transmitted.
	 * If the connection is closed, IllegalStateException is thrown.
	 * @throws IllegalStateException if transmission failed, or the connection is closed.
	 * @param bytes the bytes to transmit.
	 */
	public void transmit(byte[] bytes)
	{
		if (!_open)
		{
			debugprint("transmit", "SerialConnection is not open, transmission not possible.");
			throw new IllegalStateException("There is no connection established for transmission!");
		}
		
		try
		{
			_serialOutputStream.write(bytes);
			_serialOutputStream.flush();
		}
		catch(IOException ex)
		{
			debugprint("transmit", "IOException" + ex.toString());
			//Convert checked Exception in unchecked Exception, as there is currently no way to recover from the exception.
			//TODO add an UncheckedExceptionHandler to the containing thread so the UI will be notified if an error occurs
			throw new IllegalStateException(ex);
		}
	}
	
	//---------- Getters ---------------------------------------
	public String getPortName()
	{
		if (_serialPort == null)
			return "";
		return _serialPort.getName();
	}
	
	public int getBaudRate()
	{
		if (_serialPort == null)
			return 0;
		return _serialPort.getBaudRate();
	}
	
	public boolean isOpen()
	{
		return _open;
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
