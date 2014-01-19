package arduinoLight.channel;

import arduinoLight.interfaces.propertyListeners.ColorListener;
import arduinoLight.interfaces.propertyListeners.NameListener;
import arduinoLight.util.Color;

public interface ReadonlyChannel
{
	public Color getColor();
	public String getName();
	
	public int getId();

	public void addColorListener(ColorListener listener);
	public void removeColorListener(ColorListener listener);
	
	public void addNameListener(NameListener listener);
	public void removeNameListener(NameListener listener);
}
