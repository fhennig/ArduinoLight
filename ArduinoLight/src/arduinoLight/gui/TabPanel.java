package arduinoLight.gui;

import javax.swing.JPanel;

/**
 * This Class is a Superclass for every Module that wants to be added to the TabbedPane of the GUI.
 */
@SuppressWarnings("serial")
public abstract class TabPanel extends JPanel
{
	/**
	 * The Title of the Panel.
	 * This method is called when the Panel is added to the TabbedPane.
	 */
	public abstract String getTitle();
}
