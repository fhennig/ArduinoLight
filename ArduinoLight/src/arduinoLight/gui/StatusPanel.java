/**
 * This Panel holds the Controlls necessary to activate a Module and to switch between Channels
 * Every Module needs this Panel, it is automatically added by the ModulePanel Wrapper Class
 */

package arduinoLight.gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class StatusPanel extends JPanel{

	JComboBox _channelBox = new JComboBox();
	JLabel _channelLabel = new JLabel("Channel: ");
	JButton _removeButton = new JButton("Remove");
	JButton _addButton = new JButton("Add");
	
	public StatusPanel(){
		initComponents();
	}

	private void initComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBorder(new TitledBorder(null, "Status", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		
		this.add(_channelLabel);
		this.add(_channelBox);
		this.add(_addButton);
		this.add(_removeButton);
	}
	
}
