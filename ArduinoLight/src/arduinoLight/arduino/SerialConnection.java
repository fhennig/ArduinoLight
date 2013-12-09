package arduinoLight.arduino;

import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedOutputStream;
import java.io.IOException;

import arduinoLight.colorprovider.Colorprovider;

public abstract class SerialConnection
{
	protected Colorprovider _colorprovider;
	protected SerialPort _serialPort;
	protected BufferedOutputStream _serialOutputStream;
	protected int _baudRate;
	protected boolean _transmissionActive;
	
	public SerialConnection(Colorprovider c)
	{
		_colorprovider = c;
		_baudRate = 115200;
		_transmissionActive = false;
	}
	
	public void connect(String portName) throws IOException
	{
		try
		{
			_serialPort.setSerialPortParams(_baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			_serialOutputStream = new BufferedOutputStream(_serialPort.getOutputStream());
			_transmissionActive = true;
		}
		catch (UnsupportedCommOperationException ex)
		{
			throw new IOException();		//TODO better exception management
		}
	}
	
	public void disconnect()
	{
		_serialPort.close();
		_transmissionActive = false;
	}
	
	public int getBaudRate()
	{
		return _baudRate;
	}
	
	public void setBaudRate(int newBaudRate)
	{
		_baudRate = newBaudRate;
	}
	
	public void setColorprovider(Colorprovider cp)
	{
		_colorprovider.setActive(false);
		unsubscribeEvents();
		_colorprovider = cp;
		subscribeEvents();
		_colorprovider.setActive(_transmissionActive);
	}
	
	protected void transmit(byte[] bytes)
	{
		try
		{
			_serialOutputStream.write(bytes);
			_serialOutputStream.flush();
			//TODO packagePerSecond counter (pps)
		}
		catch(IOException ex)
		{
			//TODO exceptionmanagement ... write to log / console
		}
	}
	
	protected abstract void subscribeEvents();
	protected abstract void unsubscribeEvents();
	
}
