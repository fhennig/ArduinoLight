package arduinoLight;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JPanel;

import arduinoLight.arduino.amblone.AmbloneConnection;
import arduinoLight.channelprovider.customColors.CustomColorsProvider;
import arduinoLight.channelprovider.debugprovider.Debugprovider;
import arduinoLight.gui.Gui;
import arduinoLight.gui.SerialConnectionPanel;
import arduinoLight.gui.TabPanel;
import arduinoLight.gui.ambientLight.AmbientlightPanel;
import arduinoLight.gui.ambientLight.ScreenSelectionPanel;
import arduinoLight.gui.customColor.CustomColorPanel;
import arduinoLight.mixer.SimpleMixer;

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
		SimpleMixer mixer = new SimpleMixer(provider);
		AmbloneConnection connection = new AmbloneConnection(mixer);
		
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
