/**
 * The Guided User Interface for the ArduinoLight
 * @author Tom Hohendorf
 */

package arduinoLight.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;

import arduinoLight.gui.connectionPanel.ConnectionPanel;

public class Gui{

	private JFrame _frame = new JFrame("Arduino Light");
	private JTabbedPane _menuTabs = new JTabbedPane(JTabbedPane.TOP);
	private ConnectionPanel _connectionPanel;
	private Set<TabPanel> _modulePanels;
	
	
	public Gui(Set<TabPanel> panels, ConnectionPanel connectionPanel){
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
		
		_frame.addWindowListener(new WindowHandler());
		
		_frame.add(_menuTabs, BorderLayout.CENTER);
		_frame.add((JPanel) _connectionPanel, BorderLayout.SOUTH);
		_frame.setMinimumSize(new Dimension(600, 450));
		//_frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
		_frame.pack();
	}
	
	/**
	 * Adds the Module Panels to the TabbedPane.
	 * @param panels The List of panels that should be added to the TabbedPane
	 */
	private void buildModuleTabs(Set<TabPanel> panels){
		for(TabPanel panel : panels){
			_menuTabs.addTab(panel.getTitle(), panel);
		}
	}
	
	class WindowHandler extends WindowAdapter{
		
		@Override
		 public void windowClosing(WindowEvent e){
			_connectionPanel.disconnect();
			System.exit(0);
		}
	}
}

