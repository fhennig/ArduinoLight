package arduinoLight.arduino.amblone;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import arduinoLight.arduino.AmblonePackageFactory;
import arduinoLight.util.Color;
import arduinoLight.util.RGBColor;

/**
 * This class tests if various color-combinations are correctly transformed into AmblonePackages,
 * as the arduino-code would expect them.
 */
public class AmblonePackageTest
{
//	/**
//	 * Tests basic functionality.
//	 */
//	@Test
//	public void testSize1NoReservedValues()
//	{
//		List<RGBColor> colors = new ArrayList<>();
//	    colors.add(new Color(-1));
//		AmblonePackageFactory p = new AmblonePackageFactory(colors);
//		byte[] bytes = p.toByteArray();
//		
//		Assert.assertEquals(5, bytes.length);
//		Assert.assertEquals((byte) 241, bytes[0]); //STARTFLAG1
//		Assert.assertEquals((byte) 255, bytes[1]); 
//		Assert.assertEquals((byte) 255, bytes[2]);
//		Assert.assertEquals((byte) 255, bytes[3]);
//		Assert.assertEquals((byte)  51, bytes[4]); //ENDFLAG
//	}
//	
//	/**
//	 * Tests if byte values that are used for flags are escaped correctly
//	 * specifically: STARTFLAG1, ENDFLAG, ESCFLAG
//	 */
//	@Test
//	public void testSize1ReservedValues()
//	{
//		List<RGBColor> colors = new ArrayList<>();
//		colors.add(new Color(255, 241, 51, 0x99));
//		AmblonePackageFactory p = new AmblonePackageFactory(colors);
//		byte[] bytes = p.toByteArray();
//		
//		Assert.assertEquals(8, bytes.length);
//		Assert.assertEquals((byte) 241, bytes[0]);
//		Assert.assertEquals((byte) 0x99, bytes[1]);
//	}
//	
//	@Test
//	public void testSize4()
//	{
//		List<RGBColor> colors = new ArrayList<>();
//		for (int i = 0; i < 4; i++)
//		{
//			colors.add(new Color(-1));
//		}
//		AmblonePackageFactory p = new AmblonePackageFactory(colors);
//		byte[] bytes = p.toByteArray();
//		
//		Assert.assertEquals("2 + 3*4 expected!", 14, bytes.length);
//		Assert.assertEquals("STARTFLAG4 expected!", (byte) 244, bytes[0]);
//	}
//	
//	@Test
//	public void testSize5()
//	{
//		List<RGBColor> colors = new ArrayList<>();
//		for (int i = 0; i < 5; i++)
//		{
//			colors.add(new Color(-1));
//		}
//		AmblonePackageFactory p = new AmblonePackageFactory(colors);
//		byte[] bytes = p.toByteArray();
//		
//		Assert.assertEquals("2 + 3*4 expected!", 14, bytes.length);
//		Assert.assertEquals("STARTFLAG4 expected!", (byte) 244, bytes[0]);
//	}
}
