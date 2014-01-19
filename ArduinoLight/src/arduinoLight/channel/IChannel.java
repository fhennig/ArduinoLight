package arduinoLight.channel;

import arduinoLight.util.Color;


public interface IChannel
{
	public Color getColor();
	public void setColor(Color color);
	
	public int getId();
}
