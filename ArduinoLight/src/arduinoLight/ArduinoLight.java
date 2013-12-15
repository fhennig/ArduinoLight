package arduinoLight;

import arduinoLight.controllers.ScreenSelectionController;
import arduinoLight.controllers.SerialConnectionController;
import arduinoLight.gui.AmbientlightPanel;
import arduinoLight.gui.Gui;
import arduinoLight.gui.ScreenSelectionPanel;
import arduinoLight.gui.SerialConnectionPanel;

public class ArduinoLight
{
	public static void main(String[] args)
	{
		Gui.initLookAndFeel();
		SerialConnectionController connectionController = new SerialConnectionController();
		SerialConnectionPanel connectionPanel = new SerialConnectionPanel(connectionController);
		ScreenSelectionController selectionController = new ScreenSelectionController();
		ScreenSelectionPanel selectionPanel = new ScreenSelectionPanel(selectionController);
		AmbientlightPanel ambiPanel = new AmbientlightPanel(selectionPanel);
		new Gui(connectionPanel, ambiPanel);
	}
}
