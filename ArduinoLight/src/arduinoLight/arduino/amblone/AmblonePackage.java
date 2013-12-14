package arduinoLight.arduino.amblone;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.util.IRGBColor;


/**
 * On construction, this class takes a List of IRGBColor an creates a Byte-Array from these colors.
 * These Bytes can be sent through a serial connection.
 * @author felix
 */
public class AmblonePackage
{	
	private List<IRGBColor> _colors;
	private int colorCount;
	private List<Byte> _package;
	
	
	public AmblonePackage(List<IRGBColor> colors)
	{
		_colors = colors;
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
