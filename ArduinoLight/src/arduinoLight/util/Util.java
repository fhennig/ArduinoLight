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
}
