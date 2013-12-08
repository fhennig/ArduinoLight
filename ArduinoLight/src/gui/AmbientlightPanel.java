/**
 * The Ambientlight Panel that is used in the GUI
 * @author Tom Hohendorf
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class AmbientlightPanel extends JPanel implements ChangeListener{
	
	JPanel _leftPanel = new JPanel();
	
	JPanel _screenPartPanel = new JPanel();
	JPanel _screenOptionPanel = new JPanel();
	JPanel _table = new JPanel();
	GridLayout _g = new GridLayout(4, 4);
	JLabel _rowLabel = new JLabel("Rows: ");
	JSpinner _rowBox = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
	JLabel _colLabel = new JLabel("Coloumns: ");
	JSpinner _colBox = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
	JLabel _channelLabel = new JLabel("Channel: ");
	@SuppressWarnings("rawtypes")
	JComboBox _channelSpinner = new JComboBox();
	
	JPanel _rgbPanel = new JPanel();
	JPanel _redPanel = new JPanel();
	JPanel _greenPanel = new JPanel();
	JPanel _bluePanel = new JPanel();
	JPanel _brightnessPanel = new JPanel();
	JLabel _redLabel = new JLabel("R");
	JLabel _redValue = new JLabel("100");
	JSlider _redSlider = new JSlider(JSlider.VERTICAL, 0, 100, 100);
	JLabel _greenLabel = new JLabel("G");
	JLabel _greenValue = new JLabel("100");
	JSlider _greenSlider = new JSlider(JSlider.VERTICAL, 0, 100, 100);
	JLabel _blueLabel = new JLabel("B");
	JLabel _blueValue = new JLabel("100");
	JSlider _blueSlider = new JSlider(JSlider.VERTICAL, 0, 100, 100);
	JLabel _brightnessLabel = new JLabel("B");
	JLabel _brightnessValue = new JLabel("100");
	JSlider _brightnessSlider = new JSlider(JSlider.VERTICAL, 0, 100, 100);
	
	JPanel _performancePanel = new JPanel();
	JCheckBox _fpsBox = new JCheckBox("Limit FPS");
	JSlider _fpsSlider = new JSlider();
	
	
	public AmbientlightPanel(){
		initComponents();
	}
	
	private void initComponents(){
		//
		// Layouts
		//
		_leftPanel.setLayout(new BoxLayout(_leftPanel, BoxLayout.PAGE_AXIS));
		_performancePanel.setLayout(new BoxLayout(_performancePanel, BoxLayout.LINE_AXIS));
		_screenOptionPanel.setLayout(new BoxLayout(_screenOptionPanel, BoxLayout.LINE_AXIS));
		_screenPartPanel.setLayout(new BorderLayout());
		_redPanel.setLayout(new BoxLayout(_redPanel, BoxLayout.PAGE_AXIS));
		_greenPanel.setLayout(new BoxLayout(_greenPanel, BoxLayout.PAGE_AXIS));
		_bluePanel.setLayout(new BoxLayout(_bluePanel, BoxLayout.PAGE_AXIS));
		_brightnessPanel.setLayout(new BoxLayout(_brightnessPanel, BoxLayout.PAGE_AXIS));
		_rgbPanel.setLayout(new BoxLayout(_rgbPanel, BoxLayout.LINE_AXIS));
		_table.setLayout(_g);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		//
		// Borders
		//
		_performancePanel.setBorder(new TitledBorder(null, "Performance", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		_screenPartPanel.setBorder(new TitledBorder(null, "Screen Selection", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		_rgbPanel.setBorder(new TitledBorder(null, "Color Correction", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		
		//
		// Adding the Components
		//
		_leftPanel.add(_screenPartPanel);
		_leftPanel.add(_performancePanel);
		
		_performancePanel.add(_fpsBox);
		_performancePanel.add(Box.createRigidArea(new Dimension(10, 0)));
		_performancePanel.add(_fpsSlider);
		
		_screenOptionPanel.add(_channelLabel);
		_screenOptionPanel.add(_channelSpinner);
		_screenOptionPanel.add(Box.createHorizontalGlue());
		_screenOptionPanel.add(_rowLabel);
		_screenOptionPanel.add(_rowBox);
		_screenOptionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_screenOptionPanel.add(_colLabel);
		_screenOptionPanel.add(_colBox);
		
		
		
		_screenPartPanel.add(_screenOptionPanel, BorderLayout.NORTH);
		_screenPartPanel.add(_table, BorderLayout.CENTER);
		fillTable(_table);
		
		_redLabel.setAlignmentX(CENTER_ALIGNMENT);
		_redValue.setAlignmentX(CENTER_ALIGNMENT);
		_greenLabel.setAlignmentX(CENTER_ALIGNMENT);
		_greenValue.setAlignmentX(CENTER_ALIGNMENT);
		_blueLabel.setAlignmentX(CENTER_ALIGNMENT);
		_blueValue.setAlignmentX(CENTER_ALIGNMENT);
		_brightnessLabel.setAlignmentX(CENTER_ALIGNMENT);
		_brightnessValue.setAlignmentX(CENTER_ALIGNMENT);
		
		_rowBox.getModel().setValue(4);
		_colBox.getModel().setValue(4);
		_rowBox.addChangeListener(this);
		_colBox.addChangeListener(this);
		_redSlider.addChangeListener(this);
		_greenSlider.addChangeListener(this);
		_blueSlider.addChangeListener(this);
		_brightnessSlider.addChangeListener(this);
		
		_redPanel.add(_redSlider);
		_redPanel.add(_redLabel);
		_redPanel.add(_redValue);
		_greenPanel.add(_greenSlider);
		_greenPanel.add(_greenLabel);
		_greenPanel.add(_greenValue);
		_bluePanel.add(_blueSlider);
		_bluePanel.add(_blueLabel);
		_bluePanel.add(_blueValue);
		_brightnessPanel.add(_brightnessSlider);
		_brightnessPanel.add(_brightnessLabel);
		_brightnessPanel.add(_brightnessValue);
		_rgbPanel.add(_redPanel);
		_rgbPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_rgbPanel.add(_greenPanel);
		_rgbPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_rgbPanel.add(_bluePanel);
		_rgbPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_rgbPanel.add(_brightnessPanel);
		
		this.add(_leftPanel);
		this.add(_rgbPanel);
	}
	
	private void editTable(int rowCount,int colCount){
		
	}
	
	private void fillTable(JPanel table){
		for(int r = 0; r < _g.getRows();r++){
			for(int c = 0; c < _g.getColumns(); c++){
				table.add(new JButton());
			}
		}
	}
		
	public void stateChanged(ChangeEvent e){
		
		if(e.getSource() == _redSlider){
			_redValue.setText(Integer.toString(_redSlider.getValue()));
		} else if(e.getSource() == _greenSlider){
			_greenValue.setText(Integer.toString(_greenSlider.getValue()));
		} else if(e.getSource() == _blueSlider){
			_blueValue.setText(Integer.toString(_blueSlider.getValue()));
		} else if(e.getSource() == _brightnessSlider){
			_brightnessValue.setText(Integer.toString(_brightnessSlider.getValue()));
		} else if(e.getSource() == _rowBox){
			editTable((int)_rowBox.getValue(), (int) _colBox.getValue());
		} else if(e.getSource() == _colBox){
			editTable((int)_rowBox.getValue(), (int)_colBox.getValue());
		}
	}
}
