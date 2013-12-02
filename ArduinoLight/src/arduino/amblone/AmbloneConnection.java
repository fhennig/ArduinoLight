package arduino.amblone;

import gnu.io.*;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.IRGBColor;

/**
 * This is not complete! TODO !
 * This class is responsible for sending IRGBColor-input through a serialport to the arduino.
 * @author felix
 */
public class AmbloneConnection
{
	private SerialPort _serialPort;
	private BufferedOutputStream _serialOutputStream;
	private int _baudRate = 115200;
	
	private final int _maxChannels = 4;
	private List<IRGBColor> _colors;
	
	private boolean _transmissionActive = false;
	
	public AmbloneConnection()
	{
		_colors = new ArrayList<IRGBColor>();
	}
	
	public void setChannel(int channelIndex, IRGBColor c)
	{
		_colors.set(channelIndex, c);
	}
	
	public void setChannels(List<IRGBColor> channels)
	{
		if (channels.size() > _maxChannels)
			throw new IndexOutOfBoundsException();
		else
			_colors = channels;
	}
	
	public void connect(String portName) throws IOException
	{
		try
		{
			_serialPort.setSerialPortParams(_baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			_serialOutputStream = new BufferedOutputStream(_serialPort.getOutputStream());
		}
		catch (UnsupportedCommOperationException ex)
		{
			throw new IOException();		//TODO better exception management
		}
	}
	
	public void disconnect()
	{
		_serialPort.close();
	}
	
	public void startTransmission()
	{
		_transmissionActive = true;
		//TODO new Thread with transmit()
	}
	
	public void stopTransmission()
	{
		_transmissionActive = false;
		//TODO create Interface and a lot of events
	}
	
	
	
	/**
	 * This method transmits the color-values from _color through the _serialOutputStream to the arduino.
	 */
	private void transmit()
	{
		byte[] packageAsArray;
		while (_transmissionActive) //TODO test if port is open
		{
			packageAsArray = new AmblonePackage(_colors).toByteArray();
			try
			{
				_serialOutputStream.write(packageAsArray);
				_serialOutputStream.flush();
				//TODO packagePerSecond counter (pps)
			}
			catch(IOException ex)
			{
				//TODO write to log / console
			}

		}
	}
}
