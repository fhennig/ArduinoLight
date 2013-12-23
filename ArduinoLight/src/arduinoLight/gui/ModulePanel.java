/**
 * A Wrapper Class to add the Status Panel and a Name-Tag to every Module.
 * Every Panel that wants to be added to the TabbedPane needs to be wrapped by this.
 */

package arduinoLight.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ModulePanel extends JPanel {

	private String _name;
	private JPanel _basePanel;
	private JPanel _statusPanel = new StatusPanel();
	
	public ModulePanel(JPanel panel, String name){
		_basePanel = panel;
		_name = name;
		initComponents();
	}

	private void initComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		this.add(_basePanel);
		this.add(_statusPanel);
	}
	
	public String getName(){
		return _name;
	}

	
}
