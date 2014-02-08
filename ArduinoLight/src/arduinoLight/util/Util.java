package arduinoLight.util;

public class Util
{
	/**
	 * @param frequency  amount per second (Hz)
	 * @return  the period in nanoseconds
	 */
	public static long getPeriod(double frequency)
	{
		return Math.round(1000000000.0 / frequency);
	}
	
	/**
	 * If frequency > maxFrequency, the Period for maxFrequency is returned
	 * @param frequency  amount per second (Hz)
	 * @param maxFrequency  the max supported frequency
	 * @return  the period in nanoseconds
	 */
	public static long getPeriod(double frequency, double maxFrequency)
	{
		if (frequency > maxFrequency)
			frequency = maxFrequency;
		
		return Math.round(1000000000.0 / frequency);
	}
	
	public static String getRightPaddedString(String string, int desiredLength)
	{
		if (string.length() >= desiredLength)
			return string;
		
		int spacesNeeded = desiredLength - string.length();
		StringBuilder spaces = new StringBuilder(spacesNeeded);
		for (int i = 0; i < spacesNeeded; i++)
		{
			spaces.append(' ');
		}
		return string.concat(spaces.toString());
	}
}
