package arduinoLight.channelprovider;

import arduinoLight.channel.IChannel;
import arduinoLight.channel.ThreadingChannel;
import arduinoLight.threading.Refreshqueue;

public class ChannelFactory
{
	public static IChannel getNewChannel()
	{
		return new ThreadingChannel();
	}
}
