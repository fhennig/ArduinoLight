package arduinoLight.gui;

import arduinoLight.channelholder.Channelholder;

public class ChannelholderItem
{
	private final Channelholder _holder;
	
	public ChannelholderItem(Channelholder holder)
	{
		if (holder == null)
			throw new IllegalArgumentException();
		
		_holder = holder;
	}
	
	protected ChannelholderItem()
	{
		_holder = null;
	}
	
	public Channelholder getChannelholder()
	{
		return _holder;
	}
	
	@Override
	public String toString()
	{
		return _holder.getChannelsDescription();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof ChannelholderItem))
		{
			return false;
		}
		ChannelholderItem other = (ChannelholderItem) obj;
		if (_holder == null)
		{
			if (other._holder != null)
			{
				return false;
			}
		} else if (!_holder.equals(other._holder))
		{
			return false;
		}
		return true;
	}
}
