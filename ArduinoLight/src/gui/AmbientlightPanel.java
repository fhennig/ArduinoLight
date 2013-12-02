/**
 * The Ambientlight Panel that is used in the GUI
 * @author Tom Hohendorf
 */

package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class AmbientlightPanel extends JPanel {
	
	JPanel _leftPanel = new JPanel();
	
	JPanel _screenPartPanel = new JPanel();
	JPanel _screenOptionPanel = new JPanel();
	JTable _screenTable = new JTable(new DefaultTableModel(4, 4));
	JComboBox _rowBox = new JComboBox();
	JComboBox _colBox = new JComboBox();
	JSpinner _channelSpinner = new JSpinner();
	
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
	JCheckBox _checkBox = new JCheckBox("Checkbox");		//TODO Find better names for Slider and CheckBox
	JSlider _slider = new JSlider();
	
	
	public AmbientlightPanel(){
		initComponents();
	}
	
	private void initComponents(){
		
		
		_leftPanel.setLayout(new BoxLayout(_leftPanel, BoxLayout.PAGE_AXIS));
		_leftPanel.add(_screenPartPanel);
		_leftPanel.add(_performancePanel);
		
		_performancePanel.add(_checkBox);
		_performancePanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_performancePanel.add(_slider);
		_performancePanel.setBorder(new TitledBorder(null, "Performance", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		_performancePanel.setLayout(new BoxLayout(_performancePanel, BoxLayout.LINE_AXIS));
		
		_screenOptionPanel.add(_rowBox);
		_screenOptionPanel.add(_colBox);
		_screenOptionPanel.add(_channelSpinner);
		_screenOptionPanel.setLayout(new GridLayout(1, 3));
		_screenPartPanel.add(_screenOptionPanel);
		_screenPartPanel.add(_screenTable);
		_screenPartPanel.setLayout(new BoxLayout(_screenPartPanel, BoxLayout.PAGE_AXIS));
		_screenPartPanel.setBorder(new TitledBorder(null, "Screen Selection", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		
		_rgbSliderPanel.setLayout(new BoxLayout(_rgbSliderPanel, BoxLayout.PAGE_AXIS));
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
		_rgbSliderPanel.setBorder(new TitledBorder(null, "Color Correction", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(_leftPanel);
		this.add(_rgbSliderPanel);
	}
}
