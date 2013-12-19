package arduinoLight.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class SerialConnectionPanel extends JPanel{
	
	//TODO add model reference
	
	JLabel _connectionSpeedLabel = new JLabel("Connection Speed: 900000");
	JLabel _channelLabel = new JLabel("Channels: ");
	JSpinner _channelSpinner = new JSpinner();
	JLabel _lblNewLabel = new JLabel("COM-Port: "); //TODO Set final Labelname
	JComboBox<String> _comboBox = new JComboBox<String>(new DefaultComboBoxModel<String>());
	JButton _connectButton = new JButton("Connect");
	
	
	public SerialConnectionPanel(){
		initComponents();
	}
	
	class channelSpinnerHandler implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
			int value = (int)_channelSpinner.getModel().getValue();
			throw new UnsupportedOperationException("not implemented yet");
		}
	}
	
	class comboBoxHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String value = (String)_comboBox.getSelectedItem();
			throw new UnsupportedOperationException("not implemented yet");
		}
	}
	
	class connectButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			throw new UnsupportedOperationException("not implemented yet");
		}
	}
	
	private void initComponents() {
		
		_channelSpinner.addChangeListener(new channelSpinnerHandler());
		_comboBox.addActionListener(new comboBoxHandler());
		_connectButton.addActionListener(new connectButtonHandler());
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBorder(new TitledBorder(null, "Connection Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));				
		this.add(_connectionSpeedLabel);
		this.add(Box.createHorizontalGlue());
		this.add(_channelLabel);
		this.add(_channelSpinner);
			_channelSpinner.setModel(new SpinnerNumberModel(2, 1, 4, 1));
		this.add(Box.createHorizontalGlue());
		this.add(_lblNewLabel);
		this.add(_comboBox);
		this.add(Box.createHorizontalGlue());
		this.add(_connectButton);
		
	}
}
