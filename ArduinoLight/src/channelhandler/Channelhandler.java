package channelhandler;

import java.util.HashSet;
import java.util.Set;

public class Channelhandler
{
	private Channelhandler _instance = null;
	private Set<ProxyChannel> _channels = new HashSet<>();
	
	private Channelhandler()
	{}
	
	public Channelhandler getInstance()
	{
		if (_instance == null)
		{
			_instance = new Channelhandler();
		}
		return _instance;
	}
	
	//public IChannel 
}
