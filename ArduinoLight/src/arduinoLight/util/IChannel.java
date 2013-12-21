package arduinoLight.util;


public interface IChannel
{
	public Color getColor();
	public void setColor(Color color);
	
	public String getName();
	public void setName(String name);
}
