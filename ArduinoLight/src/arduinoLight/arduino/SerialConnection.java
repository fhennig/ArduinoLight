package arduinoLight.arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedOutputStream;
import java.util.Enumeration;


//TODO Thread needs to be cancelable
public class SerialConnection
{
	protected SerialPort _serialPort;
	protected BufferedOutputStream _serialOutputStream;
	
	/**
	 * Gives an Enumeration of CommPortIdentifiers from which one can be used as a parameter in the 'connect'-method.
	 */
	public static Enumeration<CommPortIdentifier> getAvailablePorts()
	{
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		return portEnum;
	}
	
	
	
}
