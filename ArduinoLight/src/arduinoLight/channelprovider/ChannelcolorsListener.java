package arduinoLight.channelprovider;

import java.util.List;

import arduinoLight.util.*;

/**
 * This interface should be implemented by a Mixer, that can then react to the channelsUpdatedEvent and sent the colors via an Arduinoconnection
 * This interface is part of an implementation of the Observerpattern, it makes a Channelprovider observable.
 * @author Felix
 */
public interface ChannelcolorsListener
{
	/**
	 * Gets called if the colors are refreshed. Does not get called if a channel is added or removed.
	 */
	public void channelcolorsUpdated(Object source, List<IChannel> refreshedChannellist);
}
