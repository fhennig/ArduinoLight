package arduinoLight.util;


public class Color implements RGBColor
{
	private int _argb = 0xff000000; //Initialize Black with 100% Alpha
	
	public void setARGB(int argb)
	{
		_argb = argb;
	}
	
	public void setA(int alpha)
	{
		alpha = getNormalizedInt(alpha);
		
		_argb = (_argb & 0x00ffffff) | (alpha << 24);
	}
	
	public void setR(int red)
	{
		red = getNormalizedInt(red);
		
		_argb = (_argb & 0xff00ffff) | (red << 16);
	}
	
	public void setG(int green)
	{
		green = getNormalizedInt(green);
		
		_argb = (_argb & 0xffff00ff) | (green << 8);
	}
	
	public void setB(int blue)
	{
		blue = getNormalizedInt(blue);
		
		_argb = (_argb & 0xffffff00) | blue;
	}
	
	public int getARGB()
	{
		return _argb;
	}
	
	public byte getA()
	{
		return new Integer(_argb >>> 24).byteValue();
}
	
	public byte getR()
	{
		return new Integer(_argb >>> 16).byteValue();
	}

	public byte getG()
	{
		return new Integer(_argb >>> 8).byteValue();
	}
	
	public byte getB()
	{
		return new Integer(_argb).byteValue();
	}
	
	public byte getCalculatedR()
	{
		return (byte) (getR() * (getA() / 255));
	}
	
	public byte getCalculatedG()
	{
		return (byte) (getG() * (getA() / 255));
	}
	
	public byte getCalculatedB()
	{
		return (byte) (getB() * (getA() / 255));
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
}
