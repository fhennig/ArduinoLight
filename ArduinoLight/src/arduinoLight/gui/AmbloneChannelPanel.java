package arduinoLight.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import arduinoLight.arduino.amblone.AmbloneTransmission;
import arduinoLight.channel.Channel;
import arduinoLight.channelwriter.Channelholder;
import arduinoLight.gui.comboboxitems.ChannelItem;
import arduinoLight.gui.comboboxitems.ChannelholderItem;
import arduinoLight.gui.comboboxitems.DummyChannelItem;
import arduinoLight.gui.comboboxitems.DummyChannelholderItem;
import arduinoLight.model.Model;

@SuppressWarnings("serial")
public class AmbloneChannelPanel extends JPanel
{
	private AmbloneTransmission _amblone;
	
	private JLabel _outputLabel;
	private JComboBox<Integer> _outputComboBox;
	private JLabel _channelLabel;
	private JComboBox<ChannelholderItem> _channelHolderComboBox;
	private JComboBox<ChannelItem> _channelComboBox;
	
	public AmbloneChannelPanel(AmbloneTransmission ambloneTransmission)
	{
		_amblone = ambloneTransmission;
		initComponents();
		refreshOutputCB();
		refreshChannelholderCB();
		refreshChannelCB();
	}
	
	private void initComponents()
	{
		_outputLabel = new JLabel("Output: ");
		
		ComboBoxModel<Integer> outputCBModel = new DefaultComboBoxModel<Integer>();
		_outputComboBox = new JComboBox<Integer>(outputCBModel);
		SwingUtil.setPreferredWidth(_outputComboBox, 60);
		OutputComboBoxHandler ocbHandler = new OutputComboBoxHandler();
		_outputComboBox.addActionListener(ocbHandler);
		//_outputComboBox.addPopupMenuListener(ocbHandler); //currently disabled, ports will never change

		_channelLabel = new JLabel("Channel: ");
		
		ComboBoxModel<ChannelholderItem> channelHolderCBModel = new DefaultComboBoxModel<ChannelholderItem>();
		_channelHolderComboBox = new JComboBox<ChannelholderItem>(channelHolderCBModel);
		SwingUtil.setPreferredWidth(_channelHolderComboBox, 120);
		ChannelholderComboBoxHandler chcbHandler = new ChannelholderComboBoxHandler();
		_channelHolderComboBox.addActionListener(chcbHandler);
		_channelHolderComboBox.addPopupMenuListener(chcbHandler);
		
		ComboBoxModel<ChannelItem> channelCBModel = new DefaultComboBoxModel<ChannelItem>();
		_channelComboBox = new JComboBox<ChannelItem>(channelCBModel);
		SwingUtil.setPreferredWidth(_channelComboBox, 100);
		ChannelComboBoxHandler ccbHandler = new ChannelComboBoxHandler();
		_channelComboBox.addActionListener(ccbHandler);
		_channelComboBox.addPopupMenuListener(ccbHandler);
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(_outputLabel);
		this.add(_outputComboBox);
		this.add(Box.createHorizontalStrut(20));
		this.add(_channelLabel);
		this.add(_channelHolderComboBox);
		this.add(_channelComboBox);
	}
	
	@Override
	public void setEnabled(boolean enabled)
	{
		for (Component comp : this.getComponents())
		{
			comp.setEnabled(enabled);
		}
	}
	
	/** Reloads the possible ports, tries to keep current selection intact */
	private void refreshOutputCB()
	{
		Object selectedItem = _outputComboBox.getSelectedItem();
		DefaultComboBoxModel<Integer> cbModel = (DefaultComboBoxModel<Integer>) _outputComboBox.getModel();
		cbModel.removeAllElements();
		
		Set<Integer> possiblePorts = AmbloneTransmission.getPossiblePorts();
		
		for (Integer i : possiblePorts)
			cbModel.addElement(i);
		
		if (selectedItem != null)
			_outputComboBox.setSelectedItem(selectedItem);
	}
	
	/** Reloads the Channelholders, tries to keep current selection intact */
	private void refreshChannelholderCB()
	{
		Object selectedItem = _channelHolderComboBox.getSelectedItem();
		DefaultComboBoxModel<ChannelholderItem> cbModel = (DefaultComboBoxModel<ChannelholderItem>) _channelHolderComboBox.getModel();
		cbModel.removeAllElements();
		cbModel.addElement(new DummyChannelholderItem());
		
		List<Channelholder> channelholders = Model.getInstance().getChannelholders();
		
		for (Channelholder holder : channelholders)
			cbModel.addElement(new ChannelholderItem(holder));
		
		if (selectedItem != null)
			_channelHolderComboBox.setSelectedItem(selectedItem);  //Try to set the previously selected Item
	}
	
	/**
	 * Loads the Channels for the currently selected Channelholder.
	 * Tries to keep the currently selected Channel selected.
	 **/
	private void refreshChannelCB()
	{
		Object selectedItem = _channelComboBox.getSelectedItem();
		DefaultComboBoxModel<ChannelItem> channelCBModel = (DefaultComboBoxModel<ChannelItem>) _channelComboBox.getModel();
		channelCBModel.removeAllElements();
		channelCBModel.addElement(new DummyChannelItem());
		
		Object holderObj = _channelHolderComboBox.getSelectedItem();
		if (holderObj == null || holderObj instanceof DummyChannelholderItem)
			return;
		
		ChannelholderItem holderItem = (ChannelholderItem) holderObj;
		
		Set<Channel> channels = holderItem.getChannelholder().getChannels();
		
		for (Channel c : channels)
			channelCBModel.addElement(new ChannelItem(c));

		if (selectedItem != null)
			_channelComboBox.setSelectedItem(selectedItem);  //Try to set the previously selected Item
	}
	
	private class OutputComboBoxHandler implements ActionListener, PopupMenuListener
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
					Channelholder holder = Model.getInstance().getChannelholder(associatedChannel);
					_channelHolderComboBox.setSelectedItem(new ChannelholderItem(holder));
					_channelComboBox.setSelectedItem(new ChannelItem(associatedChannel));
					return;
				}
			}
			
			_channelHolderComboBox.setSelectedItem(new DummyChannelholderItem());
			_channelComboBox.setSelectedItem(new DummyChannelItem());
		}

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e)
		{
			refreshOutputCB();
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) { }

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { }
	}
	
	private class ChannelholderComboBoxHandler implements ActionListener, PopupMenuListener
	{
		/**
		 * If the selected Channelholder changes, the ChannelComboBox is updated
		 * to show the Channels of the new selected Channelholder.
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			refreshChannelCB();
		}

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e)
		{
			refreshChannelholderCB();
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) { }

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { }
	}
	
	private class ChannelComboBoxHandler implements ActionListener, PopupMenuListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object selectedPortObj = _outputComboBox.getSelectedItem();
			if (selectedPortObj == null)
				return; //If no port is selected, we do nothing
			
			int selectedPort = ((Integer) selectedPortObj).intValue();
			
			Object selectedChannelObj = _channelComboBox.getSelectedItem();
			if (selectedChannelObj instanceof DummyChannelItem)
			{
				_amblone.clearOutput(selectedPort);
			}
			else if (selectedChannelObj instanceof ChannelItem)
			{
				_amblone.setOutput(selectedPort, ((ChannelItem) selectedChannelObj).getChannel());
			}
		}
		
		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e)
		{
			refreshChannelCB();
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) { }

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { }
	}
}
