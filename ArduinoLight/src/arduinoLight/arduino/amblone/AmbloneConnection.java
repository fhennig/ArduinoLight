package arduinoLight.arduino.amblone;

import java.util.List;

import arduinoLight.arduino.SerialConnection;
import arduinoLight.colorprovider.Colorprovider;
import arduinoLight.util.IRGBColor;

/**
 * Complete implementation of 'SerialConnection' using the Amblone-protocol
 * @author Felix
 */
public class AmbloneConnection extends SerialConnection
{		
	private List<IRGBColor> _colors;
	
	public AmbloneConnection(Colorprovider c)
	{
		super(c);
	}

	@Override
	public void colorsChanged(List<IRGBColor> newColors)
	{
		_colors = newColors;
		byte[] packageAsArray = new AmblonePackage(_colors).toByteArray();
		transmit(packageAsArray);
	}
}
