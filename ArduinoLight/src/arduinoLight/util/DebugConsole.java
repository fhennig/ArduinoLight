package arduinoLight.util;

/**
 * Encapsulates the printing to the console for debugging-purposes.
 * @author Felix
 */
public class DebugConsole
{
	public static boolean isEnabled()
	{
		return true;
	}
	
	public static void print(String containingClass, String method, String message)
	{
		if (isEnabled())
			System.out.println("+++ " + containingClass + ":\t" + method + ":\t" + message);
	}
}
