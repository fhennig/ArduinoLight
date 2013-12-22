package arduinoLight;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import arduinoLight.arduino.amblone.AmbloneConnection;
import arduinoLight.channelprovider.debugprovider.Debugprovider;
import arduinoLight.gui.AmbientlightPanel;
import arduinoLight.gui.CustomColorPanel;
import arduinoLight.gui.Gui;
import arduinoLight.gui.ModulePanel;
import arduinoLight.gui.ScreenSelectionPanel;
import arduinoLight.gui.SerialConnectionPanel;
import arduinoLight.mixer.SimpleMixer;

public class ArduinoLight
{
	public static void main(String[] args)
	{
		Debugprovider provider = new Debugprovider();   //Debugprovider as a provider
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
			
		}
		
		
		Gui.initLookAndFeel();
		SerialConnectionPanel connectionPanel = new SerialConnectionPanel();
		ScreenSelectionPanel selectionPanel = new ScreenSelectionPanel();
		AmbientlightPanel ambiPanel = new AmbientlightPanel(selectionPanel);
		CustomColorPanel colorPanel = new CustomColorPanel(provider);
		
		ModulePanel ambiLight = new ModulePanel(ambiPanel, "AmbientLight");
		ModulePanel customColor = new ModulePanel(colorPanel, "Custom Color");
		
		Set<ModulePanel> panels = new HashSet<ModulePanel>();
		panels.add(ambiLight);
		panels.add(customColor);
		
		new Gui(panels, connectionPanel);
	}
}
