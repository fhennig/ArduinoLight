package arduinoLight.channelprovider.modifier;

import java.util.List;

import arduinoLight.channelprovider.Channelprovider;
import arduinoLight.interfaces.propertyListeners.ChannelsListener;
import arduinoLight.util.IChannel;

public abstract class ProviderDecorator extends Channelprovider implements ChannelsListener
{
	private Channelprovider _provider;
	
	public ProviderDecorator(Channelprovider channelprovider)
	{
		_provider = channelprovider;
		
	}
	
	@Override
	public void channelsChanged(Object source, List<IChannel> newChannels)
	{
		if (source != _provider)
			return;
		
		updateChannels(newChannels);
		
		fireChannelsUpdatedEvent();
	}
	
	protected abstract void updateChannels(List<IChannel> newChannels);
}
