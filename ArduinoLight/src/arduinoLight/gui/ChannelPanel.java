/**
 * This Panel holds the Controls necessary to activate a Module and to switch between Channels.
 * Every Module needs this Panel, thus it is automatically added by the ModulePanel Wrapper Class.
 */

package arduinoLight.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import arduinoLight.channel.Channel;
import arduinoLight.channelholder.ChannelsChangedListener;
import arduinoLight.channelholder.ChannelsChangedEventArgs;
import arduinoLight.channelholder.ModifiableChannelholder;
import arduinoLight.gui.channelcombobox.ChannelComboBox;
import arduinoLight.gui.channelcombobox.ChannelComboBoxModel;
import arduinoLight.model.Model;

@SuppressWarnings("serial")
public class ChannelPanel extends JPanel implements ChannelsChangedListener
{
	private ModifiableChannelholder _provider;
	
	private JLabel _channelLabel = new JLabel("Selected Channel: ");
	private ChannelComboBox _channelBox;
	private JButton _removeButton;
	private JButton _addButton;
	private List<ActionListener> _listeners = new ArrayList<ActionListener>();
	
	public ChannelPanel(ModifiableChannelholder provider)
	{
		_provider = provider;
		initComponents();
		_provider.addChannelsChangedListener(this);
	}
	
	private void initComponents()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Channels"));

		_channelBox = new ChannelComboBox(new ChannelComboBoxModel(_provider));
		_channelBox.addActionListener(new ChannelComboBoxHandler());
		
		_addButton = new JButton("Add");
		_addButton.addActionListener(new AddButtonHandler());
		_removeButton = new JButton("Remove");
		_removeButton.addActionListener(new RemoveButtonHandler());
		channelsChanged(null); //Called to enable/disable the removeButton
		
		this.add(_channelLabel);
		this.add(_channelBox);
		this.add(_addButton);
		this.add(_removeButton);
	}
	
	public void addComboBoxListener(ItemListener listener){
		_channelBox.addItemListener(listener);
	}
	
	public Channel getSelectedChannel()
	{
		return _channelBox.getSelectedChannel();
	}
	
	private class AddButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Channel newChannel = Model.getInstance().getChannelFactory().newChannel();
			_provider.addChannel(newChannel);
			_channelBox.setSelectedChannel(newChannel);
		}
	}
	
	private class RemoveButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			_provider.removeChannel(_channelBox.getSelectedChannel());
		}
	}
	
	private class ChannelComboBoxHandler implements ActionListener
	{
		/**
		 * Gets called by the channelComboBox if the selected Item changes.
		 * Fires the Event again but with a different source.
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			ActionEvent evt = new ActionEvent(this, e.getID(), e.getActionCommand());
			for (ActionListener l : _listeners)
				l.actionPerformed(evt);
		}
	}

	/** Gets called by the Provider if its channels change */
	@Override
	public void channelsChanged(ChannelsChangedEventArgs e)
	{
		int channels = _provider.getChannels().size();
		if (channels < 2)
			_removeButton.setEnabled(false);
		else
			_removeButton.setEnabled(true);
	}
	
	public void addActionListener(ActionListener l)
	{
		_listeners.add(l);
	}
	
	public void removeActionListener(ActionListener l)
	{
		_listeners.remove(l);
	}
}
