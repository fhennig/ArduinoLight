package arduinoLight.gui.channelcombobox;

import arduinoLight.channel.Channel;



@SuppressWarnings("serial")
public class ChannelComboBox extends javax.swing.JComboBox<ChannelItem>
{
	public ChannelComboBox() { super(); }
	
	public ChannelComboBox(ChannelComboBoxModel model)
	{
		super(model);
	}
	
	public ChannelComboBoxModel getModel()
	{
		return (ChannelComboBoxModel) super.getModel();
	}
	
	public void setSelectedChannel(Channel channel)
	{
		getModel().setSelectedChannel(channel);
	}
	
	public Channel getSelectedChannel()
	{
		return getModel().getSelectedChannel();
	}
}
