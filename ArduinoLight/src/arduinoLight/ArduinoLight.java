package arduinoLight;

import arduinoLight.gui.AmbientlightPanel;
import arduinoLight.gui.Gui;
import arduinoLight.gui.ScreenSelectionPanel;
import arduinoLight.gui.SerialConnectionPanel;

public class ArduinoLight
{
	public static void main(String[] args)
	{
		Gui.initLookAndFeel();
		SerialConnectionPanel connectionPanel = new SerialConnectionPanel();
		ScreenSelectionPanel selectionPanel = new ScreenSelectionPanel();
		AmbientlightPanel ambiPanel = new AmbientlightPanel(selectionPanel);
		new Gui(connectionPanel, ambiPanel);
	}
}
