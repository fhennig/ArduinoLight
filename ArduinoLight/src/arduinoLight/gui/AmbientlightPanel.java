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

	ColorSlider _redSlider = new ColorSlider("R", 0, 100, 100);
	ColorSlider _greenSlider = new ColorSlider("G", 0, 100, 100);
	ColorSlider _blueSlider = new ColorSlider("B", 0, 100, 100);
	ColorSlider _brightnessSlider = new ColorSlider("B", 0, 100, 100);
	
	JPanel _rgbPanel = new JPanel();
	
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
		
		_rgbPanel.add(_redSlider);
		_rgbPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_rgbPanel.add(_greenSlider);
		_rgbPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_rgbPanel.add(_blueSlider);
		_rgbPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_rgbPanel.add(_brightnessSlider);
		
		this.add(_leftPanel);
		this.add(_rgbPanel);
	}
}
