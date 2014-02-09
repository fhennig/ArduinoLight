package arduinoLight.channelprovider;

import java.util.List;

import arduinoLight.channel.Channel;

public interface ChannelcompositionListener
{
	public void channellistChanged(Object source, List<Channel> newChannellist);
}
