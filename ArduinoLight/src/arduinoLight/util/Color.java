package arduinoLight.util;

import java.util.Collection;

/**
 * Immutable!
 * @author Felix
 */
public class Color implements RGBColor
{
	public static final Color BLACK = new Color(255, 0, 0, 0);
	public static final Color WHITE = new Color(255, 255, 255, 255);
	public static final Color RED = new Color(255, 255, 0, 0);
	public static final Color GREEN = new Color(255, 0, 255, 0);
	public static final Color BLUE = new Color(255, 0, 0, 255);
	
	private final int _argb; 
	
	public Color()
	{
		_argb = 0xff000000; //Initialize Black with 100% Alpha
	}
	
	public Color(int argb)
	{
		_argb = argb;
	}
	
	public Color(int a, int r, int g, int b)
	{
		a = getNormalizedInt(a);
		r = getNormalizedInt(r);
		g = getNormalizedInt(g);
		b = getNormalizedInt(b);
		
		int argb = 0;
		argb = (argb & 0x00ffffff) | (a << 24);
		argb = (argb & 0xff00ffff) | (r << 16);
		argb = (argb & 0xffff00ff) | (g << 8);
		argb = (argb & 0xffffff00) | b;

		_argb = argb;
	}
	
	
	//---------- Getters ---------------------------------------
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
	
	//---------- RGBColor-Interface ----------------------------
	public byte getCalculatedR()
	{

		return getAdjustedColor(getA(), getR());
	}
	
	public byte getCalculatedG()
	{
		return getAdjustedColor(getA(), getG());
	}
	
	public byte getCalculatedB()
	{
		return getAdjustedColor(getA(), getB());
	}
	
	
	
	//---------- static-helper-methods -------------------------
	/**
	 * Directly calculates the alpha-value into the color and returns the color as a byte.
	 */
	private static byte getAdjustedColor(int alpha, int color)
	{
		double ratio = alpha / 255.0;
		return (byte) Math.round(color * ratio);
	}
	
	/**
	 * Returns a value between 0 and 255. 
	 */
	private static int getNormalizedInt(int i)
	{
		if (i > 255)
			i = 255;
		else if (i < 0)
			i = 0;
		
		return i;
	}
	
	/**
	 * Returns the average color of the given colors.
	 */
	public static Color getAverageColor(Collection<Color> colors)
	{
		if (colors.size() == 0)
			return Color.BLACK;
		
		long aSum = 0;
		long rSum = 0;
		long gSum = 0;
		long bSum = 0;
		
		for (Color color : colors)
		{
			aSum += color.getA();
			rSum += color.getR();
			gSum += color.getG();
			bSum += color.getB();
		}
		
		int a = (int) Math.round((double) aSum / colors.size());
		int r = (int) Math.round((double) rSum / colors.size());
		int g = (int) Math.round((double) gSum / colors.size());
		int b = (int) Math.round((double) bSum / colors.size());
		
		return new Color(a, r, g, b);
	}

	//---------- overridden from object ------------------------
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
	
	/**
	 * returns the color in a format like this: #aarrggbb
	 */
	@Override
	public String toString() {
		String hex = Integer.toHexString(_argb);
		if (hex.length() < 8)
		{
			int amountMissing = 8 - hex.length();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < amountMissing; i++)
			{
				builder.append("0");
			}
			builder.append(hex);
			hex = builder.toString();
		}
		return "#" + hex;
	}
}
