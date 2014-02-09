package arduinoLight.channel;

import arduinoLight.interfaces.Nameable;
import arduinoLight.interfaces.propertyListeners.ColorListener;
import arduinoLight.interfaces.propertyListeners.NameListener;
import arduinoLight.util.Color;


public interface Channel extends Nameable
{
	public int getId();
	
	public Color getColor();
	public void setColor(Color color);
	
//	public String getName();
//	public void setName(String name);
	
	public void addColorListener(ColorListener listener);
	public void removeColorListener(ColorListener listener);
	
	public void addNameListener(NameListener listener);
	public void removeNameListener(NameListener listener);
}
