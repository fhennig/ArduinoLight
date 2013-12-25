package arduinoLight.channelprovider.customColors;

import static org.junit.Assert.*;

import org.junit.*;

import arduinoLight.channelprovider.ChannelproviderListenerDummy;
import arduinoLight.channelprovider.generator.customColors.CustomColorsProvider;
import arduinoLight.util.Color;

public class CustomColorsProviderTest
{
	private CustomColorsProvider _provider;
	private ChannelproviderListenerDummy _listener;
	
	@Before
	public void setup()
	{
		_provider = new CustomColorsProvider();
		_listener = new ChannelproviderListenerDummy();
		_provider.addChannelcolorsListener(_listener);
		_provider.addChannellistListener(_listener);
		_provider.addActiveStateListener(_listener);
	}
	
	@Test
	public void testAddingRemovingChannels()
	{
		_provider.addChannel();
		
		assertEquals(1, _provider.getChannels().size());
		assertEquals(1, _listener.getAmountChannelsChangedEvents());
		assertEquals(0, _listener.getAmountActiveChangedEvents());
		assertEquals(0, _listener.getAmountColorChangedEvents());
		
		
		_provider.removeChannel(_provider.getChannels().get(0));
		
		assertEquals(0, _provider.getChannels().size());
		assertEquals(2, _listener.getAmountChannelsChangedEvents());
		assertEquals("ColorsChangedEvent should fire if a channel is removed", 1, _listener.getAmountColorChangedEvents());
	}
	
	@Test
	public void testActivate()
	{
		_provider.addChannel();
		_provider.addChannel();
		
		_provider.setActive(true);
		
		assertTrue(_provider.IsActive());
		assertTrue(_listener.getLatestActiveState());
		
		assertFalse(_provider.setActive(true));
		
		assertTrue(_provider.setActive(false));
		
		assertEquals(2, _listener.getAmountActiveChangedEvents());
		assertEquals(1, _listener.getAmountColorChangedEvents()); //There should be one colorchangeevent that got fired when active went true.
	}
	
	@Test
	public void testSetChannelcolor()
	{
		_provider.addChannel();
		_provider.addChannel();
		
		Color c = new Color(200, 200, 200, 200);
		_provider.setChannelcolor(_provider.getChannels().get(0), c);
		
		Color cActual = _provider.getChannels().get(0).getColor();
		
		assertEquals(c, cActual); //Tests if setting the color worked.
		
		assertEquals(0, _listener.getAmountColorChangedEvents()); //Event should not be fired yet.
		
		_provider.setActive(true);
		
		assertEquals(1, _listener.getAmountColorChangedEvents());
		assertEquals(c, _listener.getLatestChannels().get(0).getColor()); //Tests if the change was received at the listener.
	}
}
