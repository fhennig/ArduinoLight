package arduinoLight.channelholder;

/**
 * Interface for objects that want to listen to changes in channel composition
 * in a Channelholder object.
 */
public interface ChannelsChangedListener
{
	/**
	 * This method is called if a subscribed Channelholder changes its channel
	 * composition. That means, if channels are added or removed.
	 */
	public void channelsChanged(ChannelsChangedEventArgs e);
}
