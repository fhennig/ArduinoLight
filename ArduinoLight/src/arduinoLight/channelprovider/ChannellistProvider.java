package arduinoLight.channelprovider;

import java.util.List;

import arduinoLight.channel.IChannel;

public interface ChannellistProvider
{
	public void addChannel();
	public void removeChannel(IChannel channel);
	public List<IChannel> getChannels();
	
	public void addChannellistListener(ChannelcompositionListener listener);
	public void removeChannellistListener(ChannelcompositionListener listener);
}
