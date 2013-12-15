/**
 * The Ambientlight Panel that is used in the GUI
 * @author Tom Hohendorf
 */

package arduinoLight.gui;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class AmbientlightPanel extends JPanel{
	
	JPanel _leftPanel = new JPanel();
	
	JPanel _screenSelectionPanel;

	
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
	
	
	public AmbientlightPanel(ScreenSelectionPanel selectionPanel){
		_screenSelectionPanel = selectionPanel;
		initComponents();
	}
	
	private void initComponents(){
		//
		// Layouts
		//
		_leftPanel.setLayout(new BoxLayout(_leftPanel, BoxLayout.PAGE_AXIS));
		_performancePanel.setLayout(new BoxLayout(_performancePanel, BoxLayout.LINE_AXIS));

		_redPanel.setLayout(new BoxLayout(_redPanel, BoxLayout.PAGE_AXIS));
		_greenPanel.setLayout(new BoxLayout(_greenPanel, BoxLayout.PAGE_AXIS));
		_bluePanel.setLayout(new BoxLayout(_bluePanel, BoxLayout.PAGE_AXIS));
		_brightnessPanel.setLayout(new BoxLayout(_brightnessPanel, BoxLayout.PAGE_AXIS));
		_rgbPanel.setLayout(new BoxLayout(_rgbPanel, BoxLayout.LINE_AXIS));
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		//
		// Borders
		//
		_performancePanel.setBorder(new TitledBorder(null, "Performance", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		_rgbPanel.setBorder(new TitledBorder(null, "Color Correction", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		
		//
		// Adding the Components
		//
		_leftPanel.add(_screenSelectionPanel);
		_leftPanel.add(_performancePanel);
		
		_performancePanel.add(_fpsBox);
		_performancePanel.add(Box.createRigidArea(new Dimension(10, 0)));
		_performancePanel.add(_fpsSlider);
		

		
		_redLabel.setAlignmentX(CENTER_ALIGNMENT);
		_redValue.setAlignmentX(CENTER_ALIGNMENT);
		_greenLabel.setAlignmentX(CENTER_ALIGNMENT);
		_greenValue.setAlignmentX(CENTER_ALIGNMENT);
		_blueLabel.setAlignmentX(CENTER_ALIGNMENT);
		_blueValue.setAlignmentX(CENTER_ALIGNMENT);
		_brightnessLabel.setAlignmentX(CENTER_ALIGNMENT);
		_brightnessValue.setAlignmentX(CENTER_ALIGNMENT);
		

		
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
}
