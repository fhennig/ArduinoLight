package arduinoLight.channelprovider;

import java.util.List;

import arduinoLight.util.IChannel;

public interface ChannellistProvider
{
	public void addChannel();
	public void removeChannel(IChannel channel);
	public List<IChannel> getChannels();
	
	public void addChannellistListener(ChannellistListener listener);
	public void removeChannellistListener(ChannellistListener listener);
}
