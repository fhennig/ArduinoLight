package arduinoLight.util;

public class DebugConsole
{
	public boolean isEnabled()
	{
		return true;
	}
	
	public static void print(String containingClass, String method, String message)
	{
		System.out.println("+++ " + containingClass + ": " + method + ": " + message);
	}
}
