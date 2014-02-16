package arduinoLight.channelprovider.generator.ambientlight;

import org.junit.Assert;
import org.junit.Test;

public class AreaselectionTest
{
	@Test
	public void testBasicWithoutSet()
	{
		Areaselection as = new Areaselection(2, 2);
		boolean[][] expected = new boolean[2][2];
		
		Assert.assertArrayEquals(expected, as.toBooleanArray());
	}
	
	@Test
	public void testSetGet()
	{
		Areaselection as = new Areaselection(2, 2);
		as.setCell(1, 1, true);
		boolean[][] expected = new boolean[2][2];
		expected[1][1] = true;
		
		Assert.assertArrayEquals(expected, as.toBooleanArray());
		Assert.assertEquals(true, as.getCell(1, 1));
		Assert.assertEquals(false, as.getCell(0, 0));
	}
	
	@Test
	public void testChangeSize()
	{
		Areaselection as = new Areaselection(2, 2);
		as.setCell(1, 1, true);
		as.changeSize(3, 3);
		boolean[][] expected = new boolean[3][3];
		expected[1][1] = true;
		
		Assert.assertArrayEquals(expected, as.toBooleanArray());
		Assert.assertEquals(true, as.getCell(1, 1));
		Assert.assertEquals(false, as.getCell(0, 0));
		Assert.assertEquals(false, as.getCell(2, 2));
	}
	
	@Test
	public void testClear()
	{
		Areaselection as = new Areaselection(2, 2);
		boolean[][] expected = new boolean[2][2];
		for (int y = 0; y < 2; y++)
		{
			for (int x = 0; x < 2; x++)
			{
				as.setCell(x, y, true);
				expected[x][y] = true;
			}
		}
		
		Assert.assertArrayEquals(expected, as.toBooleanArray());
		
		as.setAll(false);
		for (int y = 0; y < 2; y++)
		{
			for (int x = 0; x < 2; x++)
			{
				expected[x][y] = false;
			}
		}
		
		Assert.assertArrayEquals(expected, as.toBooleanArray());
	}
}
