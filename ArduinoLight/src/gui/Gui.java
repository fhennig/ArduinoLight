/**
 * The Guided User Interface for the ArduinoLight
 * @author Tom Hohendorf
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SpinnerListModel;
import javax.swing.JComboBox;

public class Gui{

	private final JFrame _frame = new JFrame("Arduino Light");
	
	private final JTabbedPane _menuTabs = new JTabbedPane(JTabbedPane.TOP);
	
	private final JPanel _ambiLightPanel = new JPanel();	
	
	private final JPanel _soundToLightPanel = new JPanel();
	
	private final JPanel _connectionPanel = new JPanel();
	private final JPanel _panel = new JPanel();
	private final JLabel _connectionSpeedLabel = new JLabel("Connection Speed: 900000");
	private final Component _horizontalStrut_1 = Box.createHorizontalStrut(20);
	private final JLabel _channelLabel = new JLabel("Channels: ");
	private final JSpinner _channelSpinner = new JSpinner();
	private final Component _horizontalStrut = Box.createHorizontalStrut(20);
	private final JLabel _lblNewLabel = new JLabel("New label");
	private final JComboBox _comboBox = new JComboBox();
	private final Component _horizontalStrut_2 = Box.createHorizontalStrut(20);
	private final JButton _connectButton = new JButton("Connect");
		
	public Gui(){
		initComponents();
	}
	
	public static void main(String[] args){
		initLookAndFeel();
		new Gui();
	}
		
	/**
	 * Initialize the Look and Feel
	 * First try is "Nimbus"
	 * Second try is the OS Standard Look and Feel
	 */
	private static void initLookAndFeel(){
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch(Exception e1){
				System.out.println("Could not set a valid Look and Feel!");
			}
		}
	}

	private void initComponents() {
			
		Dimension buttonSize = new Dimension(150,100);
		Dimension frameDimension = new Dimension(800, 550);
		Dimension frameMinDimension = new Dimension(500, 200);
		
		_ambiLightPanel.setLayout(new BorderLayout());
		_soundToLightPanel.setLayout(new BorderLayout());

		_menuTabs.addTab("AmbiLight", _ambiLightPanel);
		_menuTabs.addTab("SoundToLight", _soundToLightPanel);
		
		_connectionPanel.setLayout(new BorderLayout(0, 0));
		_connectionPanel.setBorder(new TitledBorder(null, "Connection Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));				
		_connectionPanel.add(_panel, BorderLayout.NORTH);
		
		_panel.add(_connectionSpeedLabel);
		_panel.add(_horizontalStrut_1);
		_panel.add(_channelLabel);
		_panel.add(_channelSpinner);
			_channelSpinner.setModel(new SpinnerListModel(new String[] {"2", "4"}));
		_panel.add(_horizontalStrut);
		_panel.add(_lblNewLabel);
		_panel.add(_comboBox);
		_panel.add(_horizontalStrut_2);
		_panel.add(_connectButton);
		
		_frame.add(_menuTabs, BorderLayout.CENTER);
		_frame.add(_connectionPanel, BorderLayout.SOUTH);
		_frame.setMinimumSize(frameMinDimension);
		_frame.setSize(frameDimension);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setVisible(true);
		_frame.pack();
	}
}

