package arduinoLight.gui.ambientLight;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import arduinoLight.channelprovider.generator.ambientlight.Ambientlight;

public class AmbientlightStartStopPanel extends JPanel
{
	private Ambientlight _ambientlight;
	
	private JSpinner _refreshrateSpinner;
	private JButton _startButton;
	
	public AmbientlightStartStopPanel(Ambientlight ambientlight)
	{
		_ambientlight = ambientlight;
	}
	
	private void initComponents()
	{
		_refreshrateSpinner = new JSpinner(new SpinnerNumberModel(30, 1, Ambientlight.MAX_FREQUENCY, 1));
		_startButton = new JButton("Start");
	}
	
	private class StartButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			
		}
	}
}
