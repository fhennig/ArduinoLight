package arduinoLight.channelholder.ambientlight;

import org.junit.Assert;
import org.junit.Test;

import arduinoLight.util.Color;

public class AvgColorBuilderTest
{
	@Test
	public void test1()
	{
		AvgColorBuilder b = new AvgColorBuilder();
		b.addColor(Color.WHITE.getARGB());
		b.addColor(Color.WHITE.getARGB());
		Assert.assertTrue(b.getAvgColor().equals(Color.WHITE));
	}
	
	@Test
	public void test2()
	{
		AvgColorBuilder b = new AvgColorBuilder();
		b.addColor(Color.RED.getARGB());
		b.addColor(Color.GREEN.getARGB());
		Assert.assertTrue(b.getAvgColor().equals(new Color(255, 127, 127, 0)));
	}
}
