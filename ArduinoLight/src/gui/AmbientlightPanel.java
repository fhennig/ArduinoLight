package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class AmbientlightPanel extends JPanel {
	
	JPanel _leftPanel = new JPanel();
	
	JPanel _screenPartPanel = new JPanel();
	
	JPanel _rgbSliderPanel = new JPanel();
	JLabel _redLabel = new JLabel("Red: ");
	JSlider _redSlider = new JSlider();
	JLabel _greenLabel = new JLabel("Green: ");
	JSlider _greenSlider = new JSlider();
	JLabel _blueLabel = new JLabel("Blue: ");
	JSlider _blueSlider = new JSlider();
	JLabel _brightnessLabel = new JLabel("Brightness: ");
	JSlider _brightnessSlider = new JSlider();
	
	JPanel _performancePanel = new JPanel();
	
	
	public AmbientlightPanel(){
		initComponents();
	}
	
	private void initComponents(){
		
		
		_leftPanel.setLayout(new BoxLayout(_leftPanel, BoxLayout.LINE_AXIS));
		_leftPanel.add(_screenPartPanel);
		_leftPanel.add(_performancePanel);
		
		_rgbSliderPanel.setLayout(new BoxLayout(_rgbSliderPanel, BoxLayout.PAGE_AXIS));
		
		_rgbSliderPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		_rgbSliderPanel.add(Box.createVerticalGlue());
		_rgbSliderPanel.add(_redLabel);
		_rgbSliderPanel.add(_redSlider);
		_rgbSliderPanel.add(Box.createVerticalGlue());
		_rgbSliderPanel.add(_greenLabel);
		_rgbSliderPanel.add(_greenSlider);
		_rgbSliderPanel.add(Box.createVerticalGlue());
		_rgbSliderPanel.add(_blueLabel);
		_rgbSliderPanel.add(_blueSlider);
		_rgbSliderPanel.add(Box.createVerticalGlue());
		_rgbSliderPanel.add(_brightnessLabel);
		_rgbSliderPanel.add(_brightnessSlider);
		_rgbSliderPanel.add(Box.createVerticalGlue());
		_rgbSliderPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		_rgbSliderPanel.setBorder(new TitledBorder(null, "Color Correction", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(_leftPanel);
		this.add(_rgbSliderPanel);
		this.add(Box.createRigidArea(new Dimension(5, 0)));
	}
}
