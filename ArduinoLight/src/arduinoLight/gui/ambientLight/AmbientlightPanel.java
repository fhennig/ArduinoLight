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

/**
 * This class is a TabPanel that provides a UI to control an Ambientlight object.
 */
@SuppressWarnings("serial")
public class AmbientlightPanel extends TabPanel
{
	private final Ambientlight _ambientLight;
	
	private ScreenSelectionPanel _screenSelectionPanel;
	private JPanel _activationPanel;
	private ChannelPanel _channelPanel;

	private JPanel _colorCorrectionPanel; //TODO this has currently no functionality,
										  //	 also it is too big and should be in its own panel.
	private ColorSlider _redSlider = new ColorSlider("R", 0, 100, 100);
	private ColorSlider _greenSlider = new ColorSlider("G", 0, 100, 100);
	private ColorSlider _blueSlider = new ColorSlider("B", 0, 100, 100);
	private ColorSlider _brightnessSlider = new ColorSlider("B", 0, 100, 100);

	
	
	public AmbientlightPanel(Ambientlight ambientlight)
	{
		_ambientLight = ambientlight;
		initComponents();
	}
	
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		_screenSelectionPanel = new ScreenSelectionPanel();
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
		this.add(_colorCorrectionPanel, gbc);
		
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
	
	private void initRGBPanel()
	{
		_colorCorrectionPanel = new JPanel();
		_colorCorrectionPanel.setLayout(new BoxLayout(_colorCorrectionPanel, BoxLayout.LINE_AXIS));
		_colorCorrectionPanel.setBorder(BorderFactory.createTitledBorder("Color Correction"));
		
		_colorCorrectionPanel.add(_redSlider);
		_colorCorrectionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_colorCorrectionPanel.add(_greenSlider);
		_colorCorrectionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_colorCorrectionPanel.add(_blueSlider);
		_colorCorrectionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_colorCorrectionPanel.add(_brightnessSlider);
	}
	
	
	
	/**
	 * This class acts upon the event that the selected channel changed.
	 */
	private class ChannelPanelHandler implements ActionListener
	{
		/**
		 * If the selected Channel changes, we update the screenSelectionPanel
		 * to display the Areaselection object that the Channel is mapped to.
		 */
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			Channel selectedChannel = _channelPanel.getSelectedChannel();
			Areaselection associatedSelection = _ambientLight.getScreenselection(selectedChannel);
			_screenSelectionPanel.setScreenselection(associatedSelection);
		}
	}

	
	
	/**
	 * This is a method for the TabPanel.
	 * It returns the title used in the tab.
	 */
	@Override
	public String getTitle()
	{
		return "Ambientlight";
	}
}
