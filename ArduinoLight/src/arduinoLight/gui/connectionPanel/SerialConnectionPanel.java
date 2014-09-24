package arduinoLight.gui.connectionPanel;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import arduinoLight.arduino.PackageFactory;
import arduinoLight.arduino.PortMap;
import arduinoLight.arduino.SerialConnection;
import arduinoLight.arduino.Transmission;
import arduinoLight.gui.CBItem;
import arduinoLight.util.DebugConsole;

@SuppressWarnings("serial")
public class SerialConnectionPanel extends JPanel
{
	private SerialConnection _connection;
	private Transmission _amblone;
	private List<PackageFactory> _packageFactories;
	
	private PortMapPanel _portMapPanel;
	private JComboBox<PortItem> _portComboBox;
	private JComboBox<Integer> _baudComboBox;
	private JComboBox<CBItem<PackageFactory>> _protocolComboBox;
	private JSpinner _frequencySpinner;
	private JToggleButton _connectButton;
	private JLabel _portLabel = new JLabel("COM-Port: ");
	private JLabel _frequencyLabel = new JLabel("Frequency: ");
	private JLabel _baudLabel = new JLabel("Baudrate: ");
	private JPanel _wrapperPanel;
	
	public SerialConnectionPanel(SerialConnection connection,
			Transmission ambloneTransmission,
			List<PackageFactory> packageFactories)
	{
		_connection = connection;
		_amblone = ambloneTransmission;
		_packageFactories = packageFactories;
		
		initComponents();
	}
	
	private void initComponents()
	{
		//amblonePanel
		_portMapPanel = new PortMapPanel(new PortMap(0));
		_portMapPanel.setEnabled(false);
				
		//frequencySpinner
		_frequencySpinner = new JSpinner();
		_frequencySpinner.setModel(new SpinnerNumberModel(100, 1, Transmission.MAX_REFRESHRATE, 1));

		//connectButton
		_connectButton = new JToggleButton("Connect");
		_connectButton.addActionListener(new ConnectButtonHandler());
		
		//portComboBox
		ComboBoxModel<PortItem> pcbModel = new DefaultComboBoxModel<PortItem>();
		_portComboBox = new JComboBox<PortItem>(pcbModel);
		_portComboBox.addPopupMenuListener(new ComboBoxMenuListener());
		_portComboBox.setPreferredSize(new Dimension(120, 0));
		refreshComboBox();
		
		//baudComboBox
		ComboBoxModel<Integer> bcbModel = new DefaultComboBoxModel<>();
		_baudComboBox = new JComboBox<>(bcbModel);
		_portComboBox.setPreferredSize(new Dimension(80, 0));
		initBaudComboBox();
		
		//protocolComboBox
		_protocolComboBox = new JComboBox<CBItem<PackageFactory>>();
		_protocolComboBox.setPreferredSize(new Dimension(80, 0));
		initProtocolComboBox();
		
		//connectButton
		_connectButton = new JToggleButton("Connect");
		_connectButton.addActionListener(new ConnectButtonHandler());
		
		//Layout
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.setBorder(BorderFactory.createTitledBorder("Connection Settings"));
		this.setLayout(new GridBagLayout());

		this.add(_portMapPanel);
		JPanel lowerLine = new JPanel();
		_wrapperPanel = lowerLine;
			lowerLine.setLayout(new BoxLayout(lowerLine, BoxLayout.X_AXIS));
			lowerLine.add(_protocolComboBox);
			lowerLine.add(_frequencyLabel);
			lowerLine.add(_frequencySpinner);
			lowerLine.add(Box.createHorizontalStrut(20));
			lowerLine.add(_portLabel);
			lowerLine.add(_portComboBox);
			lowerLine.add(Box.createHorizontalStrut(20));
			lowerLine.add(_baudLabel);
			lowerLine.add(_baudComboBox);
		gbc.gridy = 1;
		this.add(lowerLine, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(Box.createGlue(), gbc);
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 2;
		this.add(_connectButton, gbc);
		
	}	

	private void refreshComboBox()
	{
		DefaultComboBoxModel<PortItem> model = (DefaultComboBoxModel<PortItem>) _portComboBox.getModel();
		model.removeAllElements();
		Enumeration<CommPortIdentifier> ports = SerialConnection.getAvailablePorts();
		
		while(ports.hasMoreElements()){
			model.addElement(new PortItem(ports.nextElement()));
		}
		
		if(model.getSize() == 0){
			_connectButton.setEnabled(false);
		} else {
			_connectButton.setEnabled(true);
		}
		DebugConsole.print("SerialConnectionPanel", "refreshComboBox", "comboBox refreshed!");
	}
	
	private void initBaudComboBox()
	{
		DefaultComboBoxModel<Integer> model = (DefaultComboBoxModel<Integer>)_baudComboBox.getModel();
		model.addElement(115200);
		model.addElement(256000);
	}
	
	private void initProtocolComboBox()
	{
		DefaultComboBoxModel<CBItem<PackageFactory>> model = (DefaultComboBoxModel<CBItem<PackageFactory>>)_protocolComboBox.getModel();
		for (PackageFactory pf : _packageFactories)
		{
			model.addElement(new CBItem<PackageFactory>(pf, factory -> factory.getName()));
		}
		
	}
	
	class ConnectButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(_connectButton.isSelected())
			{
				PortItem selectedItem = (PortItem) _portComboBox.getModel().getSelectedItem();
				try
				{
					_connection.open(selectedItem.getPort(), (Integer)_baudComboBox.getSelectedItem());
					_portMapPanel.setEnabled(true);
					_wrapperPanel.setEnabled(false);
					System.out.println("wrapperPanel setEnabled false");
				} catch (PortInUseException | IllegalStateException | IllegalArgumentException e1) {
					_connectButton.setSelected(false);
					JOptionPane.showMessageDialog(null,
							"Could not establish a Connection!\nIs the Connection already in use?",
							"Connection failed!",
							JOptionPane.ERROR_MESSAGE);
				}
				if (_connection.isOpen())
				{
					int frequency = ((Integer) _frequencySpinner.getValue());
					@SuppressWarnings("unchecked")
					PortMap map = _amblone.start(_connection, frequency,
							((CBItem<PackageFactory>)_protocolComboBox.getSelectedItem()).getItem());
					_connectButton.setText("Disconnect");
					_portMapPanel.setPortMap(map);
				}
			}
			else
			{
				_connection.close();
				_connectButton.setText("Connect");
				_portMapPanel.setEnabled(false);
				_wrapperPanel.setEnabled(true);
			}
		}
	}
	
	/**
	 * This listener refreshes the ComboBox if the combobox's popupmenu is opened.
	 */
	private class ComboBoxMenuListener implements PopupMenuListener
	{
		@Override
		public void popupMenuCanceled(PopupMenuEvent e) { /** Do nothing */ }

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { /** Do nothing */ }

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e)
		{
			refreshComboBox();
		}
	}
	
	/**
	 * Can be used inside a ComboBox. Holds a reference to a CommPortIdentifier.
	 * Provides a proper toString method.
	 */
	private class PortItem
	{
		CommPortIdentifier _port;
		
		public PortItem(CommPortIdentifier port)
		{
			_port = port;
		}
		
		public CommPortIdentifier getPort()
		{
			return _port;
		}
		
		@Override
		public String toString()
		{
			return _port.getName();
		}
	}
}

