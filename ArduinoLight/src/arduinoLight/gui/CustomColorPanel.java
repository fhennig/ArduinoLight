package arduinoLight.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import arduinoLight.channelprovider.debugprovider.Debugprovider;

@SuppressWarnings("serial")
public class CustomColorPanel extends JPanel{

	Debugprovider _provider;
	
	SliderPanelHandler _panelHandler = new SliderPanelHandler();
	
	JPanel _leftPanel = new JPanel();
	
	JPanel _statusPanel = new JPanel();
	JCheckBox _activeBox = new JCheckBox("Active");
	JComboBox _channelBox = new JComboBox();
	
	JPanel _sliderPanel = new JPanel();
	JPanel _previewPanel = new JPanel();
	JPanel _colorPanel = new JPanel();
	
	ColorSlider _redSlider = new ColorSlider("R", 0, 255, 0);
	ColorSlider _greenSlider = new ColorSlider("G", 0, 255, 0);
	ColorSlider _blueSlider = new ColorSlider("B", 0, 255, 0);
	ColorSlider _brightnessSlider = new ColorSlider("B", 0, 255, 255);
	
	public CustomColorPanel(Debugprovider provider){
		_provider = provider;
		initComponents();
	}

	private void initComponents() {
		
		_previewPanel.setLayout(new BorderLayout());
		_sliderPanel.setLayout(new BoxLayout(_sliderPanel, BoxLayout.LINE_AXIS));
		_statusPanel.setLayout(new BoxLayout(_statusPanel, BoxLayout.LINE_AXIS));
		_leftPanel.setLayout(new BoxLayout(_leftPanel, BoxLayout.PAGE_AXIS));
		this.setLayout(new BorderLayout());
		
		_redSlider.addChangeListener(_panelHandler);
		_greenSlider.addChangeListener(_panelHandler);
		_blueSlider.addChangeListener(_panelHandler);
		_brightnessSlider.addChangeListener(_panelHandler);
		_activeBox.addChangeListener(new CheckBoxHandler());
		_channelBox.addActionListener(new ComboBoxHandler());
		
		_sliderPanel.add(_redSlider);
		_sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_sliderPanel.add(_greenSlider);
		_sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_sliderPanel.add(_blueSlider);
		_sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_sliderPanel.add(_brightnessSlider);
		
		_statusPanel.add(_activeBox);
		_statusPanel.add(Box.createHorizontalStrut(15));
		_statusPanel.add(_channelBox);
		
		_previewPanel.add(_colorPanel, BorderLayout.CENTER);
		_colorPanel.setBackground(Color.white);
		
		_statusPanel.setBorder(new TitledBorder(null, "Status", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		_previewPanel.setBorder(new TitledBorder(null, "Preview", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		_colorPanel.setBorder(new LineBorder(Color.black));
		_sliderPanel.setBorder(new TitledBorder(null, "RGB-Color", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		
		_leftPanel.add(_previewPanel);
		_leftPanel.add(_statusPanel);
		
		this.add(_sliderPanel, BorderLayout.EAST);
		this.add(_leftPanel, BorderLayout.CENTER);
	}

	class SliderPanelHandler implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			if(e.getSource() == _redSlider){
				
			} else if(e.getSource() == _greenSlider){
				
			} else if(e.getSource() == _blueSlider){
				
			} else if(e.getSource() == _brightnessSlider){
				
			}
		}
	}
	
	class CheckBoxHandler implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			boolean state = _activeBox.isSelected();
			_provider.setActive(state);
		}
		
	}
	
	class ComboBoxHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
}
