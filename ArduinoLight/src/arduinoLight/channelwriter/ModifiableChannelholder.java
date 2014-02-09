package arduinoLight.channelwriter;

import arduinoLight.channel.Channel;

public interface ModifiableChannelholder extends Channelholder
{
	public void addChannel(Channel channel);
	public void removeChannel(Channel channel);
}
