/**
 * The Ambientlight Panel that is used in the GUI
 * @author Tom Hohendorf
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class AmbientlightPanel extends JPanel {
	
	JPanel _leftPanel = new JPanel();
	
	JPanel _screenPartPanel = new JPanel();
	JPanel _screenOptionPanel = new JPanel();
	JScrollPane _tablePane = new JScrollPane();
	DefaultTableModel _tableModel = new DefaultTableModel(4, 4);
	JTable _screenTable = new JTable();
	JLabel _rowLabel = new JLabel("Rows: ");
	JComboBox _rowBox = new JComboBox();
	JLabel _colLabel = new JLabel("Coloumns: ");
	JComboBox _colBox = new JComboBox();
	JLabel _channelLabel = new JLabel("Channels: ");
	JSpinner _channelSpinner = new JSpinner();
	
	JPanel _rgbPanel = new JPanel();
	JPanel _redPanel = new JPanel();
	JPanel _greenPanel = new JPanel();
	JPanel _bluePanel = new JPanel();
	JPanel _brightnessPanel = new JPanel();
	JLabel _redLabel = new JLabel("R");
	JSlider _redSlider = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	JLabel _greenLabel = new JLabel("G");
	JSlider _greenSlider = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	JLabel _blueLabel = new JLabel("B");
	JSlider _blueSlider = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	JLabel _brightnessLabel = new JLabel("B");
	JSlider _brightnessSlider = new JSlider(JSlider.VERTICAL, 0, 255, 0);
	
	JPanel _performancePanel = new JPanel();
	JCheckBox _checkBox = new JCheckBox("Checkbox");		//TODO Find better names for Slider and CheckBox
	JSlider _slider = new JSlider();
	
	
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
		
		_performancePanel.add(_checkBox);
		_performancePanel.add(Box.createRigidArea(new Dimension(10, 0)));
		_performancePanel.add(_slider);
		
		_screenOptionPanel.add(_rowLabel);
		_screenOptionPanel.add(_rowBox);
		_screenOptionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_screenOptionPanel.add(_colLabel);
		_screenOptionPanel.add(_colBox);
		_screenOptionPanel.add(Box.createHorizontalGlue());
		_screenOptionPanel.add(_channelLabel);
		_screenOptionPanel.add(_channelSpinner);
		_screenTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		//TODO Fix Table View!!!
		_screenTable.setRowSelectionAllowed(false);
		_screenTable.setFillsViewportHeight(true);
		_screenTable.setColumnSelectionAllowed(true);
		_screenTable.setCellSelectionEnabled(true);
		
		_screenTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"A", "B", "C", "D"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		_screenTable.getColumnModel().getColumn(0).setResizable(false);
		_screenTable.getColumnModel().getColumn(1).setResizable(false);
		_screenTable.getColumnModel().getColumn(2).setResizable(false);
		_screenTable.getColumnModel().getColumn(3).setResizable(false);
		
		_screenPartPanel.add(_screenOptionPanel, BorderLayout.NORTH);
		_screenPartPanel.add(_screenTable, BorderLayout.CENTER);
		
		_redPanel.add(_redSlider);
		_redPanel.add(_redLabel);
		_greenPanel.add(_greenSlider);
		_greenPanel.add(_greenLabel);
		_bluePanel.add(_blueSlider);
		_bluePanel.add(_blueLabel);
		_brightnessPanel.add(_brightnessSlider);
		_brightnessPanel.add(_brightnessLabel);
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
