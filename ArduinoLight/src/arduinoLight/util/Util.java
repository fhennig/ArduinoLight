package arduinoLight.util;

/**
 * This class contains various static methods which provide some generally useful functionality. <br>
 * thread-safety: This class is stateless, therefore thread-safe.
 */
public class Util
{
	/**
	 * If value is greater than high, high is returned.
	 * If value is lower than low, low is returned.
	 * If value is in bounds, value is returned.
	 */
	public static int getInBounds(int value, int low, int high)
	{
		if (low > high)
			throw new IllegalArgumentException("lower bound was greater than upper bound.");
		int inBounds = Math.min(Math.max(low, value), high);
		return inBounds;
	}
	

	
	/**
	 * @param frequency  amount per second (Hz)
	 * @return  the period in nanoseconds
	 */
	public static long getPeriod(double frequency)
	{
		return Math.round(1000000000.0 / frequency);
	}
	
	
		
	/**
	 * @param string  the String that should be padded
	 * @param pad  the char that should be used for padding
	 * @param desiredLength  the desired Length of the String
	 * @return  the String followed by [pad] to match the desired Length.
	 * If the String is already the desiredLength or even longer, the String is returned.
	 */
	public static String getRightPaddedString(String string, char pad, int desiredLength)
	{
		if (string.length() >= desiredLength)
			return string;
		
		int spacesNeeded = desiredLength - string.length();
		StringBuilder spaces = new StringBuilder(spacesNeeded);
		for (int i = 0; i < spacesNeeded; i++)
		{
			spaces.append(pad);
		}
		return string.concat(spaces.toString());
	}
}
