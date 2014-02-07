package arduinoLight.channelprovider.generator.ambientlight;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import arduinoLight.util.Color;
import arduinoLight.util.DebugConsole;

public class ScreenshotGetter
{
	public Color[][] getScreenshot()
	{
		return getArrayFromImage(getBufferedImageScreenshot());
	}
	
	/**
	 * Used to get a Screenshot of the Main Screen of the user.
	 */
	private BufferedImage getBufferedImageScreenshot()
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
	
	private Color[][] getArrayFromImage(BufferedImage image)
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
	
	public Color getAverageColor(Color[][] image, Screenselection selection)
	{
		List<Color> selectedPixels = getSelectedPixels(image, selection);
		
		if (selectedPixels.size() == 0)
			return Color.BLACK;
		
		long rSum = 0;
		long gSum = 0;
		long bSum = 0;
		
		for (Color pixel : selectedPixels)
		{
			rSum += pixel.getR();
			gSum += pixel.getG();
			bSum += pixel.getB();
		}
		
		int r = (int) Math.round((double) rSum / selectedPixels.size());
		int g = (int) Math.round((double) gSum / selectedPixels.size());
		int b = (int) Math.round((double) bSum / selectedPixels.size());
		
		return new Color(255, r, g, b);
	}
	
	private List<Color> getSelectedPixels(Color[][] image, Screenselection selection)
	{
		if (selection == null)
			return new ArrayList<Color>();
		
		List<Color> selectedPixels = new ArrayList<>(200);
		double partWidth = (double)image[0].length / selection.getRows();
		double partHeight = (double)image.length / selection.getColumns();
		
		for (int y = 0; y < selection.getRows(); y++)
		{
			for (int x = 0; x < selection.getColumns(); x++)
			{
				if (selection.getCell(x, y))
				{
					int imgX = (int) Math.round(x * partWidth);
					int imgY = (int) Math.round(y * partHeight);
					List<Color> selectedPartPixels = getSelectedPixels(image, imgX, imgY, (int) Math.round(partWidth), (int) Math.round(partHeight));
					selectedPixels.addAll(selectedPartPixels);
				}
			}
		}
		
		return selectedPixels;
	}
	
	private List<Color> getSelectedPixels(Color[][] image, int x, int y, int xLength, int yLength)
	{
		List<Color> selectedPixels = new ArrayList<>(xLength * yLength);
		
		for (; y < yLength + y; y++)
		{
			for (; x < xLength + x; x++)
			{
				selectedPixels.add(image[x][y]);
			}
		}
		
		return selectedPixels;
	}
}
