/**
 * The Guided User Interface for the ArduinoLight
 * @author Tom Hohendorf
 */

package arduinoLight.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;

public class Gui{

	JFrame _frame = new JFrame("Arduino Light");
	JTabbedPane _menuTabs = new JTabbedPane(JTabbedPane.TOP);
	JPanel _connectionPanel;
	
	Set<ModulePanel> _modulePanels = new HashSet<ModulePanel>();
	
	public Gui(Set<ModulePanel> panels, JPanel connectionPanel){
		_modulePanels = panels;
		_connectionPanel = connectionPanel;
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
		
		buildModuleTabs(_modulePanels);
		
		_frame.add(_menuTabs, BorderLayout.CENTER);
		_frame.add(_connectionPanel, BorderLayout.SOUTH);
		_frame.setMinimumSize(new Dimension(600, 450));
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setVisible(true);
		_frame.pack();
	}
	
	private void buildModuleTabs(Set<ModulePanel> panels){
		for(ModulePanel panel : panels){
			_menuTabs.addTab(panel.getName(), panel);
		}
	}
}

