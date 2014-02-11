package arduinoLight.gui.comboboxitems;

public class DummyChannelholderItem extends ChannelholderItem
{
	@Override
	public String toString()
	{
		return "None";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof DummyChannelholderItem)
			return true;
		return false;
	}
}
