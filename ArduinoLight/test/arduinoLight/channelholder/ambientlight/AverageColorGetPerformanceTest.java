package arduinoLight.channelholder.ambientlight;

import org.junit.Test;

import arduinoLight.channelholder.ambientlight.Areaselection;
import arduinoLight.channelholder.ambientlight.AverageColorGetter;
import arduinoLight.channelholder.ambientlight.ScreenshotHelper;
import arduinoLight.util.Color;

/**
 * This class may not be the optimal approach for a performance test, but it works.
 * It contains methods with large iterations over some operations to observe the execution time
 * for each operation.
 */
public class AverageColorGetPerformanceTest
{
	private final int _iterations = 100;

//	@Test
//	public void testAvgColor1x1a()
//	{
//		Areaselection selection = new Areaselection(1, 1);
//		selection.setAll(true);
//		
//		Color avg;
//		for (int i = 0; i < _iterations; i++)
//		{
//			avg = ScreenshotHelper.getScreenshot().getAverageColor(selection);
//			printColor(avg);
//		}
//	}

	@Test
	public void testAvgColor1x1b()
	{
		Areaselection selection = new Areaselection(1, 1);
		selection.setAll(true);
		
		Color avg;
		AverageColorGetter g;
		for (int i = 0; i < _iterations; i++)
		{
			g = new AverageColorGetter(ScreenshotHelper.getScreenshot());
			avg = g.getAverageColor(selection);
			printColor(avg);
		}
	}
	
	@Test
	public void testGetScreenshot()
	{
		for (int i = 0; i < _iterations; i++)
		{
			ScreenshotHelper.getScreenshot();
		}
	}
	
	private void printColor(Color color)
	{
		System.out.println(color);
	}
}
