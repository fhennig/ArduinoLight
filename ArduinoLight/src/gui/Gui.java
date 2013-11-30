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
	private final JButton _connectButton = new JButton("Connect");
	private final JSpinner _channelSpinner = new JSpinner();
	private final JLabel _connectionSpeedLabel = new JLabel("Connection Speed: 900000");
	private final JLabel _channelLabel = new JLabel("Channels: ");
	private final JPanel _panel = new JPanel();
	private final Component _horizontalStrut_1 = Box.createHorizontalStrut(20);
	private final Component _horizontalStrut = Box.createHorizontalStrut(20);
	private final JLabel _lblNewLabel = new JLabel("New label");
	private final Component _horizontalStrut_2 = Box.createHorizontalStrut(20);
	private final JComboBox _comboBox = new JComboBox();
	
		
	public static void main(String[] args){
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
				System.out.println("Ja, is alle!");
			}
		}
		new Gui();
	}
		
	public Gui(){
		initComponents();
	}

	private void initComponents() {
			
		//Dimension buttonSize = new Dimension(150,100);
		Dimension frameDimension = new Dimension(800, 550);
		Dimension frameMinDimension = new Dimension(500, 200);
		
		
		ImageIcon image = new ImageIcon("http://bimg1.mlstatic.com/monitor-dell-22-led-st2220l-hdmi-full-hd_MLM-F-3105639313_092012.jpg");
		_ambiLightPanel.setPreferredSize(new Dimension(200,200));
		_ambiLightPanel.setLayout(new BorderLayout());
		_soundToLightPanel.setLayout(new BorderLayout());
		_menuTabs.addTab("AmbiLight", _ambiLightPanel);
		
		_menuTabs.addTab("SoundToLight", _soundToLightPanel);
		
		_frame.getContentPane().add(_connectionPanel, BorderLayout.SOUTH);
		
		_connectionPanel.setLayout(new BorderLayout(0, 0));
		_connectionPanel.setBorder(new TitledBorder(null, "Connection Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				_connectionPanel.add(_panel, BorderLayout.NORTH);
		
				_panel.add(_connectionSpeedLabel);
				_connectionSpeedLabel.setAlignmentX(0.5f);
				
				_panel.add(_horizontalStrut);
				_panel.add(_channelLabel);
				_channelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				_panel.add(_channelSpinner);
				_channelSpinner.setAlignmentX(0.0f);
				_channelSpinner.setModel(new SpinnerListModel(new String[] {"2", "4"}));
				_panel.add(_horizontalStrut_1);
				
						_panel.add(_lblNewLabel);
						_panel.add(_comboBox);
						_panel.add(_horizontalStrut_2);
						_panel.add(_connectButton);
		
		_frame.setMinimumSize(frameMinDimension);
		_frame.getContentPane().add(_menuTabs, BorderLayout.CENTER);
		_frame.setSize(frameDimension);
		_frame.setVisible(true);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.pack();
	}
}

