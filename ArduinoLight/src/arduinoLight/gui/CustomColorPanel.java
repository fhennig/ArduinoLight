package arduinoLight.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.transform.Source;

import arduinoLight.channelprovider.debugprovider.Debugprovider;

@SuppressWarnings("serial")
public class CustomColorPanel extends JPanel implements ChangeListener{

	Debugprovider _provider;
	
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
		this.setLayout(new BorderLayout());
		
		_redSlider.addChangeListener(this);
		_greenSlider.addChangeListener(this);
		_blueSlider.addChangeListener(this);
		_brightnessSlider.addChangeListener(this);
		
		_sliderPanel.add(_redSlider);
		_sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_sliderPanel.add(_greenSlider);
		_sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_sliderPanel.add(_blueSlider);
		_sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_sliderPanel.add(_brightnessSlider);
		
		_previewPanel.add(_colorPanel, BorderLayout.CENTER);
		_colorPanel.setBackground(Color.white);
		
		
		_previewPanel.setBorder(new TitledBorder(null, "Preview", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		_colorPanel.setBorder(new LineBorder(Color.black));
		_sliderPanel.setBorder(new TitledBorder(null, "RGB-Color", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		
		this.add(_sliderPanel, BorderLayout.EAST);
		this.add(_previewPanel);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == _redSlider){
			
		} else if(e.getSource() == _greenSlider){
			
		} else if(e.getSource() == _blueSlider){
			
		} else if(e.getSource() == _brightnessSlider){
			
		}
	}
}
