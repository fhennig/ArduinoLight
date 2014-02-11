package arduinoLight.gui;

import java.awt.Dimension;

import javax.swing.JComponent;

public class SwingUtil
{
	public static void setPreferredWidth(JComponent comp, int width)
	{
		Dimension preferredSize = comp.getPreferredSize();
		preferredSize.width = width;
		comp.setPreferredSize(preferredSize);
	}
}
