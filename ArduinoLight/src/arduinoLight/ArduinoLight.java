package arduinoLight;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.SwingUtilities;

import arduinoLight.arduino.PortMap;
import arduinoLight.arduino.SerialConnection;
import arduinoLight.arduino.amblone.AmbloneTransmission;
import arduinoLight.channelholder.ambientlight.Ambientlight;
import arduinoLight.gui.ChannelModifyPanel;
import arduinoLight.gui.Gui;
import arduinoLight.gui.TabPanel;
import arduinoLight.gui.ambientLight.AmbientlightPanel;
import arduinoLight.gui.connectionPanel.SerialConnectionPanel;
import arduinoLight.model.Model;
import arduinoLight.util.Color;

/**
 * Main class containing the main method.
 * Only used to start the application.
 */
public class ArduinoLight
{
	public static void main(String[] args)
	{		
		//Add some Channels for testing purposes:
		Model.getInstance().getChannelFactory().newChannel("RED").setColor(Color.RED);
		Model.getInstance().getChannelFactory().newChannel("GREEN").setColor(Color.GREEN);
		Model.getInstance().getChannelFactory().newChannel("BLUE").setColor(Color.BLUE);
		
		//Initialize GUI in Swing event dispatch thread
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				PortMap map = new PortMap();
				SerialConnection connection = new SerialConnection();
				AmbloneTransmission amblone = new AmbloneTransmission(map);
				Ambientlight ambientlight = Model.getInstance().getAmbientlight();
				
				Gui.initLookAndFeel();
				SerialConnectionPanel connectionPanel = new SerialConnectionPanel(connection, amblone, map);
				TabPanel ambiPanel = new AmbientlightPanel(ambientlight);
				TabPanel chanModPanel = new ChannelModifyPanel();
				
				Set<TabPanel> panels = new LinkedHashSet<TabPanel>();
				panels.add(ambiPanel);
				panels.add(chanModPanel);
				
				new Gui(panels, connectionPanel);
			}
		});
	}
}
