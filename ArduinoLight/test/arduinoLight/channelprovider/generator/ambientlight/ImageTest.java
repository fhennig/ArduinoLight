package arduinoLight.channelprovider.generator.ambientlight;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import arduinoLight.util.Color;

public class ImageTest
{
	@Test
	public void testConstructorAndGet()
	{
		Image i = new Image(3, 3, Color.RED);
		
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				Assert.assertEquals(Color.RED, i.getPixel(x, y));
			}
		}
	}
	
	@Test
	public void testArrayConstructor()
	{
		Color[][] imageArray = new Color[3][3];
		imageArray[0][0] = Color.RED;
		imageArray[1][0] = Color.GREEN;
		imageArray[2][0] = Color.BLUE;
		
		Image i = new Image(imageArray);
		
		Assert.assertEquals(Color.RED, i.getPixel(0, 0));
		Assert.assertEquals(Color.GREEN, i.getPixel(1, 0));
		Assert.assertEquals(Color.BLUE, i.getPixel(2, 0));
		Assert.assertEquals("null should be translated to be black", Color.BLACK, i.getPixel(1, 1));
	}
	
	@Test
	public void testGetSet()
	{
		Image i = new Image(3, 3);
		i.setPixel(2, 2, Color.GREEN);
		
		Assert.assertEquals(Color.GREEN, i.getPixel(2, 2));
	}
	
	@Test
	public void testGetSelectedPixelsWithCoordinates()
	{
		Image i = new Image(3, 3, Color.BLACK);
		i.setPixel(0, 0, Color.RED);
		List<Color> selectedPixels = i.getSelectedPixels(0, 0, 1, 1);
		
		Assert.assertEquals(1, selectedPixels.size());
		Assert.assertEquals(Color.RED, selectedPixels.get(0));
	}
	
//	private Image get3x3Image()
//	{
//		//Do not change the Colors! The tests rely on these.
//		Image i = new Image(3, 3, Color.BLACK);
//		i.setPixel(0, 0, Color.RED);
//		i.setPixel(1, 0, Color.GREEN);
//		i.setPixel(2, 0, Color.BLUE);
//		return i;
//	}
}
