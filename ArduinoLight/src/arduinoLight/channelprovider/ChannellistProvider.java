package arduinoLight.channelprovider;

import java.util.List;

import arduinoLight.channel.Channel;

public interface ChannellistProvider
{
	public void addChannel();
	public void removeChannel(Channel channel);
	public List<Channel> getChannels();
	
	public void addChannellistListener(ChannelcompositionListener listener);
	public void removeChannellistListener(ChannelcompositionListener listener);
}
