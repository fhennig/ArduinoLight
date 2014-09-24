package arduinoLight.arduino;

import java.util.List;

import arduinoLight.util.RGBColor;

public class AdalightPackageFactory implements PackageFactory
{
	private List<RGBColor> _colors;
	private byte[] _package;
	


	@Override
	public byte[] createPackage(List<RGBColor> colors)
	{
		_colors = colors;
		_package = new byte[3 + 3 + colors.size() * 3];
		
		setupPackage();
		addColors();
		byte[] result = _package;
		_colors = null;
		_package = null;
		return result;
	}
	
	private void setupPackage()
	{
		_package[0] = 'A';                              // Magic word
		_package[1] = 'd';
		_package[2] = 'a';
		_package[3] = (byte)((_colors.size() - 1) >> 8);   // LED count high byte
		_package[4] = (byte)((_colors.size() - 1) & 0xff); // LED count low byte
		_package[5] = (byte)(_package[3] ^ _package[4] ^ 0x55); // Checksum
	}
	
	private void addColors()
	{
		int index = 6;
		for (RGBColor c : _colors)
		{
			_package[index++] = c.getCalculatedR();
			_package[index++] = c.getCalculatedG();
			_package[index++] = c.getCalculatedB();
		}
	}

	@Override
	public int getMaxPackageSize()
	{
		return 100;
	}

	@Override
	public String getName()
	{
		return "Adalight Protocol";
	}
}
