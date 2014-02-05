package arduinoLight.channelprovider;

import java.util.List;

import arduinoLight.channel.IChannel;
import arduinoLight.interfaces.propertyListeners.ActiveListener;

public class ChannelproviderListenerDummy implements ChannelcolorsListener, ChannelcompositionListener, ActiveListener
{
	private List<IChannel> _latestChannels;
	private boolean _latestActiveState;
	
	private int _amountColorChangedEvents = 0;
	private int _amountActiveChangedEvents = 0;
	private int _amountChannelsChangedEvents = 0;
	
	private Object _latestSource;
	
	
	@Override
	public void channelcolorsUpdated(Object source, List<IChannel> refreshedChannellist)
	{
		_latestChannels = refreshedChannellist;
		_latestSource = source;
		_amountColorChangedEvents++;
	}

	@Override
	public void activeChanged(Object source, boolean newActive)
	{
		_latestActiveState = newActive;
		_latestSource = source;
		_amountActiveChangedEvents++;
	}

	public List<IChannel> getLatestChannels()
	{
		return _latestChannels;
	}

	public boolean getLatestActiveState()
	{
		return _latestActiveState;
	}

	public Object getLatestSource()
	{
		return _latestSource;
	}

	public int getAmountColorChangedEvents()
	{
		return _amountColorChangedEvents;
	}

	public int getAmountActiveChangedEvents()
	{
		return _amountActiveChangedEvents;
	}
	
	public int getAmountChannelsChangedEvents()
	{
		return _amountChannelsChangedEvents;
	}

	@Override
	public void channellistChanged(Object source, List<IChannel> newChannellist)
	{
		_amountChannelsChangedEvents++;
	}
}
