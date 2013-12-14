/**
 * The Guided User Interface for the ArduinoLight
 * @author Tom Hohendorf
 */

package arduinoLight.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;

public class Gui{

	JFrame _frame = new JFrame("Arduino Light");
	
	JTabbedPane _menuTabs = new JTabbedPane(JTabbedPane.TOP);
	
	AmbientlightPanel _ambientlightPanel;
	
	JPanel _soundToLightPanel = new JPanel();
	
	JColorChooser _colorChooser = new JColorChooser();
	
	JPanel _serialConnectionPanel;
		
	
	public Gui(SerialConnectionPanel connectionPanel, AmbientlightPanel ambiPanel){
		_serialConnectionPanel = connectionPanel;
		_ambientlightPanel = ambiPanel;
		initComponents();
	}
		
	/**
	 * Sets the Look and Feel to the System L&F
	 */
	public static void initLookAndFeel(){
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
				System.out.println("Could not set a valid Look and Feel!");
		}
	}

	private void initComponents() {
		_colorChooser.setPreviewPanel(new JPanel());	
		
		_menuTabs.addTab("AmbiLight", _ambientlightPanel);		//TODO Add third tab for blank Color Settings / Testing
		_menuTabs.addTab("SoundToLight", _soundToLightPanel);
		_menuTabs.addTab("Custom Colors", _colorChooser);
		
		_frame.add(_menuTabs, BorderLayout.CENTER);
		_frame.add(_serialConnectionPanel, BorderLayout.SOUTH);
		_frame.setMinimumSize(new Dimension(600, 450));
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setVisible(true);
		_frame.pack();
	}
}

