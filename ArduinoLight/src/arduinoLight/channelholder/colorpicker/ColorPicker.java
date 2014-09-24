package arduinoLight.channelholder.colorpicker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import arduinoLight.channel.Channel;
import arduinoLight.channelholder.ChannelsChangedEventArgs;
import arduinoLight.channelholder.ChannelsChangedListener;
import arduinoLight.channelholder.ModifiableChannelholder;

public class ColorPicker implements ModifiableChannelholder
{
	private final Set<Channel> _channels = new HashSet<>();
	private final List<ChannelsChangedListener> _listeners = new ArrayList<>();

	@Override
	public Set<Channel> getChannels()
	{
		return Collections.unmodifiableSet(_channels);
	}

	@Override
	public String getChannelsDescription()
	{
		return "Color Picker Channels";
	}

	@Override
	public void addChannelsChangedListener(ChannelsChangedListener listener)
	{
		_listeners.add(listener);
	}

	@Override
	public void removeChannelsChangedListener(ChannelsChangedListener listener)
	{
		_listeners.remove(listener);
	}

	@Override
	public void addChannel(Channel channel)
	{
		boolean wasAdded = _channels.add(channel);
		if (wasAdded)
			fireChannelsChangedEvent(new ChannelsChangedEventArgs(this, null, channel));
	}

	@Override
	public void removeChannel(Channel channel)
	{
		boolean wasRemoved = _channels.remove(channel);
		if (wasRemoved)
			fireChannelsChangedEvent(new ChannelsChangedEventArgs(this, channel, null));
	}

	
	/** Event is not fired concurrently */
	private void fireChannelsChangedEvent(ChannelsChangedEventArgs e)
	{
		for (ChannelsChangedListener l : _listeners)
			l.channelsChanged(e);
	}
}
