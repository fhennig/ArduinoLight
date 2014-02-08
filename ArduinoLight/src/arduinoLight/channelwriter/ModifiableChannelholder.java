package arduinoLight.channelwriter;

import arduinoLight.channel.IChannel;

public interface ModifiableChannelholder extends Channelholder
{
	public void addChannel(IChannel channel);
	public void removeChannel(IChannel channel);
}
