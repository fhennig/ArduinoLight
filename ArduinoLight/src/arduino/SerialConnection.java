package arduino;

import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;

import colorprovider.Colorprovider;

public abstract class SerialConnection
{
	protected Colorprovider _colorprovider;
	protected SerialPort _serialPort;
	protected BufferedOutputStream _serialOutputStream;
	protected int _baudRate = 115200;
	protected boolean _transmissionActive = false;
	
	public SerialConnection(Colorprovider c)
	{
		_colorprovider = c;
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
	
	protected abstract void subscribeEvents();
	protected abstract void unsubscribeEvents();
	
	protected abstract void transmit();
}
