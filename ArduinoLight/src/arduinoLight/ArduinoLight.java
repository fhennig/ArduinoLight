package arduinoLight;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.util.*;

import arduinoLight.arduino.SerialConnection;
import arduinoLight.arduino.amblone.AmbloneTransmission;
import arduinoLight.channel.Channel;
import arduinoLight.channelholder.ambientlight.Ambientlight;
import arduinoLight.channelholder.ambientlight.Areaselection;
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
		
		SerialConnection connection = new SerialConnection();
		AmbloneTransmission amblone = new AmbloneTransmission();
		Ambientlight ambientlight = Model.getInstance().getAmbientlight();
		
		Gui.initLookAndFeel();
		SerialConnectionPanel connectionPanel = new SerialConnectionPanel(connection, amblone);
		TabPanel ambiPanel = new AmbientlightPanel(ambientlight);
		TabPanel chanModPanel = new ChannelModifyPanel();
		
		Set<TabPanel> panels = new LinkedHashSet<TabPanel>();
		panels.add(ambiPanel);
		panels.add(chanModPanel);
		
		new Gui(panels, connectionPanel);
	}
	
	
	
	public static void test()
	{
		Channel channel1 = Model.getInstance().getChannelFactory().newChannel();
		Channel channel2 = Model.getInstance().getChannelFactory().newChannel();
		
		Areaselection selectionTop = new Areaselection(2, 1);
		Areaselection selectionBot = new Areaselection(2, 1);
		selectionTop.setCell(0, 0, true);
		selectionBot.setCell(0, 1, true);
		
		Ambientlight ambientlight = Model.getInstance().getAmbientlight();
		ambientlight.addChannel(channel1, selectionTop);
		ambientlight.addChannel(channel2, selectionBot);
		
		SerialConnection connection = new SerialConnection();
		Enumeration<CommPortIdentifier> portIds = SerialConnection.getAvailablePorts();
		CommPortIdentifier id = null;
		CommPortIdentifier p;
		while (portIds.hasMoreElements())
		{
			p = portIds.nextElement();
			if (p.getName().matches("COM4"))
			{
				id = p;
				break;
			}
		}
		if (id != null)
		{
			try
			{
				connection.open(id, 256000);
			} catch (PortInUseException e)
			{
				System.out.println("COM4 found, but is already in use!");
				return;
			}
		}
		else
		{
			System.out.println("COM4 not found!");
			return;
		}
		
		AmbloneTransmission amblone = new AmbloneTransmission();
		amblone.setOutput(1, channel1);
		amblone.setOutput(0, channel2);
		
		
		amblone.start(connection, 100);
		ambientlight.start(10);
	}
}
