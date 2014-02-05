package arduinoLight.arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.util.*;

import arduinoLight.channel.IChannel;
import arduinoLight.channelprovider.ChannelcolorsListener;
import arduinoLight.channelprovider.generator.Channelgenerator;
import arduinoLight.interfaces.Closeable;
import arduinoLight.interfaces.propertyListeners.ActiveListener;
import arduinoLight.util.RGBColor;
/**
 * This class provides a framework to set up a serialconnection. Subclasses need to implement the transmission protocol.
 * The Speed of the connection is observable via the SpeedListener-Interface.
 * Is observable by any class that implements SerialConnectionListener.
 * @author Felix
 */
public abstract class SerialConnectionOld implements ChannelcolorsListener, Closeable
{
	protected Channelgenerator _channelprovider;
	
	protected boolean _connectionActive = false;
	
	private final List<ActiveListener> _activeListeners = new ArrayList<ActiveListener>();
	
	private SerialConnection _connection;
	
	//TODO special for amblone: use hashmap with 4 entries.
	//TODO add stuff to set refreshfrequency
			
	
	public SerialConnectionOld(Channelgenerator channelprovider)
	{
		setColorprovider(channelprovider);
		_connection = new SerialConnection();
	}
	
	
	
	/**
	 * Gives an Enumeration of CommPortIdentifiers from which one can be used as a parameter in the 'connect'-method.
	 */
	public static Enumeration<CommPortIdentifier> getAvailablePorts()
	{
		return SerialConnection.getAvailablePorts();
	}
	 
	public void connect(CommPortIdentifier portId, int baudRate) throws PortInUseException
	{
		_connection.open(portId, baudRate);
		setActive(true);
	}
	
	//TODO call this shutdown and encapsule
	/**
	 * Closes the connection.
	 */
	public void disconnect()
	{
		_connection.close();
		setActive(false);
	}
	
	//TODO this as a private method looks confusing
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
	
	//TODO this should be replaced by different logic (hasmap with ints, channels)
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
	}
	
	/**
	 * Here, subclasses need to implement their concrete logic of getting bytes out of the list of colors.
	 * @return a byte[] that can be transmitted via a serial connection.
	 */
	protected abstract byte[] getBytesToTransmit(List<RGBColor> colors);
	
	protected synchronized void transmit(byte[] bytes)
	{
		_connection.transmit(bytes);
	}
	
	//---------- Getters --------------------------------------- //TODO these all move in the new class
	public String getPortName()
	{
		return _connection.getPortName();
	}
	
	public int getBaudRate()
	{
		return _connection.getBaudRate();
	}
	
	public boolean IsActive()
	{
		return _connection.isOpen();
	}

	//---------- Event-Notify-Methods --------------------------
	//TODO this will obsolete
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
	
	//---------- Closeable-Interface ---------------------------
	@Override
	public void onCloseEvent()
	{
		disconnect();
	}
}
