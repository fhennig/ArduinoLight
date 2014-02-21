package arduinoLight.channelholder.ambientlight;

import arduinoLight.util.Color;

/**
 * This class encapsulates a two dimensional array which represents an image. <br>
 * thread-safety: This class is not thread-safe. 
 */
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
		int width = pixels[0].length;
		int height = pixels.length;
		
		if (width <= 0 || height <= 0)
			throw new IllegalArgumentException();

		
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
		validateCoordinates(x, y);
		return _pixels[y][x];
	}
	
	public int getWidth()
	{
		return _pixels[0].length;
	}
	
	public int getHeight()
	{
		return _pixels.length;
	}
	
	/** Throws IllegalArgumentException if the given coordinates are out of bounds. */
	public void validateCoordinates(int x, int y)
	{
		if (x < 0 || x >= getWidth())
			throw new IllegalArgumentException("x=" + x + " but must be between 0 and " + getWidth());
		if (y < 0 || y >= getHeight())
			throw new IllegalArgumentException("y=" + y + " but must be between 0 and " + getHeight());
	}
}
