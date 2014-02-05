package arduinoLight.channel;

import arduinoLight.util.Color;


public interface IChannel extends ReadonlyChannel
{
	public void setColor(Color color);
	public void setName(String name);
}
