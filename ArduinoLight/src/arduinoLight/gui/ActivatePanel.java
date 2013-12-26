package arduinoLight.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import arduinoLight.interfaces.Activatable;

@SuppressWarnings("serial")
public class ActivatePanel extends JPanel{

	Activatable _provider;
	
	JToggleButton _activateButton = new JToggleButton("Activate");
	
	public ActivatePanel(Activatable provider){
		_provider = provider;
		initComponents();
	}

	private void initComponents() {
		
		_activateButton.addActionListener(new ButtonHandler());
		
		_activateButton.setPreferredSize(new Dimension(10, 25));
		
		this.setBorder(new TitledBorder(null, "Activate", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		this.setLayout(new GridLayout(1, 1));
		
		this.add(_activateButton);
	}
	
	class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(_activateButton.isSelected()){
				if(_provider.setActive(true)){
					_activateButton.setText("Deactivate");
				} else {
					_activateButton.setSelected(false);
					JOptionPane.showMessageDialog(null, "Could not activate the module!", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				_provider.setActive(false);
				_activateButton.setText("Activate");
			}
		}
	}
	
	public boolean isActive(){
		return _activateButton.isSelected();
	}
}
