package arduinoLight.channelprovider.generator.customColors;

import arduinoLight.channel.Channel;
import arduinoLight.channelprovider.generator.Channelgenerator;
import arduinoLight.util.Color;

public class CustomColorsProvider extends Channelgenerator
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
	
	public void setChannelcolor(Channel channel, Color color)
	{
		Channel foundChannel = null;
		
		for (Channel c : _channels)
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
