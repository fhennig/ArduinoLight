package arduinoLight;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.util.*;

import arduinoLight.arduino.SerialConnection;
import arduinoLight.arduino.amblone.AmbloneConnectionOld;
import arduinoLight.arduino.amblone.AmbloneTransmission;
import arduinoLight.channel.IChannel;
import arduinoLight.channelprovider.ChannelFactory;
import arduinoLight.channelprovider.DebugColorswitchThread;
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
		//test();
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
	
	
	
	public static void test()
	{
		final List<IChannel> channels = new ArrayList<>();
		IChannel channel1 = ChannelFactory.getInstance().getChannel();
		IChannel channel2 = ChannelFactory.getInstance().getChannel();
		channels.add(channel1);
		channels.add(channel2);
		
		Thread testThread = new DebugColorswitchThread(channels);
		
		
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
		amblone.setOutput(0, channel1);
		amblone.setOutput(1, channel2);
		
		
		testThread.start();
		amblone.start(connection, 100);
	}
}
