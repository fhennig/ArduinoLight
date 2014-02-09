package arduinoLight.interfaces.propertyListeners;

import java.util.List;

import arduinoLight.channel.Channel;

public interface ChannelsListener
{
	public void channelsChanged(Object source, List<Channel> newChannels);
}
