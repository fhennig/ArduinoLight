package arduinoLight.gui.comboboxitems;

import arduinoLight.channel.Channel;

public class ChannelItem
{
	private final Channel _channel;
	
	public ChannelItem(Channel channel)
	{
		if (channel == null)
			throw new IllegalArgumentException();
		_channel = channel;
	}
	
	/** Used to create a DummyChannelItem */
	protected ChannelItem()
	{
		_channel = null;
	}
	
	public Channel getChannel()
	{
		return _channel;
	}
	
	@Override
	public String toString()
	{
		return _channel.getName() + " [" + Integer.toString(_channel.getId()) + "]";
	}

//	@Override
//	public boolean equals(Object obj)
//	{
//		if (this == obj)
//		{
//			return true;
//		}
//		if (obj == null)
//		{
//			return false;
//		}
//		if (!(obj instanceof ChannelItem))
//		{
//			return false;
//		}
//		ChannelItem other = (ChannelItem) obj;
//		if (_channel == null)
//		{
//			if (other._channel != null)
//			{
//				return false;
//			}
//		} else if (!_channel.equals(other._channel))
//		{
//			return false;
//		}
//		return true;
//	}
}
