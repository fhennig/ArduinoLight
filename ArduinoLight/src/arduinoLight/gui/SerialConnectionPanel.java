package arduinoLight.gui;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import arduinoLight.arduino.SerialConnection;
import arduinoLight.interfaces.propertyListeners.ActiveListener;
import arduinoLight.interfaces.propertyListeners.SpeedListener;

@SuppressWarnings("serial")
public class SerialConnectionPanel extends JPanel implements ActiveListener, SpeedListener, ConnectionPanel{
	
	SerialConnection _connection;
	PortComboBoxHandler _portBoxHandler = new PortComboBoxHandler();
	
	JLabel _connectionSpeedLabel = new JLabel("Packages per Second: 0");
	JLabel _lblNewLabel = new JLabel("COM-Port: ");
	DefaultComboBoxModel<ComboBoxPortItem> _comboBoxModel = new DefaultComboBoxModel<ComboBoxPortItem>();
	JComboBox<ComboBoxPortItem> _portComboBox = new JComboBox<ComboBoxPortItem>(_comboBoxModel);
	JToggleButton _connectButton = new JToggleButton("Connect");
	
	
	public SerialConnectionPanel(SerialConnection connection){
		_connection = connection;
		_connection.addActiveListener(this);
		initComponents();
	}
	
	class connectButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(_connectButton.isSelected()){
				try {
					ComboBoxPortItem selectedItem = (ComboBoxPortItem) _comboBoxModel.getSelectedItem();
					_connection.connect(selectedItem.getPort(), 256000);
				} catch (PortInUseException | IllegalStateException | IllegalArgumentException e1) {
					_connectButton.setSelected(false);
					JOptionPane.showMessageDialog(null,
							"Could not establish a Connection!\nIs the Connection already in use?",
							"Connection failed!",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				_connection.disconnect();
			}
		}
	}
	
	class PortComboBoxHandler implements ActionListener{

		public void refreshComboBox(){
			_comboBoxModel.removeAllElements();
			Enumeration<CommPortIdentifier> ports = _connection.getAvailablePorts();
			
			while(ports.hasMoreElements()){
				_comboBoxModel.addElement(new ComboBoxPortItem(ports.nextElement()));
			}
			
			if(_comboBoxModel.getSize() == 0){
				_connectButton.setEnabled(false);
			} else {
				_connectButton.setEnabled(true);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			refreshComboBox();
		}
		
	}
	
	private void initComponents() {
		
		_portBoxHandler.refreshComboBox();
		
		_connectButton.addActionListener(new connectButtonHandler());
		_portComboBox.addActionListener(_portBoxHandler);
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBorder(new TitledBorder(null, "Connection Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));				
		this.add(_connectionSpeedLabel);
		this.add(Box.createHorizontalGlue());
		this.add(_lblNewLabel);
		this.add(_portComboBox);
		this.add(Box.createHorizontalGlue());
		this.add(_connectButton);
		
	}
	
	class ComboBoxPortItem {

		CommPortIdentifier _port;
		
		public ComboBoxPortItem(CommPortIdentifier port){
			_port = port;
		}
		
		@Override
		public String toString() {
			return _port.getName();
		}
		
		public CommPortIdentifier getPort(){
			return _port;
		}
	}

	@Override
	public void activeChanged(Object source, boolean newActive) {
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
	
	public void disconnect(){
		_connection.disconnect();
	}
}
