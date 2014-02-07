package arduinoLight.channelprovider.generator.ambientlight;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import arduinoLight.util.Color;
import arduinoLight.util.DebugConsole;

public class ScreenshotGetter
{
	/**
	 * Used to get a Screenshot of the Main Screen of the user.
	 * @return
	 */
	private static BufferedImage getBufferedImageScreenshot()
	{
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage capture = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		try {
			capture = new Robot().createScreenCapture(screenRect);
		} catch (AWTException e) {
			//TODO AWTEXception handlen
			DebugConsole.print("AmbientlightLogic", "getScreenshot", "Exception:\n\n" + e.toString());
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
	
	public static Color[][] getScreenshot()
	{
		return getArrayFromImage(getBufferedImageScreenshot());
	}
}
