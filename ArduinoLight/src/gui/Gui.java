/**
 * The Guided User Interface for the ArduinoLight
 * @author Tom Hohendorf
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.JComboBox;

public class Gui{

	JFrame _frame = new JFrame("Arduino Light");
	
	JTabbedPane _menuTabs = new JTabbedPane(JTabbedPane.TOP);
	
	AmbientlightPanel _ambientlightPanel = new AmbientlightPanel();
	
	JPanel _soundToLightPanel = new JPanel();
	
	CustomColorsPanel _customColorsPanel = new CustomColorsPanel();
	
	JPanel _mainPanel = new JPanel();
	JLabel _connectionSpeedLabel = new JLabel("Connection Speed: 900000");
	JLabel _channelLabel = new JLabel("Channels: ");
	JSpinner _channelSpinner = new JSpinner();
	JLabel _lblNewLabel = new JLabel("COM-Port: "); //TODO Set final Labelname
	@SuppressWarnings("rawtypes")
	JComboBox _comboBox = new JComboBox();
	JButton _connectButton = new JButton("Connect");
		
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
			
		_menuTabs.addTab("AmbiLight", _ambientlightPanel);		//TODO Add third tab for blank Color Settings / Testing
		_menuTabs.addTab("SoundToLight", _soundToLightPanel);
		_menuTabs.addTab("Custom Colors", _customColorsPanel);
		
		_mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.LINE_AXIS));
		_mainPanel.setBorder(new TitledBorder(null, "Connection Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));				
		_mainPanel.add(_connectionSpeedLabel);
		_mainPanel.add(Box.createHorizontalGlue());
		_mainPanel.add(_channelLabel);
		_mainPanel.add(_channelSpinner);
			_channelSpinner.setModel(new SpinnerNumberModel(2, 1, 4, 1));
		_mainPanel.add(Box.createHorizontalGlue());
		_mainPanel.add(_lblNewLabel);
		_mainPanel.add(_comboBox);
		_mainPanel.add(Box.createHorizontalGlue());
		_mainPanel.add(_connectButton);
		
		_frame.add(_menuTabs, BorderLayout.CENTER);
		_frame.add(_mainPanel, BorderLayout.SOUTH);
		_frame.setMinimumSize(new Dimension(600, 450));
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setVisible(true);
		_frame.pack();
	}
}

