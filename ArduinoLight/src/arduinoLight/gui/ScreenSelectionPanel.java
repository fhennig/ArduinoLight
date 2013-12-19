package arduinoLight.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class ScreenSelectionPanel extends JPanel {

	//TODO add model reference
	
	JPanel _screenOptionPanel = new JPanel();
	JLabel _rowLabel = new JLabel("Rows: ");
	JSpinner _rowBox = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
	JLabel _colLabel = new JLabel("Coloumns: ");
	JSpinner _colBox = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
	JLabel _channelLabel = new JLabel("Channel: ");
	@SuppressWarnings("rawtypes")
	JComboBox _channelSpinner = new JComboBox();
	JPanel _table = new JPanel();
	GridLayout _g = new GridLayout(4, 4);
	
	public ScreenSelectionPanel(){
		initComponents();
	}
	
	private void initComponents() {
		_table.setLayout(_g);
		_screenOptionPanel.setLayout(new BoxLayout(_screenOptionPanel, BoxLayout.LINE_AXIS));
		
		this.setLayout(new BorderLayout());
		this.setBorder(new TitledBorder(null, "Screen Selection", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		this.add(_screenOptionPanel, BorderLayout.NORTH);
		this.add(_table, BorderLayout.CENTER);
		
		_rowBox.getModel().setValue(4);
		_colBox.getModel().setValue(4);
		
		_screenOptionPanel.add(_channelLabel);
		_screenOptionPanel.add(_channelSpinner);
		_screenOptionPanel.add(Box.createHorizontalGlue());
		_screenOptionPanel.add(_rowLabel);
		_screenOptionPanel.add(_rowBox);
		_screenOptionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_screenOptionPanel.add(_colLabel);
		_screenOptionPanel.add(_colBox);

		fillTable(_table);
	}
	
	private void fillTable(JPanel table){
		for(int r = 0; r < _g.getRows();r++){
			for(int c = 0; c < _g.getColumns(); c++){
				table.add(new JButton());
			}
		}
	}
}
