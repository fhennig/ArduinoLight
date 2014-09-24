package arduinoLight.gui.colorpicker;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import arduinoLight.channelholder.colorpicker.ColorPicker;
import arduinoLight.gui.ChannelPanel;
import arduinoLight.gui.TabPanel;
import arduinoLight.util.Color;

@SuppressWarnings("serial")
public class ColorPickingPanel extends TabPanel
{
	private final ColorPicker _colorPicker;
	
	private ChannelPanel _channelPanel;
	private JButton _colorButton;
	
	
	
	public ColorPickingPanel(ColorPicker picker)
	{
		assert picker != null;
		_colorPicker = picker;
		initComponents();
	}
	
	
	
	
	private void initComponents()
	{		
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		this.setLayout(layout);
		_channelPanel = new ChannelPanel(_colorPicker);
		_channelPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
		this.add(_channelPanel);
		_colorButton = new JButton("Pick Color");
		_colorButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 20));
		_colorButton.addActionListener(new ColorButtonListener());
		this.add(_colorButton);
	}
	
	
	
	@Override
	public String getTitle()
	{
		return "Color Picker";
	}
	
	private class ColorButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Color c = _channelPanel.getSelectedChannel().getColor();
			java.awt.Color selectedColor = JColorChooser.showDialog(ColorPickingPanel.this, "Choose a Color",
					new java.awt.Color(c.getR(), c.getG(), c.getB(), c.getA()));
			if (selectedColor != null)
				_channelPanel.getSelectedChannel().setColor(new Color(selectedColor.getRGB()));
		}
	}
}
