package arduinoLight.gui;

import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.transform.Source;

public class ColorSlider extends JPanel{

	Set<ChangeListener> _listener = new HashSet<ChangeListener>();
	
	JSlider _slider;
	JLabel _nameLabel;
	JLabel _valueLabel;
	
	public ColorSlider(String name, int min, int max, int value){
		_nameLabel = new JLabel(name);
		_valueLabel = new JLabel(Integer.toString(value));
		_slider = new JSlider(JSlider.VERTICAL, min, max, value);
		initComponents();
	}

	private void initComponents() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		_slider.setAlignmentX(CENTER_ALIGNMENT);
		_nameLabel.setAlignmentX(CENTER_ALIGNMENT);
		_valueLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		_slider.addChangeListener(new SliderHandler());
		
		this.add(_slider);
		this.add(_nameLabel);
		this.add(_valueLabel);
	}
	
	public void setValueLabel(int newValue){
		_valueLabel.setText(Integer.toString(newValue));
	}
	
	public void setAll(int newValue){
		_valueLabel.setText(Integer.toString(newValue));
		_slider.setValue(newValue);
	}
	
	public int getValue(){
		return  _slider.getValue();
	}
	
	public void addChangeListener(ChangeListener listener){
		_listener.add(listener);
	}
	
	private void fireStateChanged(Object source){
		ChangeEvent e = new ChangeEvent(source);
		for(ChangeListener listener : _listener){
			listener.stateChanged(e);
		}
	}
	
	class SliderHandler implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			fireStateChanged(ColorSlider.this);
		}
		
	}
	
}
