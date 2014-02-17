/**
 * The Ambientlight Panel that is used in the GUI
 * @author Tom Hohendorf
 */

package arduinoLight.gui.ambientLight;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import arduinoLight.channel.Channel;
import arduinoLight.channelholder.ambientlight.Ambientlight;
import arduinoLight.channelholder.ambientlight.Areaselection;
import arduinoLight.gui.ChannelPanel;
import arduinoLight.gui.ColorSlider;
import arduinoLight.gui.TabPanel;
import arduinoLight.model.Model;

@SuppressWarnings("serial")
public class AmbientlightPanel extends TabPanel{
	
	Ambientlight _ambientLight = Model.getInstance().getAmbientlight();
	
	ScreenSelectionPanel _screenSelectionPanel;
	JPanel _rgbPanel;
	JPanel _activationPanel;
	ChannelPanel _channelPanel = new ChannelPanel(_ambientLight);

	ColorSlider _redSlider = new ColorSlider("R", 0, 100, 100);
	ColorSlider _greenSlider = new ColorSlider("G", 0, 100, 100);
	ColorSlider _blueSlider = new ColorSlider("B", 0, 100, 100);
	ColorSlider _brightnessSlider = new ColorSlider("B", 0, 100, 100);
	
	
	
	public AmbientlightPanel(String title)
	{
		_title = title;
		initComponents();
	}
	
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		_screenSelectionPanel = new ScreenSelectionPanel(new Areaselection(2, 2));
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 2;
		gbc.weighty = 1;
		gbc.weightx = 1;
		this.add(_screenSelectionPanel, gbc);
		
		initRGBPanel();
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 2;
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		this.add(_rgbPanel, gbc);
		
		gbc.gridy = 1;
		_activationPanel = new AmbientlightStartStopPanel(_ambientLight);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.weighty = 0;
		this.add(_activationPanel, gbc);
		
		_channelPanel = new ChannelPanel(_ambientLight);
		_channelPanel.addActionListener(new ChannelPanelHandler());
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		this.add(_channelPanel, gbc);
	}
	
	private void initChannelPanel()
	{
		//TODO selectionchanged handlers
	}
	
	private void initRGBPanel()
	{
		_rgbPanel = new JPanel();
		_rgbPanel.setLayout(new BoxLayout(_rgbPanel, BoxLayout.LINE_AXIS));
		_rgbPanel.setBorder(BorderFactory.createTitledBorder("Color Correction"));
		
		_rgbPanel.add(_redSlider);
		_rgbPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_rgbPanel.add(_greenSlider);
		_rgbPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_rgbPanel.add(_blueSlider);
		_rgbPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_rgbPanel.add(_brightnessSlider);
	}
	
	private class ChannelPanelHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			Channel selectedChannel = _channelPanel.getSelectedChannel();
			Areaselection associatedSelection = _ambientLight.getScreenselection(selectedChannel);
			//ScreenSelectionPanel.setScreenselection(associatedSelection); TODO
		}
	}
}
