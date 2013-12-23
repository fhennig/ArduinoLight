/**
 * The Guided User Interface for the ArduinoLight
 * @author Tom Hohendorf
 */

package arduinoLight.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;

public class Gui{

	private JFrame _frame = new JFrame("Arduino Light");
	private JTabbedPane _menuTabs = new JTabbedPane(JTabbedPane.TOP);
	private JPanel _connectionPanel;
	
	private Set<JPanel> _modulePanels;
	
	public Gui(Set<JPanel> panels, JPanel connectionPanel){
		_modulePanels = panels;
		_connectionPanel = connectionPanel;
		initComponents();
	}
		
	/**
	 * Tries to set the Look and Feel to Nimbus.
	 */
	public static void initLookAndFeel(){
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
				System.out.println("Could not set a valid Look and Feel!");
		}
	}

	private void initComponents() {
		
		buildModuleTabs(_modulePanels);
		
		_frame.add(_menuTabs, BorderLayout.CENTER);
		_frame.add(_connectionPanel, BorderLayout.SOUTH);
		_frame.setMinimumSize(new Dimension(600, 450));
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
		_frame.pack();
	}
	
	/**
	 * Adds the Module Panels to the TabbedPane.
	 * @param panels The List of panels that should be added to the TabbedPane
	 */
	private void buildModuleTabs(Set<JPanel> panels){
		for(JPanel panel : panels){
			_menuTabs.addTab(panel.getName(), panel);
		}
	}
}

