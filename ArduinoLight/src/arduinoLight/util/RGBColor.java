package arduinoLight.util;

/**
 * This interface is used by the Amblone-Part to get colors.
 * (Gets implemented by 'Color')
 * @author felix
 */
public interface RGBColor
{
	/**
	 * Returns the red value with the alpha value already calculated in.
	 */
	public byte getCalculatedR();
	
	
	/**
	 * Returns the green value with the alpha value already calculated in.
	 */
	public byte getCalculatedG();
	
	
	/**
	 * Returns the blue value with the alpha value already calculated in.
	 */
	public byte getCalculatedB();
}
