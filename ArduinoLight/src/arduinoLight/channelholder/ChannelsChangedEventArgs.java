package arduinoLight.channelholder;

import arduinoLight.channel.Channel;

public class ChannelsChangedEventArgs
{
	private Object _source;
	private Channel _removedChannel;
	private Channel _addedChannel;
	
	public ChannelsChangedEventArgs(Object source, Channel removed, Channel added)
	{
		if (source == null)
			throw new IllegalArgumentException("source cannot be null!");
		if (removed == null && added == null)
			throw new IllegalArgumentException("'removed' and 'added' were both null, only one can be null!");
		
		_source = source;
		_removedChannel = removed;
		_addedChannel = added;
	}
	
	public Channel getRemovedChannel()
	{
		return _removedChannel;
	}
	
	public Channel getAddedChannel()
	{
		return _addedChannel;
	}
	
	public Object getSource()
	{
		return _source;
	}
}
