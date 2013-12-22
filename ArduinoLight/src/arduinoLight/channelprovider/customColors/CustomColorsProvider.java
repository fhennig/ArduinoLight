package arduinoLight.channelprovider.customColors;

import arduinoLight.channelprovider.Channelprovider;
import arduinoLight.util.IChannel;
import arduinoLight.util.Color;

public class CustomColorsProvider extends Channelprovider
{
	@Override
	protected boolean activate()
	{
		return true;
	}

	@Override
	protected boolean deactivate()
	{
		return true;
	}
	
	public void setChannelcolor(IChannel channel, Color color)
	{
		IChannel foundChannel = null;
		
		for (IChannel c : _channels)
		{
			if (c == channel)
			{
				foundChannel = c;
			}
		}
		
		if (foundChannel == null)
			throw new IllegalArgumentException(); //Illegal IChannel passed. 
		
		foundChannel.setColor(color);
		if (IsActive()) //Only fire the event if the module is active.
			fireChannelcolorsUpdatedEvent();
	}
}
