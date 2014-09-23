package arduinoLight.channelholder.ambientlight;

import arduinoLight.util.Color;

public class AvgColorBuilder
{
	private int _r, _g, _b;
	private int _counter;
	
	
	
	public void addColor(int color)
	{
		_r += Color.getR(color);
		_g += Color.getG(color);
		_b += Color.getB(color);
		_counter++;
	}
	
	public Color getAvgColor()
	{
		return new Color(255,
				_r / _counter,
				_g / _counter,
				_b / _counter);
	}
}
