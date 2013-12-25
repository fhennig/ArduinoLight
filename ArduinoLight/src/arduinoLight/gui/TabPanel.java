/**
 * This Class is a Superclass for every Module that wants to be added to the TabbedPane of the GUI
 * When subclassed, the name field should be set to something more specific.
 */

package arduinoLight.gui;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TabPanel extends JPanel{
	
	protected String _title;
	
	public String getTitle(){
		return _title;
	}
}
