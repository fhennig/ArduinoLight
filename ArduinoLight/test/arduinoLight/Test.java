package arduinoLight;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.util.Enumeration;

import arduinoLight.arduino.SerialConnection;
import arduinoLight.arduino.amblone.AmbloneTransmission;
import arduinoLight.arduino.amblone.PortMap;
import arduinoLight.channel.Channel;
import arduinoLight.channelholder.ambientlight.Ambientlight;
import arduinoLight.channelholder.ambientlight.Areaselection;
import arduinoLight.model.Model;

/**
 * This class contains a Test-Method.
 */
public class Test
{
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
		
		PortMap map = new PortMap();
		AmbloneTransmission amblone = new AmbloneTransmission(map);
		map.setPort(1, channel1);
		map.setPort(0, channel2);
		
		
		amblone.start(connection, 100);
		ambientlight.start(10);
	}
}
