package arduinoLight.util;

/**
 * This interface is used by the Amblone-Part to get colors.
 * (Gets implemented by 'Color')
 * @author felix
 */
public interface RGBColor
{
	public byte getCalculatedR();
	public byte getCalculatedG();
	public byte getCalculatedB();
}
