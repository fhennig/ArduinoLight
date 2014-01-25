package arduinoLight;

import java.util.LinkedHashSet;
import java.util.Set;

import arduinoLight.arduino.amblone.AmbloneConnection;
import arduinoLight.channelprovider.ChannelFactory;
import arduinoLight.channelprovider.generator.customColors.CustomColorsProvider;
import arduinoLight.gui.Gui;
import arduinoLight.gui.SerialConnectionPanel;
import arduinoLight.gui.TabPanel;
import arduinoLight.gui.ambientLight.AmbientlightPanel;
import arduinoLight.gui.customColor.CustomColorPanel;

public class ArduinoLight
{
	private static final ChannelFactory _factory = new ChannelFactory(); //Global access point to the ChannelFactory TODO maybe make the factory a singleton and remove this reference
		
	public static ChannelFactory getChannelFactory()
	{
		return _factory;
	}
	
	public static void main(String[] args)
	{		
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
