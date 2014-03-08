package arduinoLight.gui.connectionPanel;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import arduinoLight.arduino.amblone.AmbloneTransmission;
import arduinoLight.channel.Channel;
import arduinoLight.channelholder.Channelholder;
import arduinoLight.gui.ChannelholderItem;
import arduinoLight.gui.SwingUtil;
import arduinoLight.gui.channelcombobox.ChannelComboBox;
import arduinoLight.gui.channelcombobox.ChannelComboBoxModel;
import arduinoLight.model.Model;

@SuppressWarnings("serial")
public class AmbloneChannelPanel extends JPanel
{
	private AmbloneTransmission _amblone;
	
	private JLabel _outputLabel;
	private JComboBox<Integer> _outputComboBox;
	private JLabel _channelLabel;
	private JComboBox<ChannelholderItem> _channelHolderComboBox;
	private ChannelComboBox _channelComboBox;
	
	public AmbloneChannelPanel(AmbloneTransmission ambloneTransmission)
	{
		_amblone = ambloneTransmission;
		initComponents();
	}
	
	private void initComponents()
	{
		_outputLabel = new JLabel("Output: ");
		
		initOutputCB();
		SwingUtil.setPreferredWidth(_outputComboBox, 60);

		_channelLabel = new JLabel("Channel: ");
		
		initChannelholderCB();
		SwingUtil.setPreferredWidth(_channelHolderComboBox, 120);
		
		Channelholder selectedChannelholder = ((ChannelholderItem) _channelHolderComboBox.getSelectedItem()).getChannelholder();
		_channelComboBox = new ChannelComboBox(new ChannelComboBoxModel(selectedChannelholder, true));
		ChannelComboBoxHandler ccbHandler = new ChannelComboBoxHandler();
		_channelComboBox.addActionListener(ccbHandler);
		SwingUtil.setPreferredWidth(_channelComboBox, 100);
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(_outputLabel);
		this.add(_outputComboBox);
		this.add(Box.createHorizontalStrut(20));
		this.add(_channelLabel);
		this.add(_channelHolderComboBox);
		this.add(_channelComboBox);
		
		//preselect something
		_channelHolderComboBox.setSelectedItem(new ChannelholderItem(Model.getInstance().getChannelFactory()));
	}
	
	/** Initializes the OutputComboBox with Model and Handler */
	private void initOutputCB()
	{
		DefaultComboBoxModel<Integer> cbModel = new DefaultComboBoxModel<>();
		Set<Integer> possiblePorts = AmbloneTransmission.getPossiblePorts();
		
		for (Integer i : possiblePorts)
			cbModel.addElement(i);
		
		_outputComboBox = new JComboBox<Integer>(cbModel);
		OutputComboBoxHandler handler = new OutputComboBoxHandler();
		_outputComboBox.addActionListener(handler);
	}
	
	/** Initializes the ChannelholderComboBox with Model and Handler */
	private void initChannelholderCB()
	{
		DefaultComboBoxModel<ChannelholderItem> cbModel = new DefaultComboBoxModel<ChannelholderItem>();
		List<Channelholder> channelholders = Model.getInstance().getChannelholders();
		
		for (Channelholder holder : channelholders)
			cbModel.addElement(new ChannelholderItem(holder));
		
		_channelHolderComboBox = new JComboBox<ChannelholderItem>(cbModel);
		_channelHolderComboBox.setSelectedItem(new ChannelholderItem(Model.getInstance().getChannelFactory()));
		ChannelholderComboBoxHandler chcbHandler = new ChannelholderComboBoxHandler();
		_channelHolderComboBox.addActionListener(chcbHandler);
	}
	
	@Override
	public void setEnabled(boolean enabled)
	{
		for (Component comp : this.getComponents())
		{
			comp.setEnabled(enabled);
		}
	}
	
	private class OutputComboBoxHandler implements ActionListener
	{
		/**
		 * Is called if the selection in the OutputComboBox changed.
		 * If the selected Output is mapped to a channel, the ChannelholderCB and ChannelCB
		 * are updated to display the mapped channel,
		 * else they display 'none'.
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object selectedObj = _outputComboBox.getSelectedItem();
			if (selectedObj != null)
			{
				int selectedPort = ((Integer) selectedObj).intValue();
				Channel associatedChannel = _amblone.getChannel(selectedPort);
				if (associatedChannel != null)
				{
					//Set other ComboBoxes to show associated Channel
					Channelholder holder = Model.getInstance().getChannelholder(associatedChannel); 
					preselectedChannel = associatedChannel;
					_channelHolderComboBox.setSelectedItem(new ChannelholderItem(holder));
					return;
				}
			}
			
			//If the selected Port has no channel set, display the dummys
			preselectedChannel = null;
			_channelHolderComboBox.setSelectedItem(new ChannelholderItem(Model.getInstance().getChannelFactory()));
		}
	}
	
	private Channel preselectedChannel;
	
	private class ChannelholderComboBoxHandler implements ActionListener
	{
		/**
		 * If the selected Channelholder changes, the ChannelComboBox is updated
		 * to show the Channels of the new selected Channelholder.
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object channelholderItemObj = _channelHolderComboBox.getSelectedItem();
			if (channelholderItemObj == null)
				return;
			
			Channelholder channelholder = ((ChannelholderItem) channelholderItemObj).getChannelholder();
			boolean sameChannelholder = _channelComboBox.getModel().getItemSource() == channelholder;
			if (sameChannelholder)
			{
				_channelComboBox.getModel().setSelectedChannel(preselectedChannel);
				return;
			}
			
			_channelComboBox.getModel().setItemSource(channelholder, true, preselectedChannel);
		}
	}
	
	private class ChannelComboBoxHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object selectedPortObj = _outputComboBox.getSelectedItem();
			Channel selectedChannel = _channelComboBox.getModel().getSelectedChannel();
			if (selectedPortObj == null)
				return; //If no port is selected, we do nothing
			
			int selectedPort = ((Integer) selectedPortObj).intValue();
			
			Channel setChannel = _amblone.getChannel(selectedPort);
			
			if (selectedChannel == setChannel)
				return; //If the channel is already set, the 'actionPerformed' was probably not called,
						//because of user input, rather because the displayed item should change.
			
			//selectedChannel might be null, but that means that the output should be cleared.
			//setOutput supports this.
			_amblone.setOutput(selectedPort, selectedChannel);
		}
	}
}
