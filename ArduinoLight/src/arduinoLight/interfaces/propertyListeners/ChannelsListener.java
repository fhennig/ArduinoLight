package arduinoLight.interfaces.propertyListeners;

import java.util.List;

import arduinoLight.channel.IChannel;

public interface ChannelsListener
{
	public void channelsChanged(Object source, List<IChannel> newChannels);
}
