package arduinoLight.channelholder.ambientlight;

import java.awt.AWTPermission;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import arduinoLight.util.Color;
import arduinoLight.util.DebugConsole;

/**
 * This class encapsulates the functionality to take a screenshot. <br> 
 * thread-safety: This class has not state, is immutable and therefore thread-safe.
 */
public class ScreenshotHelper
{
	/**
	 * Checks if it is permitted to take a screenshot.
	 * @throws SecurityException  if it is not permitted to take a screenshot.
	 */
	public static void checkScreenshotPermission() throws SecurityException
	{
		SecurityManager security = System.getSecurityManager();
		if (security != null)
			security.checkPermission(new AWTPermission("readDisplayPixels"));
	}
	
	
	
	/**
	 * Returns a screenshot of the Users main screen
	 */
	public static Image getScreenshot()
	{
		BufferedImage bufferedImage = getBufferedImageScreenshot();
		Color[][] imageArray = getArrayFromImage(bufferedImage); 
		Image image = new Image(imageArray);
		return image;
	}
	
	
	
	/**
	 * Used to get a Screenshot of the primary screen of the user.
	 */
	public static BufferedImage getBufferedImageScreenshot()
	{
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage capture = null;
		try {
			capture = new Robot().createScreenCapture(screenRect);
		} catch (Exception e) {
			//TODO handle AWTEXception and possible SecurityException 
			DebugConsole.print("AmbientlightLogic", "getScreenshot", "Exception:\n\n" + e.toString());
			throw new IllegalStateException(e); //throw unchecked Ex.
		}
		return capture;
	}
	

	
	private static Color[][] getArrayFromImage(BufferedImage image)
	{
		DataBufferInt dbb = (DataBufferInt) image.getRaster().getDataBuffer();
		final int[] pixels = dbb.getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		int counter = 0;
		
		Color[][] result = new Color[height][width];
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				result[i][j] = new Color(pixels[counter]);
				counter++;
			}
		}
		
		return result;
	}
}
