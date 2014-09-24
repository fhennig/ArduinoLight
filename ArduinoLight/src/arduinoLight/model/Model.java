package arduinoLight.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import arduinoLight.channel.Channel;
import arduinoLight.channelholder.Channelholder;
import arduinoLight.channelholder.ChannelsChangedListener;
import arduinoLight.channelholder.ambientlight.Ambientlight;
import arduinoLight.channelholder.colorpicker.ColorPicker;

/**
 * An important core class of this project.
 * Is a singleton an provides access to a ChannelFactory and other important Model objects.
 * @author Felix
 *
 */
public class Model
{
	public static Model _instance = null;
	
	private ChannelFactory _channelFactory = new ChannelFactory();
	private Ambientlight _ambientlight = new Ambientlight();
	private ColorPicker _colorPicker = new ColorPicker();
	private Channelholder _unusedChannels = new UnusedChannels();
	private List<Channelholder> _channelwriters = new ArrayList<>();
	
	/** private constructor because this is a singleton */
	private Model()
	{
		//Initialize the List of Channelwriters:
		_channelwriters.add(_ambientlight);
		_channelwriters.add(_colorPicker);
	}
	
	/** static method to get the current instance of this singleton */
	public static Model getInstance()
	{
		if (_instance == null)
		{
			_instance = new Model();
		}
		return _instance;
	}
	
	public ChannelFactory getChannelFactory()
	{
		return _channelFactory;
	}
	
	public Channelholder getUnusedChannels()
	{
		return _unusedChannels;
	}
	
	public Ambientlight getAmbientlight()
	{
		return _ambientlight;
	}
	
	public ColorPicker getColorPicker()
	{
		return _colorPicker;
	}
	
	/**
	 * The returned List currently does not change, as Channelholders 
	 * cannot be added or removed.
	 */
	public List<Channelholder> getChannelholders()
	{
		List<Channelholder> channelHolders = new ArrayList<Channelholder>();
		channelHolders.add(_channelFactory); //ChannelFactory at the beginning of the list
		channelHolders.addAll(_channelwriters);
		channelHolders.add(_unusedChannels);
		return channelHolders;
	}
	
	/**
	 * Searches for a Channelholder that uses the specified channel.
	 * If the channel is not being written to currently, an instance of UnusedChannels is returned. 
	 */
	public Channelholder getChannelholder(Channel channel)
	{
		for (Channelholder cwriter : _channelwriters)
		{
			if (cwriter.getChannels().contains(channel))
				return cwriter;
		}
		
		return _unusedChannels;
	}
	
	private class UnusedChannels implements Channelholder
	{
		@Override
		public Set<Channel> getChannels()
		{
			Set<Channel> unusedChannels = new HashSet<Channel>(getChannelFactory().getChannels());
			
			for (Channelholder channelwriter : _channelwriters)
			{
				unusedChannels.removeAll(channelwriter.getChannels());
			}
			
			return unusedChannels;
		}

		@Override
		public String getChannelsDescription()
		{
			return "Unused Channels";
		}

		@Override
		public void addChannelsChangedListener(ChannelsChangedListener listener)
		{
			//TODO
		}

		@Override
		public void removeChannelsChangedListener(ChannelsChangedListener listener)
		{
			//TODO
		}
	}
}
