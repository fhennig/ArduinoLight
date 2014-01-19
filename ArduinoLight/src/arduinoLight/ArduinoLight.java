package arduinoLight;

import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import arduinoLight.arduino.amblone.AmbloneConnection;
import arduinoLight.channelprovider.ChannelFactory;
import arduinoLight.channelprovider.generator.customColors.CustomColorsProvider;
import arduinoLight.gui.Gui;
import arduinoLight.gui.SerialConnectionPanel;
import arduinoLight.gui.TabPanel;
import arduinoLight.gui.ambientLight.AmbientlightPanel;
import arduinoLight.gui.customColor.CustomColorPanel;
import arduinoLight.interfaces.Refreshable;
import arduinoLight.threading.Refresher;

public class ArduinoLight
{
	private static final ChannelFactory _factory = new ChannelFactory();
	private static final Queue<Refreshable> _queue = new ConcurrentLinkedQueue<>();
	
	public static Queue<Refreshable> getRefreshQueue()
	{
		return _queue;
	}
	
	public static ChannelFactory getChannelFactory()
	{
		return _factory;
	}
	
	public static void main(String[] args)
	{		
		new Refresher(_queue); //Here we create our Refresh-thread. Currently, we use only one Thread and one Object, later, this could be expanded easily
		
		CustomColorsProvider provider = new CustomColorsProvider();
		
		AmbloneConnection connection = new AmbloneConnection(provider);
		
		Gui.initLookAndFeel();
		SerialConnectionPanel connectionPanel = new SerialConnectionPanel(connection);
		connection.addSpeedListener(connectionPanel);
		TabPanel ambiPanel = new AmbientlightPanel("AmbientLight");
		TabPanel colorPanel = new CustomColorPanel(provider, "Custom Color");
		
		provider.addChannelcolorsListener((arduinoLight.channelprovider.ChannelcolorsListener)colorPanel);
		
		Set<TabPanel> panels = new LinkedHashSet<TabPanel>();
		panels.add(ambiPanel);
		panels.add(colorPanel);
		
		new Gui(panels, connectionPanel);
	}
}
