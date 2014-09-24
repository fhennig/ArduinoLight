package arduinoLight.arduino;

import java.util.List;

import arduinoLight.util.RGBColor;

public interface PackageFactory
{
	public byte[] createPackage(List<RGBColor> colors);
	public int getMaxPackageSize();
	public String getName();
}
