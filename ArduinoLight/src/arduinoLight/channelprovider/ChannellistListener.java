package arduinoLight.channelprovider;

import java.util.List;

import arduinoLight.util.IChannel;

public interface ChannellistListener
{
	public void channellistChanged(Object source, List<IChannel> newChannellist);
}
