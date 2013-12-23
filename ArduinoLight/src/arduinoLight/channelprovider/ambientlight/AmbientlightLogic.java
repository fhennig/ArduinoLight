package arduinoLight.channelprovider.ambientlight;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import arduinoLight.util.DebugConsole;

public class AmbientlightLogic// extends Colorprovider
{
	/**
	 * Used to get a Screenshot of the Main Screen of the user.
	 * @return
	 */
	private BufferedImage getScreenshot()
	{
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage capture = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		try {
			capture = new Robot().createScreenCapture(screenRect);
		} catch (AWTException e) {
			//TODO AWTEXception handlen
			DebugConsole.print("AmbientlightLogic", "getScreenshot", e.toString());
		}
		return capture;
	}
	
	private int[][] getArrayFromImage(BufferedImage image)
	{
		DataBufferInt dbb = (DataBufferInt) image.getRaster().getDataBuffer();
		final int[] pixels = dbb.getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		int counter = 0;
		
		int[][] result = new int[height][width];
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				result[i][j] = pixels[counter];
				counter++;
			}
		}
		
		return result;
	}
}
