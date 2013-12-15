package arduinoLight.arduino.amblone;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.util.RGBColor;


/**
 * On construction, this class takes a List of IRGBColor and creates a Byte-Array from the first 4 colors in the list.
 * These Bytes can then be used to send them over a serialconnection.
 * @author Felix
 */
public class AmblonePackage
{	
	private List<RGBColor> _colors;
	private int colorCount;
	private List<Byte> _package;
	
	
	public AmblonePackage(List<RGBColor> colors)
	{
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
		Byte[] bArr = (Byte[]) _package.toArray();
		byte[] byteArray = new byte[bArr.length];
		
		for (int i = 0; i < bArr.length; i++)
		{
			byteArray[i] = bArr[i].byteValue();
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
	}
	
	
	
	private void setColorflags()
	{
		for (int i = 0; i < colorCount; i++)
		{
			if (_colors.get(i).getCalculatedR() == AmbloneFlags.ENDFLAG)
				setEscapeflag();
			_package.add(_colors.get(i).getCalculatedR());
			
			if (_colors.get(i).getCalculatedG() == AmbloneFlags.ENDFLAG)
				setEscapeflag();
			_package.add(_colors.get(i).getCalculatedG());
		
			if (_colors.get(i).getCalculatedB() == AmbloneFlags.ENDFLAG)
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
	
	private class AmbloneFlags
	{
		public static final byte STARTFLAG1 = 1;
		public static final byte STARTFLAG2 = 2;
		public static final byte STARTFLAG3 = 3;
		public static final byte STARTFLAG4 = 4;
		public static final byte ENDFLAG = 3;
		public static final byte ESCFLAG = 6;
	}
}
