package arduinoLight.channelholder;

import java.util.*;

import arduinoLight.channel.Channel;

/**
 * This type represents an object that stores channels.
 */
public interface Channelholder
{
	/**
	 * This method returns an unmodifiableList of
	 * the channels that are used for writing. 
	 */
	public Set<Channel> getChannels();
	
	
	
	/**
	 * @return  a string that can be used as a name for the Set of Channels. 
	 */
	public String getChannelsDescription();
	
	
	
	/**
	 * A listener gets notified of a Channel is added or removed from the Channelholder.
	 */
	public void addChannelsChangedListener(ChannelsChangedListener listener);
	
	
	
	public void removeChannelsChangedListener(ChannelsChangedListener listener);
}
