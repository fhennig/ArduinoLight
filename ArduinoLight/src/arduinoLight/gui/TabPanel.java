/**
 * This Class is a Superclass for every Module that wants to be added to the TabbedPane of the GUI
 * When subclassed, the name field should be set to something more specific.
 */

package arduinoLight.gui;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class TabPanel extends JPanel
{
	public abstract String getTitle();
}
