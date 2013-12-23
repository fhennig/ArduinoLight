package arduinoLight.mixer;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.channelprovider.Channelprovider;
import arduinoLight.channelprovider.ChannelcolorsListener;
import arduinoLight.util.*;

/**
 * This Class uses a Channelprovider and provides the colorvalues of the channels to a ColorsUpdatedListener.
 * It 'translates' channels into colors.
 * It is the most basic implementation of a mixer and mainly a placeholder.
 * @author Felix
 */
public class SimpleMixer extends Colorprovider implements ChannelcolorsListener
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
			_channelprovider.removeChannelcolorsListener(this);
		}
		_channelprovider = channelprovider;
		_channelprovider.addChannelcolorsListener(this);
	}

	/**
	 * This method gets called by the Channelprovider that is subscribed.
	 * The Channels are converted into colors and a ColorsChangeEvent gets fired (Event gets 'forwarded').
	 */
	@Override
	public void channelcolorsUpdated(Object source, List<IChannel> refreshedChannellist)
	{
		if (source != _channelprovider)
			return;
		
		int channelCount = refreshedChannellist.size();
		List<RGBColor> colors = new ArrayList<>(channelCount);
		
		for (IChannel channel : refreshedChannellist)
		{
			colors.add(channel.getColor());
		}
		
		fireColorsUpdatedEvent(colors); //Event 'forwarding' takes place here.
	}
}
