package arduinoLight.gui;

import java.awt.Dimension;

import javax.swing.JComponent;

/**
 * Some generally useful static methods related to Swing.
 */
public class SwingUtil
{
	/**
	 * This method encapsulates some ugly code.
	 * It sets the preferred width of a component while keeping the preferred height intact.
	 */
	public static void setPreferredWidth(JComponent comp, int width)
	{
		Dimension preferredSize = comp.getPreferredSize();
		preferredSize.width = width;
		comp.setPreferredSize(preferredSize);
	}
}
