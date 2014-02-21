package arduinoLight.channelholder.ambientlight;

import org.junit.Assert;
import org.junit.Test;

import arduinoLight.channelholder.ambientlight.Areaselection;

public class AreaselectionTest
{
	@Test
	public void testBasicWithoutSet()
	{
		Areaselection as = new Areaselection(2, 2);
		boolean[][] expected = new boolean[2][2];
		
		Assert.assertArrayEquals(expected, getArrayFromAreaselection(as));
	}
	
	@Test
	public void testXandYareDefinedCorrectly()
	{
		Areaselection as = new Areaselection(1, 2);
		as.setCell(1, 0, true);
		
		Assert.assertEquals(true, as.getCell(1, 0));
		Assert.assertEquals(false, as.getCell(0, 0));
	}
	
	@Test
	public void testSetGet()
	{
		Areaselection as = new Areaselection(2, 2);
		as.setCell(1, 1, true);
		boolean[][] expected = new boolean[2][2];
		expected[1][1] = true;
		
		Assert.assertArrayEquals(expected, getArrayFromAreaselection(as));
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
		
		Assert.assertArrayEquals(expected, getArrayFromAreaselection(as));
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
		
		Assert.assertArrayEquals(expected, getArrayFromAreaselection(as));
		
		as.setAll(false);
		for (int y = 0; y < 2; y++)
		{
			for (int x = 0; x < 2; x++)
			{
				expected[x][y] = false;
			}
		}
		
		Assert.assertArrayEquals(expected, getArrayFromAreaselection(as));
	}
	
	private boolean[][] getArrayFromAreaselection(Areaselection selection)
	{
		boolean[][] array = new boolean[selection.getRows()][selection.getColumns()];
		
		for (int r = 0; r < selection.getRows(); r++)
		{
			for (int c = 0; c < selection.getColumns(); c++)
			{
				array[r][c] = selection.getCell(c, r);
			}
		}
		
		return array;
	}
}
