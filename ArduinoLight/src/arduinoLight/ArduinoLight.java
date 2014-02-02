package arduinoLight;

import java.util.LinkedHashSet;
import java.util.Set;

import arduinoLight.arduino.amblone.AmbloneConnectionOld;
import arduinoLight.channelprovider.generator.customColors.CustomColorsProvider;
import arduinoLight.gui.Gui;
import arduinoLight.gui.SerialConnectionPanel;
import arduinoLight.gui.TabPanel;
import arduinoLight.gui.ambientLight.AmbientlightPanel;
import arduinoLight.gui.customColor.CustomColorPanel;

public class ArduinoLight
{
	public static void main(String[] args)
	{		
		CustomColorsProvider provider = new CustomColorsProvider();
		
		AmbloneConnectionOld connection = new AmbloneConnectionOld(provider);
		
		Gui.initLookAndFeel();
		SerialConnectionPanel connectionPanel = new SerialConnectionPanel(connection);
		TabPanel ambiPanel = new AmbientlightPanel("AmbientLight");
		TabPanel colorPanel = new CustomColorPanel(provider, "Custom Color");
		
		provider.addChannelcolorsListener((arduinoLight.channelprovider.ChannelcolorsListener)colorPanel);
		
		Set<TabPanel> panels = new LinkedHashSet<TabPanel>();
		panels.add(ambiPanel);
		panels.add(colorPanel);
		
		new Gui(panels, connectionPanel);
	}
}
