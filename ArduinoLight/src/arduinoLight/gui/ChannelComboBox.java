package arduinoLight.gui;

import arduinoLight.channel.Channel;
import arduinoLight.gui.comboboxitems.ChannelItem;


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
}
