package arduinoLight.gui.ambientLight;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import arduinoLight.channelholder.ambientlight.Ambientlight;
import arduinoLight.interfaces.propertyListeners.ActiveListener;

@SuppressWarnings("serial")
public class AmbientlightStartStopPanel extends JPanel implements ActiveListener
{
	private Ambientlight _ambientlight;
	
	private JLabel _refreshrateLabel;
	private JSpinner _refreshrateSpinner;
	private JButton _startButton;
	
	public AmbientlightStartStopPanel(Ambientlight ambientlight)
	{
		_ambientlight = ambientlight;
		_ambientlight.addActiveListener(this);
		initComponents();
	}
	
	private void initComponents()
	{
		this.setBorder(BorderFactory.createTitledBorder("Activation"));
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		_refreshrateLabel = new JLabel("Refresh Rate: ");
		this.add(_refreshrateLabel, gbc);
		
		_refreshrateSpinner = new JSpinner(new SpinnerNumberModel(30, 1, Ambientlight.MAX_FREQUENCY, 1));
		gbc.gridx = 1;
		this.add(_refreshrateSpinner, gbc);
		
		if (_ambientlight.isActive())
			_startButton = new JButton("Stop");
		else
			_startButton = new JButton("Start");
		_startButton.addActionListener(new StartButtonHandler());
		gbc.gridx = 2;
		gbc.weightx = 1;
		gbc.ipadx = 18;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 10, 0, 0);
		this.add(_startButton, gbc);
	}
	
	private int getSelectedRefreshRate()
	{
		return (Integer) _refreshrateSpinner.getValue();
	}
	
	private class StartButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (_ambientlight.isActive())
			{
				_ambientlight.stop();
			}
			else
			{
				_ambientlight.start(getSelectedRefreshRate());
			}
		}
	}

	/** gets called by ambientlight if the active-status changes. */
	@Override
	public void activeChanged(final Object source, final boolean newActive)
	{
		if (!SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override public void run() { activeChanged(source, newActive); }
			});
		}
		
		if (newActive)
		{
			_startButton.setText("Stop");
			_refreshrateSpinner.setEnabled(false);
		}
		else
		{
			_startButton.setText("Start");
			_refreshrateSpinner.setEnabled(true);
		}
	}
}
