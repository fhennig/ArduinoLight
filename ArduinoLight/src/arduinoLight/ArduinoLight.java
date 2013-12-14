package arduinoLight;

import controllers.SerialConnectionController;
import arduinoLight.gui.Gui;
import arduinoLight.gui.SerialConnectionPanel;

public class ArduinoLight
{
	public static void main(String[] args)
	{
		Gui.initLookAndFeel();
		SerialConnectionController connectionController = new SerialConnectionController();
		SerialConnectionPanel connectionPanel = new SerialConnectionPanel(connectionController);
		new Gui(connectionPanel);
	}
}
