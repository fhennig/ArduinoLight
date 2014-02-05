package arduinoLight.channelprovider;

import java.util.List;

import arduinoLight.channel.IChannel;

public interface ChannelcompositionListener
{
	public void channellistChanged(Object source, List<IChannel> newChannellist);
}
