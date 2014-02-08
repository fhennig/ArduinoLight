package arduinoLight.channelprovider.generator.ambientlight;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.util.Color;
import arduinoLight.util.DebugConsole;

public class Image
{
	private final Color[][] _pixels;
	
	/** Creates a new Image with a copy of the given Colors */
	public Image(Color[][] pixels)
	{
		if (pixels.length <= 0 || pixels[0].length <= 0)
			throw new IllegalArgumentException();

		int width = pixels[0].length;
		int height = pixels.length;
		
		_pixels = new Color[height][width];
		
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				Color pixel = pixels[y][x];
				if (pixel == null)
					pixel = Color.BLACK;
				setPixel(x, y, pixel);
			}
		}
	}
	
	/** Creates a new black Image with the specified dimensions. */
	public Image(int width, int height)
	{
		this(width, height, Color.BLACK);
	}
	
	/** Creates a new Image with the specified dimensions and color. */
	public Image(int width, int height, Color color)
	{
		if (width <= 0 || height <= 0)
			throw new IllegalArgumentException();
		if (color == null)
			throw new IllegalArgumentException();
		
		DebugConsole.print("Image", "contructor", "creating image: " + width + "x" + height);
		
		_pixels = new Color[height][width];
		
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				setPixel(x, y, color);
			}
		}
	}
	
	public void setPixel(int x, int y, Color color)
	{
		validateCoordinates(x, y);
		_pixels[y][x] = color;
	}
	
	public Color getPixel(int x, int y)
	{
		return _pixels[y][x];
	}
	
	public Color getAverageColor(Areaselection selection)
	{
		List<Color> selectedPixels = getSelectedPixels(selection);
		
		return Color.getAverageColor(selectedPixels);
	}
	
	public List<Color> getSelectedPixels(Areaselection selection)
	{
		if (selection == null)
			return new ArrayList<Color>();
		
		List<Color> selectedPixels = new ArrayList<>(200);
		double partWidth = (double)_pixels[0].length / selection.getRows();
		double partHeight = (double)_pixels.length / selection.getColumns();
		
		for (int y = 0; y < selection.getRows(); y++)
		{
			for (int x = 0; x < selection.getColumns(); x++)
			{
				if (selection.getCell(x, y))
				{
					int imgX = (int) Math.round(x * partWidth);
					int imgY = (int) Math.round(y * partHeight);
					List<Color> selectedPartPixels = getSelectedPixels(imgX, imgY, (int) Math.round(partWidth), (int) Math.round(partHeight));
					selectedPixels.addAll(selectedPartPixels);
				}
			}
		}
		
		return selectedPixels;
	}
	
	public List<Color> getSelectedPixels(int x, int y, int xLength, int yLength)
	{
		List<Color> selectedPixels = new ArrayList<>(xLength * yLength);
		
		for (int yi = y; yi < yLength + y; yi++)
		{
			for (int xi = x; xi < xLength + x; xi++)
			{
				selectedPixels.add(_pixels[yi][xi]);
			}
		}
		
		return selectedPixels;
	}
	
	public int getWidth()
	{
		return _pixels[0].length;
	}
	
	public int getHeight()
	{
		return _pixels.length;
	}
	
	/** Throws IllegalArgumentException if the given coordinates are out of bounds */
	private void validateCoordinates(int x, int y)
	{
		if (x < 0 || x >= getWidth())
			throw new IllegalArgumentException("x=" + x + " but must be between 0 and " + getWidth());
		if (y < 0 || y >= getHeight())
			throw new IllegalArgumentException("y=" + y + " but must be between 0 and " + getHeight());
	}
}
