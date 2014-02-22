package arduinoLight.util;

/**
 * Encapsulates the printing to the console for debugging-purposes.
 */
public class DebugConsole
{
	private static int classLength = 0;
	private static int methodLength = 0;
	
	public static boolean isEnabled()
	{
		return true;
	}
	
	public static void print(String containingClass, String method, String message)
	{
		if (!isEnabled())
			return;
		containingClass = padClass(containingClass);
		method = padMethod(method);
		System.out.println("+++ " + containingClass + ":" + method + ":" + message);
	}
	
	
	private static String padClass(String containingClass)
	{
		if (containingClass.length() > classLength)
			classLength = containingClass.length();
		
		return Util.getRightPaddedString(containingClass, classLength);
	}
	
	private static String padMethod(String method)
	{
		if (method.length() > methodLength)
			methodLength = method.length();
		
		return Util.getRightPaddedString(method, methodLength);
	}
}
