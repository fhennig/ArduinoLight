package arduinoLight.gui.ambientLight;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import arduinoLight.channelholder.ambientlight.Areaselection;

@SuppressWarnings("serial")
public class ScreenSelectionPanel extends JPanel
{
	private static final int _MAX_ROWS = 10;
	private static final int _MAX_COLS = 10;
	
	private Areaselection _selection;
	
	private JLabel _rowLabel = new JLabel("Rows: ");
	private JSpinner _rowBox;
	private JLabel _colLabel = new JLabel("Coloumns: ");
	private JSpinner _colBox;
	private JPanel  _tablePanel;
	private JButton _clearBtn;
	
	public ScreenSelectionPanel()
	{
		this(null);
	}
	
	public ScreenSelectionPanel(Areaselection selection)
	{
		initComponents();
		setScreenselection(selection);
	}
	
	private void initComponents()
	{		
		initSpinners();
		_clearBtn = new JButton("Clear");
		_clearBtn.addActionListener(new ClearHandler());
		_tablePanel = new JPanel();
		
		this.setLayout(new BorderLayout());
		this.setBorder(new TitledBorder(null, "Screen Selection", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		
		JPanel screenOptionPanel = new JPanel();
		screenOptionPanel.setLayout(new BoxLayout(screenOptionPanel, BoxLayout.LINE_AXIS));
		
		screenOptionPanel.add(_rowLabel);
		screenOptionPanel.add(_rowBox);
		screenOptionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		screenOptionPanel.add(_colLabel);
		screenOptionPanel.add(_colBox);
		screenOptionPanel.add(Box.createHorizontalGlue());
		screenOptionPanel.add(_clearBtn);
		
		this.add(screenOptionPanel, BorderLayout.NORTH);
		this.add(_tablePanel, BorderLayout.CENTER);
	}
	
	/** Initializes the row and column spinners */
	private void initSpinners()
	{
		_rowBox = new JSpinner(new SpinnerNumberModel(1, 1, _MAX_ROWS, 1));
		_colBox = new JSpinner(new SpinnerNumberModel(1, 1, _MAX_COLS, 1));
		SpinnerHandler handler = new SpinnerHandler();
		_rowBox.addChangeListener(handler);
		_colBox.addChangeListener(handler);
	}	
	
	/**
	 * Sets the currently displayed selection to the given selection.
	 * null is also a supported value. If null is given, the UI will be grayed out.
	 */
	public void setScreenselection(Areaselection selection)
	{
		_selection = selection;
		
		int rows = 1;
		int cols = 1;
		boolean uiEnabled = false;
		
		if (_selection != null)
		{
			rows = Math.min(_selection.getRows(), _MAX_ROWS);
			cols = Math.min(_selection.getColumns(), _MAX_COLS);
			//Change the selection dimensions to fit the values supported by the UI
			_selection.changeSize(cols, rows);
			
			uiEnabled = true;
		}
		
		_rowBox.setValue(rows);
		_colBox.setValue(cols);
		componentsSetEnable(uiEnabled);
		repaintTable();
	}
	
	private void componentsSetEnable(boolean b)
	{
		_rowBox.setEnabled(b);
		_colBox.setEnabled(b);
		_clearBtn.setEnabled(b);
		
		Component[] tableBtns = _tablePanel.getComponents();
		for (Component tableBtn : tableBtns)
		{
			tableBtn.setEnabled(b);
		}
	}

	/**
	 * Creates the grid with the toggle buttons in it.
	 * The dimensions of the table are determined by the dimensions of the screen selection.
	 * This method should be called after the selection changed or its dimensions changed.
	 */
	private void repaintTable()
	{
		_tablePanel.removeAll();
		
		if (_selection == null)
			return;
		
		int rows = _selection.getRows();
		int cols = _selection.getColumns();
		
		_tablePanel.setLayout(new GridLayout(rows, cols));
		
		for (int r = 0; r < rows; r++)
			for (int c = 0; c <cols; c++)
			{
				CellButton cBtn = new CellButton(c, r);
				cBtn.setSelected(_selection.getCell(c, r));
				_tablePanel.add(cBtn);
			}
		
		_tablePanel.revalidate();
	}
	
	
	private class ClearHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			selectionSetAllCells(false);
		}
	}
	
	private void selectionSetAllCells(boolean b)
	{
		//_selection.setAll(b) is not used, because the UI needs to be updated anyway.
		//cleaner would be an event thrown by the selection, however the overhead produced
		//by that would be out of proportion to the gained architectural improvements.
		Component[] tableComps = _tablePanel.getComponents();
		for (Component cb : tableComps)
		{
			if (cb instanceof CellButton)
				((CellButton) cb).setSelected(b);
		}
	}
	
	private class SpinnerHandler implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent e)
		{
			int rows = (int) _rowBox.getValue();
			int cols = (int) _colBox.getValue();
			
			_selection.changeSize(cols, rows);
			repaintTable();
		}
	}
	
	private class CellButton extends JToggleButton implements ActionListener
	{
		private final int _x;
		private final int _y;
		
		public CellButton(int x,int y)
		{
			_x = x;
			_y = y;
			this.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (arg0.getSource() != this)
				return;
			
			_selection.setCell(_x, _y, isSelected());
		}
	}
}
