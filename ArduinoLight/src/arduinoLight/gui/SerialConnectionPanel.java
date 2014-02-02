package arduinoLight.gui;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import arduinoLight.arduino.SerialConnectionOld;
import arduinoLight.interfaces.propertyListeners.ActiveListener;
import arduinoLight.interfaces.propertyListeners.SpeedListener;

@SuppressWarnings("serial")
public class SerialConnectionPanel extends JPanel implements ActiveListener, ConnectionPanel{
	
	SerialConnectionOld _connection;
	
	java.net.URL _imageURL = SerialConnectionPanel.class.getResource("images/view_refresh.png");
	ImageIcon _icon = new ImageIcon(_imageURL, "refresh");
	
	JLabel _connectionSpeedLabel = new JLabel("Packages per Second: 0"); //label for pps TODO this is obsolete
	JLabel _lblNewLabel = new JLabel("COM-Port: ");
	DefaultComboBoxModel<ComboBoxPortItem> _comboBoxModel = new DefaultComboBoxModel<ComboBoxPortItem>();
	JComboBox<ComboBoxPortItem> _portComboBox = new JComboBox<ComboBoxPortItem>(_comboBoxModel);
	JToggleButton _connectButton = new JToggleButton("Connect");
	JButton _refreshButton;
	
	
	public SerialConnectionPanel(SerialConnectionOld connection){
		initImageIcon();
		_refreshButton = new JButton(_icon);
		_connection = connection;
		_connection.addActiveListener(this);
		initComponents();
	}
	
	private void initImageIcon(){
		Image icon = _icon.getImage();
		Image newIcon = icon.getScaledInstance(20, 17, Image.SCALE_DEFAULT);
		_icon = new ImageIcon(newIcon);
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
	

	private void refreshComboBox(){
		_comboBoxModel.removeAllElements();
		Enumeration<CommPortIdentifier> ports = SerialConnectionOld.getAvailablePorts();
		
		while(ports.hasMoreElements()){
			_comboBoxModel.addElement(new ComboBoxPortItem(ports.nextElement()));
		}
		
		if(_comboBoxModel.getSize() == 0){
			_connectButton.setEnabled(false);
		} else {
			_connectButton.setEnabled(true);
		}
	}
	
	class RefreshButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			refreshComboBox();
		}
		
	}
	
	private void initComponents() {
		
		refreshComboBox();
		
		_connectButton.addActionListener(new connectButtonHandler());
		_refreshButton.addActionListener(new RefreshButtonHandler());
		
		_refreshButton.setPreferredSize(new Dimension(30, 10));
		_connectButton.setPreferredSize(new Dimension(100, 25));
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBorder(new TitledBorder(null, "Connection Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));				
		this.add(_connectionSpeedLabel);
		this.add(Box.createHorizontalGlue());
		this.add(_lblNewLabel);
		this.add(_portComboBox);
		this.add(_refreshButton);
		this.add(Box.createHorizontalStrut(20));
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
	
	public void disconnect(){
		_connection.disconnect();
	}
}
