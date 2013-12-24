/**
 * The User Interface for the Custom Color Module.
 * It is a simple Color Picker for Configuration or Testing Purposes
 */

package arduinoLight.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import arduinoLight.channelprovider.customColors.CustomColorsProvider;

@SuppressWarnings("serial")
public class CustomColorPanel extends TabPanel{

	//Model Reference
	private CustomColorsProvider _colorProvider;
	
	//Controller Reference (Inner Class)
	private SliderPanelHandler _panelHandler = new SliderPanelHandler();
	
	private JPanel _mainPanel = new JPanel();
	private StatusPanel _statusPanel;
	private JPanel _sliderPanel = new JPanel();
	private JPanel _previewPanel = new JPanel();
	private JPanel _colorPanel = new JPanel();
	
	private ColorSlider _redSlider = new ColorSlider("R", 0, 255, 0);
	private ColorSlider _greenSlider = new ColorSlider("G", 0, 255, 0);
	private ColorSlider _blueSlider = new ColorSlider("B", 0, 255, 0);
	private ColorSlider _brightnessSlider = new ColorSlider("B", 0, 255, 255);
	
	public CustomColorPanel(CustomColorsProvider colorProvider){
		_colorProvider = colorProvider;
		_title = "Custom Color";
		 _statusPanel = new StatusPanel(_colorProvider);
		initComponents();
	}

	private void initComponents() {
		
		_previewPanel.setLayout(new BorderLayout());
		_sliderPanel.setLayout(new BoxLayout(_sliderPanel, BoxLayout.LINE_AXIS));
		_mainPanel.setLayout(new BorderLayout());
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		_redSlider.addChangeListener(_panelHandler);
		_greenSlider.addChangeListener(_panelHandler);
		_blueSlider.addChangeListener(_panelHandler);
		_brightnessSlider.addChangeListener(_panelHandler);
		
		_sliderPanel.add(_redSlider);
		_sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_sliderPanel.add(_greenSlider);
		_sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_sliderPanel.add(_blueSlider);
		_sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_sliderPanel.add(_brightnessSlider);
		
		_previewPanel.add(_colorPanel, BorderLayout.CENTER);
		_colorPanel.setBackground(new Color(0, 0, 0));
		
		_previewPanel.setBorder(new TitledBorder(null, "Preview", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		_colorPanel.setBorder(new LineBorder(Color.black));
		_sliderPanel.setBorder(new TitledBorder(null, "RGB-Color", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		
		
		_mainPanel.add(_sliderPanel, BorderLayout.EAST);
		_mainPanel.add(_previewPanel, BorderLayout.CENTER);
		
		this.add(_mainPanel);
		this.add(_statusPanel);
		
	}

	/**
	 * Inner Class that handles the ChangeEvents thrown by the Sliders
	 */
	class SliderPanelHandler implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			arduinoLight.util.Color color = new arduinoLight.util.Color(_brightnessSlider.getValue(), _redSlider.getValue(), _greenSlider.getValue(), _blueSlider.getValue());
				_colorProvider.setChannelcolor(_statusPanel.getSelectedChannel(), color);

		}
	}
}
