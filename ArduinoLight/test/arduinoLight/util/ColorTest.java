package arduinoLight.util;

import static org.junit.Assert.*;

import org.junit.*;

public class ColorTest
{

	@Test
	public void colorConstructorTest()
	{
		Color c = new Color(200, 20, 40, 75);
		
		assertEquals(200, c.getA());
		assertEquals(20,  c.getR());
		assertEquals(40,  c.getG());
		assertEquals(75,  c.getB());
	}
	
	@Test
	public void colorDefaultContructorTest()
	{
		Color c = new Color();
		
		assertEquals(255, c.getA());
		assertEquals(0,   c.getR());
		assertEquals(0,   c.getG());
		assertEquals(0,   c.getB());
	}
	
	@Test
	public void colorSettersNormalTest()
	{
		Color c = new Color();
		
		c.setA(200);
		c.setR(199);
		c.setG(198);
		c.setB(197);
		
		assertEquals(200, c.getA());
		assertEquals(199, c.getR());
		assertEquals(198, c.getG());
		assertEquals(197, c.getB());
	}
	
	@Test
	public void colorSettersArgumentTooHighTest()
	{
		Color c = new Color();
		
		c.setA(300);
		c.setR(300);
		c.setG(300);
		c.setB(300);
		
		assertEquals(255, c.getA());
		assertEquals(255, c.getR());
		assertEquals(255, c.getG());
		assertEquals(255, c.getB());
	}
	
	@Test
	public void colorSettersArgumentTooLowTest()
	{
		Color c = new Color();
		
		c.setA(-1);
		c.setR(-1);
		c.setG(-1);
		c.setB(-1);
		
		assertEquals(0, c.getA());
		assertEquals(0, c.getR());
		assertEquals(0, c.getG());
		assertEquals(0, c.getB());
	}
	
	
	@Test
	public void colorSetGetARGBTest()
	{
		Color c = new Color();
		
		c.setARGB(0xabcd1234);
		
		assertEquals(0xab, c.getA());
		assertEquals(0xcd, c.getR());
		assertEquals(0x12, c.getG());
		assertEquals(0x34, c.getB());
	}
	
	@Test
	public void colorEqualsAndHashCodePositiveTest()
	{
		Color c1 = new Color(1, 2, 3, 4);
		Color c2 = new Color(1, 2, 3, 4);
		
		assertTrue(c1.equals(c2));
		assertTrue(c2.equals(c1));
		
		assertEquals(c1.hashCode(), c2.hashCode());
	}
	
	@Test
	public void colorEqualsAndHashCodeNegativeTest()
	{
		Color c1 = new Color(1, 2, 3, 4);
		Color c2 = new Color(1, 2, 3, 5);
		
		assertFalse(c1.equals(c2));
		assertFalse(c2.equals(c1));
		
		assertNotEquals(c1.hashCode(), c2.hashCode());
	}
	
	@Test
	/**
	 * Tests if Color values are calculated correctly from the alpha value.
	 */
	public void colorRGBColorInterfaceTest()
	{
		Color c = new Color(255, 300, 12, 34);
		
		assertEquals((byte) 255, c.getCalculatedR());
		assertEquals((byte) 12, c.getCalculatedG());
		assertEquals((byte) 34, c.getCalculatedB());
		
		c.setA(100);
		
		byte calculatedR = (byte) Math.round((100 / 255.0) * 255);
		byte calculatedG = (byte) Math.round((100 / 255.0) * 12);
		byte calculatedB = (byte) Math.round((100 / 255.0) * 34);
		
		assertEquals(calculatedR, c.getCalculatedR());
		assertEquals(calculatedG, c.getCalculatedG());
		assertEquals(calculatedB, c.getCalculatedB());
	}
}
