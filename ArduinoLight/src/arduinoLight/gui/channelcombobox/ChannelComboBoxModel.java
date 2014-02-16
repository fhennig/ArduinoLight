package arduinoLight.gui.channelcombobox;

import java.util.Set;

import javax.swing.DefaultComboBoxModel;

import arduinoLight.channel.Channel;
import arduinoLight.channelholder.Channelholder;
import arduinoLight.channelholder.ChannelholderListener;
import arduinoLight.channelholder.ChannelsChangedEventArgs;
import arduinoLight.util.DebugConsole;

@SuppressWarnings("serial")
public class ChannelComboBoxModel extends DefaultComboBoxModel<ChannelItem> implements ChannelholderListener
{
	private Channelholder _itemSource;
	
	
	
	public ChannelComboBoxModel() { }
	
	public ChannelComboBoxModel(Channelholder itemSource)
	{
		this(itemSource, false);
	}
	
	public ChannelComboBoxModel(Channelholder itemSource, boolean includeDummy)
	{
		setItemSource(itemSource, includeDummy, null);
	}
	
	
	
	public synchronized void setItemSource(Channelholder source, boolean includeDummy, Channel preselectedChannel)
	{
		if (source == _itemSource)
		{
//			boolean dummyExists = this.getElementAt(0) instanceof DummyChannelItem;
//			if (includeDummy && !dummyExists)
//			{
//				this.insertElementAt(new DummyChannelItem(), 0);
//			}
//			if (preselectedItem != null)
//				this.setSelectedItem(preselectedItem);
//			
			return;
		}
		DebugConsole.print("", "", "" + preselectedChannel);
		printItems(1);
		if (_itemSource != null)
			_itemSource.removeChannelholderListener(this);
		
		_itemSource = source;
		
		Set<Channel> channels = _itemSource.getChannels();
		int insertionIndex = 0;
		if (includeDummy)
		{
			ChannelItem dummy = new DummyChannelItem();
			this.insertElementAt(dummy, insertionIndex++);
			if (preselectedChannel == null)
				this.setSelectedItem(dummy);
		}
		printItems(2);
		for (Channel c : channels)
		{
			ChannelItem cItem = new ChannelItem(c);
			this.insertElementAt(cItem, insertionIndex++);
			if (c.equals(preselectedChannel))
				this.setSelectedItem(cItem);
		}
		//Check if selectedItem has been set:
		if (this.getIndexOf(this.getSelectedItem()) >= insertionIndex)
			this.setSelectedItem(null);
		printItems(3);
		while (this.getSize() > insertionIndex)
		{
			this.removeElementAt(insertionIndex);
		}
		printItems(4);
		_itemSource.addChannelholderListener(this);
	}
	
	public void setSelectedChannel(Channel channel)
	{
		if (channel == null)
			this.setSelectedItem(new DummyChannelItem());
		else
			this.setSelectedItem(new ChannelItem(channel));
	}
	
	public Channel getSelectedChannel()
	{
		ChannelItem cItem = (ChannelItem) this.getSelectedItem();
		if (cItem == null)
			return null;
		
		return cItem.getChannel();
	}
	
	public Channelholder getItemSource()
	{
		return _itemSource;
	}

	/**
	 * This method gets called by the _itemSource if the list of channels changed.
	 */
	@Override
	public void channelsChanged(ChannelsChangedEventArgs e)
	{
		if (e.getSource() != _itemSource)
			return; //for safety
		
		if (e.getAddedChannel() != null)
			this.addElement(new ChannelItem(e.getAddedChannel()));
		
		if (e.getRemovedChannel() != null)
			this.removeElement(new ChannelItem(e.getRemovedChannel()));
		
		printItems(100);
	}
	
	private void printItems(int prefix)
	{
		StringBuilder channels = new StringBuilder();
		for (int i = 0; i < this.getSize(); i++)
		{
			channels.append(this.getElementAt(i).toString() + ", ");
		}
		DebugConsole.print("ChannelCBModel", "debug-print", prefix + ": " + _itemSource + ": " + channels);
	}
}
