package arduinoLight.util;

/**
 * This Interface is used by the AmblonePackage to get colors.
 * (Gets implemented by 'Color')
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
