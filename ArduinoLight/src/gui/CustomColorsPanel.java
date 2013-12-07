package gui;

import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class CustomColorsPanel extends JPanel{

	JColorChooser _colorChooser = new JColorChooser();
	
	public CustomColorsPanel(){
		initComponents();
	}
	
	private void initComponents(){
		_colorChooser.setBorder(new TitledBorder(null, "Color Chooser", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		_colorChooser.setPreviewPanel(new JPanel());
		this.add(_colorChooser);
	}
}
