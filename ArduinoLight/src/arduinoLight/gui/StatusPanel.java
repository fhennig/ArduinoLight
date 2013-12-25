/**
 * This Panel holds the Controls necessary to activate a Module and to switch between Channels.
 * Every Module needs this Panel, thus it is automatically added by the ModulePanel Wrapper Class.
 */

package arduinoLight.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import arduinoLight.channelprovider.ActiveStateListener;
import arduinoLight.channelprovider.ChannellistListener;
import arduinoLight.channelprovider.ChannellistProvider;
import arduinoLight.util.IChannel;

@SuppressWarnings("serial")
public class StatusPanel extends JPanel implements ChannellistListener, ActiveStateListener{

	ChannellistProvider _provider;
	
	private JToggleButton _activeButton = new JToggleButton("Activate");
	private DefaultComboBoxModel<ComboBoxChannelItem> _channelBoxModel = new DefaultComboBoxModel<ComboBoxChannelItem>();
	private JComboBox<ComboBoxChannelItem> _channelBox = new JComboBox<ComboBoxChannelItem>(_channelBoxModel);
	private JLabel _channelLabel = new JLabel("Channel: ");
	private JButton _removeButton = new JButton("Remove");
	private JButton _addButton = new JButton("Add");
	
	public StatusPanel(ChannellistProvider provider){
		_provider = provider;
		_provider.addChannellistListener(this);
		initComponents();
	}
	
	private void initComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBorder(new TitledBorder(null, "Status", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));

		_removeButton.setEnabled(false);
		_addButton.addActionListener(new AddButtonHandler());
		_removeButton.addActionListener(new RemoveButtonHandler());
		
		this.add(_activeButton);
		this.add(Box.createHorizontalStrut(15));
		this.add(_channelLabel);
		this.add(_channelBox);
		this.add(_addButton);
		this.add(_removeButton);
	}
	
	class ComboBoxChannelItem{
		
		IChannel _channel;
		
		public ComboBoxChannelItem(IChannel channel){
			_channel = channel;
		}
		
		@Override
		public String toString() {
			return _channel.getName();
		}
		
		public IChannel getChannel(){
			return _channel;
		}
	}
	
	class AddButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			_provider.addChannel();
			_removeButton.setEnabled(true);
		}
	}
	
	class RemoveButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			_provider.removeChannel(getSelectedChannel());
			if(_channelBox.getItemCount() < 1){
				_removeButton.setEnabled(false);
			}
		}
	}
	
	public IChannel getSelectedChannel() {
		ComboBoxChannelItem channelItem = (ComboBoxChannelItem)_channelBox.getSelectedItem(); 
		return channelItem.getChannel();
		
	}

	public void channellistChanged(Object source, List<IChannel> newChannellist) {
		_channelBoxModel.removeAllElements();
		for(IChannel channel : newChannellist){
			ComboBoxChannelItem channelItem = new ComboBoxChannelItem(channel);
			_channelBoxModel.addElement(channelItem);
		}
		
	}

	@Override
	public void activeStateChanged(Object source, boolean newActive)
	{
		if(newActive){
			_activeButton.setText("Deactivate");
		} else {
			_activeButton.setText("Activate");
		}
	}
	
}
