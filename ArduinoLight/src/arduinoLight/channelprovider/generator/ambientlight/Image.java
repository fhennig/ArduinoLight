package arduinoLight.channelprovider.generator.ambientlight;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.util.Color;

public class Image
{
	private final Color[][] _pixels;
	
	/**
	 * Creates a new Image with a copy of the given Colors
	 * pixels.length will be the height of the Image.
	 * pixels[0].length will be the width of the Image.
	 */
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
		double partWidth = (double)getWidth() / selection.getColumns();
		double partHeight = (double)getHeight() / selection.getRows();
		//DebugConsole.print("Image", "getSelectedPixels", "width: " + getWidth() + "   sel-cols: " + selection.getColumns() + "   partW: " + partWidth);
		//DebugConsole.print("Image", "getSelectedPixels", "height: " + getHeight() + "   sel-rows: " + selection.getRows() + "   partH: " + partHeight);
		System.out.println();
		for (int y = 0; y < selection.getRows(); y++)
		{
			for (int x = 0; x < selection.getColumns(); x++)
			{
				if (selection.getCell(x, y))
				{
					int imgX1 = (int) Math.round(x * partWidth);
					int imgY1 = (int) Math.round(y * partHeight);
					int imgX2 = (int) Math.round((x + 1) * partWidth) - 1;
					int imgY2 = (int) Math.round((y + 1) * partHeight - 1);
					
					List<Color> selectedPartPixels = getSelectedPixels(imgX1, imgY1, imgX2, imgY2);
					selectedPixels.addAll(selectedPartPixels);
				}
			}
		}
		
		return selectedPixels;
	}
	
	/**
	 * Returns a List of Colors that contains the Colors that are inside
	 * the rectangle that is specified by the given points (x1|y1), (x2|y2).
	 * The given points are included in the rectangle.
	 */
	public List<Color> getSelectedPixels(int x1, int y1, int x2, int y2)
	{
		validateCoordinates(x1, y1);
		validateCoordinates(x2, y2);
		if (x1 > x2)
		{
			int temp = x2;
			x2 = x1;
			x1 = temp;
		}
		if (y1 > y2)
		{
			int temp = y2;
			y2 = y1;
			y1 = temp;
		}
		
		int amountOfElements = (x2 - x1) * (y2 - y1);
		List<Color> selectedPixels = new ArrayList<>(amountOfElements);
		
		for (int y = y1; y <= y2; y++)
		{
			for (int x = x1; x <= x2; x++)
			{
				selectedPixels.add(_pixels[y][x]);
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
