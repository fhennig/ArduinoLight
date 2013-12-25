package arduinoLight.util;

/**
 * Immutable!
 * @author Felix
 */
public class Color implements RGBColor
{
	private int _argb = 0xff000000; //Initialize Black with 100% Alpha
	
	public Color()
	{
		
	}
	
	public Color(int argb)
	{
		_argb = argb;
	}
	
	public Color(int a, int r, int g, int b)
	{
		setA(a);
		setR(r);
		setG(g);
		setB(b);
	}
	
	
	
	
	
	private void setA(int alpha)
	{
		alpha = getNormalizedInt(alpha);
		
		_argb = (_argb & 0x00ffffff) | (alpha << 24);
	}
	
	private void setR(int red)
	{
		red = getNormalizedInt(red);
		
		_argb = (_argb & 0xff00ffff) | (red << 16);
	}
	
	private void setG(int green)
	{
		green = getNormalizedInt(green);
		
		_argb = (_argb & 0xffff00ff) | (green << 8);
	}
	
	private void setB(int blue)
	{
		blue = getNormalizedInt(blue);
		
		_argb = (_argb & 0xffffff00) | blue;
	}
	
	public int getARGB()
	{
		return _argb;
	}
	
	public int getA()
	{
		return _argb >>> 24;
}
	
	public int getR()
	{
		int mask = 0x00ff0000;
		int red = _argb & mask;
		return red >>> 16;
	}

	public int getG()
	{
		int mask = 0x0000ff00;
		int green = _argb & mask;
		return green >>> 8;
	}
	
	public int getB()
	{
		int mask = 0x000000ff;
		int blue = _argb & mask;
		return blue;
	}
	
	public byte getCalculatedR()
	{
		int red = getR();
		int alpha = getA();
		double ratio = alpha / 255.0;
		double result = Math.round(red * ratio);
		return (byte) result;
	}
	
	public byte getCalculatedG()
	{
		int green = getG();
		int alpha = getA();
		double ratio = alpha / 255.0;
		double result = Math.round(green * ratio);
		return (byte) result;
	}
	
	public byte getCalculatedB()
	{
		int blue = getB();
		int alpha = getA();
		double ratio = alpha / 255.0;
		double result = Math.round(blue * ratio);
		return (byte) result;
	}
	
	private int getNormalizedInt(int i)
	{
		if (i > 255)
			i = 255;
		else if (i < 0)
			i = 0;
		
		return i;
	}

	@Override
	public int hashCode() {
		final int prime = 47;
		int result = 1;
		result = prime * result + _argb;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Color)) {
			return false;
		}
		Color other = (Color) obj;
		if (_argb != other._argb) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "#" + Integer.toHexString(_argb);
	}
}
