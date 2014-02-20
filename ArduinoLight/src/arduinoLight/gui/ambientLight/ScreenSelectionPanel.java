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
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import arduinoLight.channelholder.ambientlight.Areaselection;

@SuppressWarnings("serial")
public class ScreenSelectionPanel extends JPanel
{
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
	
	public void setScreenselection(Areaselection selection)
	{
		_selection = selection;
		if (_selection == null)
		{
			_colBox.setValue(1);
			_rowBox.setValue(1);
			componentsSetEnable(false);
			initTable();
			return;
		}

		//TODO here we change the size of the model, just because it does not fit
		//	   to the screen. Is this the appropriate action?
		int rows = Math.min(Math.max(1, _selection.getRows()), 10);
		int columns = Math.min(Math.max(1, _selection.getColumns()), 10);
		
		_selection.changeSize(columns, rows);
		
		_rowBox.setValue(rows);
		_colBox.setValue(columns);
		
		componentsSetEnable(true);
		
		initTable();
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
	
	private void initSpinners()
	{
		_rowBox = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
		_colBox = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
		SpinnerHandler handler = new SpinnerHandler();
		_rowBox.addChangeListener(handler);
		_colBox.addChangeListener(handler);
	}	

	private void initTable()
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
			_selection.setAll(false);
			Component[] tableComps = _tablePanel.getComponents();
			for (Component cb : tableComps)
			{
				if (cb instanceof CellButton)
					((CellButton) cb).setSelected(false);
			}
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
			initTable();
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
