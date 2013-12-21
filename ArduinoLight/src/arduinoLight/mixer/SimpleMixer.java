package arduinoLight.mixer;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.channelprovider.Channelprovider;
import arduinoLight.channelprovider.ChannelsUpdatedListener;
import arduinoLight.util.*;

/**
 * This Class uses a Channelprovider and provides the colorvalues of the channels to a ColorsUpdatedListener.
 * It 'translates' channels into colors.
 * It is the most basic implementation of a mixer and mainly a placeholder.
 * @author Felix
 */
public class SimpleMixer extends Colorprovider implements ChannelsUpdatedListener
{
	private Channelprovider _channelprovider;
	
	public SimpleMixer(Channelprovider channelprovider)
	{
		setChannelprovider(channelprovider);
	}
	
	public void setChannelprovider(Channelprovider channelprovider)
	{
		if (_channelprovider != null)
		{
			_channelprovider.removeChannelsUpdatedListener(this);
		}
		_channelprovider = channelprovider;
		_channelprovider.addChannelsUpdatedListener(this);
	}

	/**
	 * This method gets called by the Channelprovider that is subscribed.
	 * The Channels are converted into colors and a ColorsChangeEvent gets fired.
	 */
	@Override
	public void channelsUpdated(List<IChannel> refreshedChannellist)
	{
		int channelCount = refreshedChannellist.size();
		List<RGBColor> colors = new ArrayList<>(channelCount);
		
		for (IChannel channel : refreshedChannellist)
		{
			colors.add(channel.getColor());
		}
		
		fireColorsUpdatedEvent(colors);
	}
	
}
