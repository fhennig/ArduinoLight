package arduinoLight.gui;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import arduinoLight.arduino.SerialConnection;
import arduinoLight.arduino.SerialConnectionListener;
import arduinoLight.mixer.Colorprovider;

@SuppressWarnings("serial")
public class SerialConnectionPanel extends JPanel implements SerialConnectionListener{
	
	SerialConnection _connection;
	
	JLabel _connectionSpeedLabel = new JLabel("Packages per Second: 0");
	JLabel _lblNewLabel = new JLabel("COM-Port: ");
	DefaultComboBoxModel<ComboBoxPortItem> _comboBoxModel = new DefaultComboBoxModel<ComboBoxPortItem>();
	JComboBox<ComboBoxPortItem> _comboBox = new JComboBox<ComboBoxPortItem>(_comboBoxModel);
	JToggleButton _connectButton = new JToggleButton("Connect");
	
	
	public SerialConnectionPanel(SerialConnection connection){
		_connection = connection;
		initComponents();
	}
	
	class connectButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				ComboBoxPortItem selectedItem = (ComboBoxPortItem) _comboBoxModel.getSelectedItem();
				_connection.connect(selectedItem.getPort(), 256000);
			} catch (PortInUseException | IllegalStateException | IllegalArgumentException e1) {
				_connectButton.setSelected(false);
				JOptionPane.showMessageDialog(null,
					    "Could not establish a Connection!\nIs the Connection already in use?",
					    "Connection failed",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void initComponents() {
		
		initComboBox();
		
		_connectButton.addActionListener(new connectButtonHandler());
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBorder(new TitledBorder(null, "Connection Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));				
		this.add(_connectionSpeedLabel);
		this.add(Box.createHorizontalGlue());
		this.add(_lblNewLabel);
		this.add(_comboBox);
		this.add(Box.createHorizontalGlue());
		this.add(_connectButton);
		
	}
	
	private void initComboBox(){
		Enumeration<CommPortIdentifier> ports = _connection.getAvailablePorts();
		
		while(ports.hasMoreElements()){
			_comboBoxModel.addElement(new ComboBoxPortItem(ports.nextElement()));
		}
	}

	@Override
	public void activeChanged(Object source, boolean newActive) { //TODO Change replacement of Buttons to something better
		if(newActive){
			_connectButton.setText("Disconnect");
		} else {
			_connectButton.setText("Connect");
		}
	}

	@Override
	public void speedChanged(Object source, int newSpeed) {
		_connectionSpeedLabel.setText("Packages per Second: " + newSpeed );
	}

	@Override
	public void colorproviderChanged(Object source,
			Colorprovider newColorprovider) {
		// TODO DO SOMETHING
		
	}
}
