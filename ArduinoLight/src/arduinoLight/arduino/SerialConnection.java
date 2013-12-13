package arduinoLight.arduino;

import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;

import arduinoLight.colorprovider.Colorprovider;
import arduinoLight.colorprovider.ColorsUpdatedListener;
import arduinoLight.util.IRGBColor;

public abstract class SerialConnection implements ColorsUpdatedListener
{
	protected Colorprovider _colorprovider;
	protected SerialPort _serialPort;
	protected BufferedOutputStream _serialOutputStream;
	protected int _baudRate;
	protected boolean _transmissionActive;
	
	public SerialConnection(Colorprovider c)
	{
		_colorprovider = c;
		_transmissionActive = false;
	}
	
	/**
	 * Tries to establish a serialconnection with the given portName and set the _serialOutputStream.
	 * If the connection could be established, _transmissionActive is set to true.
	 * @param portName The port that you want to connect to.
	 * @param baudRate recommended value: 155200.
	 */
	public void connect(String portName, int baudRate)
	{
		try
		{
			_serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			_serialOutputStream = new BufferedOutputStream(_serialPort.getOutputStream());
			_transmissionActive = true;
		}
		catch (UnsupportedCommOperationException | IOException ex)
		{
			throw new IllegalArgumentException(ex);
		}
	}
	
	public void disconnect()
	{
		_serialPort.close();
		_transmissionActive = false;
	}
	
	public void setColorprovider(Colorprovider cp)
	{
		if (_colorprovider != null)
		{
			_colorprovider.setActive(false);
			_colorprovider.removeColorsUpdatedListener(this);
		}
		_colorprovider = cp;
		_colorprovider.addColorsUpdatedListener(this);
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
			throw new IllegalStateException(ex);
		}
	}
	
	public abstract void colorsUpdated(List<IRGBColor> newColors); //Written explicit here, as a reminder that subclasses have to implement this Interface.
}
