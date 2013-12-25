package arduinoLight.arduino.amblone;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.util.DebugConsole;
import arduinoLight.util.RGBColor;


/**
 * On construction, this class takes a List of RGBColor and creates a Byte-Array from the first 4 colors in the list.
 * These Bytes can then be used to send them over a serialconnection. Get the Bytes with 'toByteArray()'.
 * @author Felix
 */
public class AmblonePackage
{	
	private List<RGBColor> _colors;
	private int colorCount;
	private List<Byte> _package;
	
	
	public AmblonePackage(List<RGBColor> colors)
	{
		_colors = colors;
		if (_colors.size() > 4)
			_colors = colors.subList(0, 4);
		colorCount = _colors.size();
		
		int maxPackageSize = 2 + colorCount * (3 * 2);
		_package = new ArrayList<>(maxPackageSize);
		
		setStartflag();
		setColorflags();
		setEndflag();
	}
	
	
	
	/**
	 * @return A Byte-Array representing an Amblonepackage (Startflag, colorvalues, Endflag).
	 */
	public byte[] toByteArray()
	{
		Object[] objArray =  _package.toArray();
		byte[] byteArray = new byte[objArray.length];
		
		for (int i = 0; i < objArray.length; i++)
		{
			byteArray[i] = ((Byte) objArray[i]).byteValue();
		}
		
		return byteArray;
	}
	
	
	
	private void setStartflag()
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
		DebugConsole.print("AmblonePackage", "setStartflag", "sf-value:" + startFlag);
	}
	
	
	
	private void setColorflags()
	{
		for (int i = 0; i < colorCount; i++)
		{
			if (AmbloneFlags.isReservedValue(_colors.get(i).getCalculatedR()))
				setEscapeflag();
			_package.add(_colors.get(i).getCalculatedR());
			
			if (AmbloneFlags.isReservedValue(_colors.get(i).getCalculatedG()))
				setEscapeflag();
			_package.add(_colors.get(i).getCalculatedG());
		
			if (AmbloneFlags.isReservedValue(_colors.get(i).getCalculatedB()))
				setEscapeflag();
			_package.add(_colors.get(i).getCalculatedB());
		}
	}
	
	
	
	private void setEscapeflag()
	{
		_package.add(AmbloneFlags.ESCFLAG);
	}
	
	
	
	private void setEndflag()
	{
		_package.add(AmbloneFlags.ENDFLAG);
	}
	
	/**
	 * These constants correspond to the byte-values used on the arduino.
	 * The private class is used to encapsulate the values.
	 * @author Felix
	 */
	private static class AmbloneFlags
	{
		public static final byte STARTFLAG1 = (byte) 241;
		public static final byte STARTFLAG2 = (byte) 242;
		public static final byte STARTFLAG3 = (byte) 243;
		public static final byte STARTFLAG4 = (byte) 244;
		public static final byte ENDFLAG = 51;
		public static final byte ESCFLAG = (byte) 0x99;
		public static final byte[] RESERVED_FLAGS = {STARTFLAG1, STARTFLAG2, STARTFLAG3, STARTFLAG4, ENDFLAG};
		
		public static final boolean isReservedValue(byte b)
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
