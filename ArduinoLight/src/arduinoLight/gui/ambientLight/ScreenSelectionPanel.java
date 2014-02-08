package arduinoLight.gui.ambientLight;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import arduinoLight.channelprovider.generator.ambientlight.Areaselection;

@SuppressWarnings("serial")
public class ScreenSelectionPanel extends JPanel {

	private Areaselection _selection;
	
	JPanel _screenOptionPanel = new JPanel();
	JLabel _rowLabel = new JLabel("Rows: ");
	JSpinner _rowBox;
	JLabel _colLabel = new JLabel("Coloumns: ");
	JSpinner _colBox;
	JLabel _channelLabel = new JLabel("Channel: ");
	@SuppressWarnings("rawtypes")
	JComboBox _channelSpinner = new JComboBox();
	JPanel _table;
	GridLayout _grid;
	
	public ScreenSelectionPanel(Areaselection selection)
	{
		_selection = selection;
		initComponents();
	}
	
	private void initComponents()
	{
		int currentRows = _selection.getRows();
		int currentColumns = _selection.getColumns();
		
		_rowBox = new JSpinner(new SpinnerNumberModel(currentRows, 1, 10, 1));
		_colBox = new JSpinner(new SpinnerNumberModel(currentColumns, 1, 10, 1));
		
		_grid = new GridLayout(currentRows, currentColumns);
		
		_table = new JPanel();
		_table.setLayout(_grid);
		_screenOptionPanel.setLayout(new BoxLayout(_screenOptionPanel, BoxLayout.LINE_AXIS));
		
		this.setLayout(new BorderLayout());
		this.setBorder(new TitledBorder(null, "Screen Selection", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		this.add(_screenOptionPanel, BorderLayout.NORTH);
		this.add(_table, BorderLayout.CENTER);
		
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
		for(int r = 0; r < _grid.getRows();r++){
			for(int c = 0; c < _grid.getColumns(); c++){
				JToggleButton button = new JToggleButton();
				button.addActionListener(new FieldButtonHandler(c, r));
				table.add(button);
			}
		}
	}
	
	private class FieldButtonHandler implements ActionListener
	{
		private final int _x;
		private final int _y;
		
		
		public FieldButtonHandler(int x, int y)
		{
			_x = x;
			_y = y;
		}
		

		@Override
		public void actionPerformed(ActionEvent e)
		{
			JToggleButton button = (JToggleButton)e.getSource();
			_selection.setCell(_x, _y, button.isSelected());
		}
		
	}
}
