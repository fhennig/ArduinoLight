package arduinoLight.util;

import static org.junit.Assert.*;

import org.junit.*;

/**
 * This class tests the Color class.
 * @author Felix
 */
public class ColorTest
{
	/**
	 * Tests if the parameterized constructor works.
	 */
	@Test
	public void testColorConstructor()
	{
		Color c = new Color(200, 20, 40, 75);
		
		assertEquals(200, c.getA());
		assertEquals(20,  c.getR());
		assertEquals(40,  c.getG());
		assertEquals(75,  c.getB());
	}
	
	/**
	 * Tests if the default constructed color is black with full alpha.
	 */
	@Test
	public void testColorDefaultContructor()
	{
		Color c = new Color();
		
		assertEquals(255, c.getA());
		assertEquals(0,   c.getR());
		assertEquals(0,   c.getG());
		assertEquals(0,   c.getB());
	}
	
	/**
	 * Tests the normal set-methods.
	 */
	@Test
	public void testColorSettersNormal()
	{
		Color c = new Color(200, 199, 198, 197);
		
		assertEquals(200, c.getA());
		assertEquals(199, c.getR());
		assertEquals(198, c.getG());
		assertEquals(197, c.getB());
	}

	/**
	 * Tests if too high values are handled correctly.
	 */
	@Test
	public void testColorSettersArgumentTooHigh()
	{
		Color c = new Color(300, 300, 300, 300);
		
		assertEquals(255, c.getA());
		assertEquals(255, c.getR());
		assertEquals(255, c.getG());
		assertEquals(255, c.getB());
	}
	
	/**
	 * Tests if too low values are handled correctly.
	 */
	@Test
	public void testColorSettersArgumentTooLow()
	{
		Color c = new Color(-1, -1, -1, -1);
		
		assertEquals(0, c.getA());
		assertEquals(0, c.getR());
		assertEquals(0, c.getG());
		assertEquals(0, c.getB());
	}
	
	/**
	 * Tests if setting the ARGB directly leads to correct A, R, G, B values.
	 */
	@Test
	public void testColorSetGetARGB()
	{
		Color c = new Color(0xabcd1234);
		
		assertEquals(0xab, c.getA());
		assertEquals(0xcd, c.getR());
		assertEquals(0x12, c.getG());
		assertEquals(0x34, c.getB());
	}

	/**
	 * Tests if equals() returns true if the colors are equal.
	 * Tests if the hashcodes are equal if the colors are equal.
	 */
	@Test
	public void testColorEqualsAndHashCodePositive()
	{
		Color c1 = new Color(1, 2, 3, 4);
		Color c2 = new Color(1, 2, 3, 4);
		
		assertTrue(c1.equals(c2));
		assertTrue(c2.equals(c1));
		
		assertEquals(c1.hashCode(), c2.hashCode());
	}
	
	/**
	 * Tests if equals() returns false if the colors are not equal.
	 * Tests if the hashcodes are not equal if the colors are not equal.
	 */
	@Test
	public void testColorEqualsAndHashCodeNegative()
	{
		Color c1 = new Color(1, 2, 3, 4);
		Color c2 = new Color(1, 2, 3, 5);
		
		assertFalse(c1.equals(c2));
		assertFalse(c2.equals(c1));
		
		assertNotEquals(c1.hashCode(), c2.hashCode());
	}
	
	/**
	 * Tests if Color values are calculated correctly from the alpha value.
	 */
	@Test
	public void testColorRGBColorInterface()
	{
		Color c = new Color(100, 300, 12, 34);
		
		byte calculatedR = (byte) Math.round((100 / 255.0) * 255);
		byte calculatedG = (byte) Math.round((100 / 255.0) * 12);
		byte calculatedB = (byte) Math.round((100 / 255.0) * 34);
		
		assertEquals(calculatedR, c.getCalculatedR());
		assertEquals(calculatedG, c.getCalculatedG());
		assertEquals(calculatedB, c.getCalculatedB());
	}
}
