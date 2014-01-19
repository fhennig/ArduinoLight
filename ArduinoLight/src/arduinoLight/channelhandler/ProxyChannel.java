package arduinoLight.channelhandler;

import java.util.concurrent.atomic.AtomicInteger;

import arduinoLight.util.Color;
import arduinoLight.util.IChannel;

public class ProxyChannel implements IChannel
{
	private static final AtomicInteger _instances = new AtomicInteger(0); //Used to generate Ids.
	private final int _id;
	private Color _color;
	private ProxyChannel _futureSelf;
	
	public ProxyChannel()
	{
		this(new Color());
	}
	
	public ProxyChannel(Color color)
	{
		_id = _instances.getAndIncrement();
		_color = color;
		//TODO Verbindung mit dem Handler etc.
	}
	
	@Override
	public Color getColor()
	{
		return _color;
	}

	@Override
	public void setColor(Color color)
	{
		_color = color;
		//TODO encapsule in state object
	}

	@Override
	public int getId()
	{
		return _id;
	}
	
	private class ProxyChannelState
	{
		private Color _color;

		public Color getColor()
		{
			return _color;
		}

		public void setColor(Color color)
		{
			this._color = color;
		}
	}
}