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
	public void testColorConstructorSingleArguments()
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
	 * Tests if too high values are handled correctly.
	 */
	@Test
	public void testConstructorArgumentsTooHigh()
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
	public void testConstructorArgumentsTooLow()
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
	public void testConstructorARGB()
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
	
	/**
	 * Tests if the toString-method returns the expected value.
	 */
	@Test
	public void testToString()
	{
		Color c1 = new Color(255, 0, 0, 0);
		Color c2 = new Color(0,0,0,0);
		
		assertEquals("#ff000000", c1.toString());
		assertEquals("#00000000", c2.toString());
	}
}
