package arduinoLight;

import arduinoLight.gui.Gui;

public class ArduinoLight
{
	public static void main(String[] args)
	{
		Gui.initLookAndFeel();
		new Gui();
	}
}
