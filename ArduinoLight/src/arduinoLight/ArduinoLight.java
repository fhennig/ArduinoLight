package arduinoLight;

import java.util.LinkedHashSet;
import java.util.Set;

import arduinoLight.arduino.amblone.AmbloneConnection;
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
		/*Debugprovider provider = new Debugprovider();   //Debugprovider as a provider
		provider.addChannel();					
		provider.addChannel();
		SimpleMixer mixer = new SimpleMixer(provider);
		AmbloneConnection connection = new AmbloneConnection(mixer);
		
		Enumeration<CommPortIdentifier> ports = connection.getAvailablePorts();
		CommPortIdentifier resultPort = null;
		CommPortIdentifier port;
		
		while (ports.hasMoreElements())
		{
			port = ports.nextElement();
			if (port.getName().equals("COM4"))
			{
				resultPort = port;
				break;
			}
		}
		
		if (resultPort != null)
		{
			try {
				connection.connect(resultPort, 256000);
			} catch (PortInUseException e) {
				System.out.println("PortInUseException");
			}
			
			provider.setActive(true);
			
		}*/
		
		CustomColorsProvider provider = new CustomColorsProvider();
		AmbloneConnection connection = new AmbloneConnection(provider);
		
		Gui.initLookAndFeel();
		SerialConnectionPanel connectionPanel = new SerialConnectionPanel(connection);
		TabPanel ambiPanel = new AmbientlightPanel("AmbientLight");
		TabPanel colorPanel = new CustomColorPanel(provider, "Custom Color");
		
		Set<TabPanel> panels = new LinkedHashSet<TabPanel>();
		panels.add(ambiPanel);
		panels.add(colorPanel);
		
		new Gui(panels, connectionPanel);
	}
}
