package arduinoLight.channelholder;

import arduinoLight.channel.Channel;

/**
 * Interface for Channelholders that also support adding and removing channels.
 * This type is used by the GUI.
 */
public interface ModifiableChannelholder extends Channelholder
{
	/**
	 * Adds a channel to the Channelholder.
	 * @param channel  the channel that should be added.
	 */
	public void addChannel(Channel channel);
	
	
	
	/**
	 * Removes a channel from the channelholder, if the channel is owned by the Channelholder.
	 * @param channel  the channel that should be removed.
	 */
	public void removeChannel(Channel channel);
}
