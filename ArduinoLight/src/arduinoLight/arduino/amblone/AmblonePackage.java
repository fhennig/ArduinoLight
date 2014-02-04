package arduinoLight.arduino.amblone;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.util.RGBColor;


/**
 * On construction, this class takes a List of RGBColor and creates a byte-package consisting of
 * a Byte-Array from the first 4 colors in the list, a startflag and an endflag.
 * These Bytes can then be used to send them over a serialconnection. Get the Bytes with 'toByteArray()'.
 */
public class AmblonePackage
{	
	private final List<RGBColor> _colors;
	private final int colorCount;
	private final List<Byte> _package;
	
	
	public AmblonePackage(List<RGBColor> colors)
	{
		if (colors.size() > 4)
			colors = colors.subList(0, 4);
		_colors = colors;
		colorCount = _colors.size();
		
		int maxPackageSize = 2 + colorCount * (3 * 2);
		_package = new ArrayList<>(maxPackageSize);
		
		addStartflag();
		addColorflags();
		addEndflag();
	}
	
	
	
	/**
	 * @return A Byte-Array representing an Amblonepackage (Startflag, colorvalues, Endflag).
	 */
	public byte[] toByteArray()
	{
		byte[] byteArray = new byte[_package.size()];
		
		for (int i = 0; i < _package.size(); i++)
		{
			byteArray[i] = _package.get(i).byteValue();
		}
		
		return byteArray;
	}
	
	
	
	private void addStartflag()
	{
		byte startFlag = 0;
		
		switch(colorCount)
		{
			case 1: startFlag = AmbloneFlags.STARTFLAG1; break;
			case 2: startFlag = AmbloneFlags.STARTFLAG2; break;
			case 3: startFlag = AmbloneFlags.STARTFLAG3; break;
			case 4: startFlag = AmbloneFlags.STARTFLAG4; break;
			default:
			{
				throw new IllegalArgumentException("Illegal value (" + colorCount + ") for 'colorCount'."
												 + " Should be between 1 and 4.");
			}
		}
		
		_package.add(startFlag);
	}
	
	
	
	private void addColorflags()
	{
		for (int i = 0; i < colorCount; i++)
		{
			if (AmbloneFlags.isReservedValue(_colors.get(i).getCalculatedR()))
				addEscapeflag();
			_package.add(_colors.get(i).getCalculatedR());
			
			if (AmbloneFlags.isReservedValue(_colors.get(i).getCalculatedG()))
				addEscapeflag();
			_package.add(_colors.get(i).getCalculatedG());
		
			if (AmbloneFlags.isReservedValue(_colors.get(i).getCalculatedB()))
				addEscapeflag();
			_package.add(_colors.get(i).getCalculatedB());
		}
	}
	
	
	
	private void addEscapeflag()
	{
		_package.add(AmbloneFlags.ESCFLAG);
	}
	
	
	
	private void addEndflag()
	{
		_package.add(AmbloneFlags.ENDFLAG);
	}
	
	/**
	 * These constants correspond to the byte-values used on the arduino.
	 * The private class is used to encapsulate the values.
	 */
	private static class AmbloneFlags
	{
		public static final byte STARTFLAG1 = (byte) 241;
		public static final byte STARTFLAG2 = (byte) 242;
		public static final byte STARTFLAG3 = (byte) 243;
		public static final byte STARTFLAG4 = (byte) 244;
		public static final byte ENDFLAG = 51;
		public static final byte ESCFLAG = (byte) 0x99;
		public static final byte[] RESERVED_FLAGS = {STARTFLAG1, STARTFLAG2, STARTFLAG3, STARTFLAG4, ENDFLAG, ESCFLAG};
		
		public static final boolean isReservedValue(byte b) //TODO this has really bad performance and is called very often
		{
			for(int i = 0; i < RESERVED_FLAGS.length; i++)
			{
				if (b == RESERVED_FLAGS[i])
					return true;
			}
			return false;
		}
	}
}
