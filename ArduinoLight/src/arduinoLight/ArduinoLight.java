package arduinoLight;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;

import arduinoLight.arduino.AdalightPackageFactory;
import arduinoLight.arduino.AmblonePackageFactory;
import arduinoLight.arduino.PackageFactory;
import arduinoLight.arduino.SerialConnection;
import arduinoLight.arduino.Transmission;
import arduinoLight.gui.Gui;
import arduinoLight.gui.TabPanel;
import arduinoLight.gui.ambientLight.AmbientlightPanel;
import arduinoLight.gui.colorpicker.ColorPickingPanel;
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
				SerialConnection connection = new SerialConnection();
				Transmission amblone = new Transmission();
				List<PackageFactory> factories = new ArrayList<>();
				factories.add(new AmblonePackageFactory());
				factories.add(new AdalightPackageFactory());
				
				Gui.initLookAndFeel();
				SerialConnectionPanel connectionPanel = new SerialConnectionPanel(connection, amblone, factories);
				TabPanel ambiPanel = new AmbientlightPanel(Model.getInstance().getAmbientlight());
				TabPanel colorPickerPanel = new ColorPickingPanel(Model.getInstance().getColorPicker());
				
				Set<TabPanel> panels = new LinkedHashSet<TabPanel>();
				panels.add(ambiPanel);
				panels.add(colorPickerPanel);
				
				new Gui(panels, connectionPanel);
			}
		});
	}
}
