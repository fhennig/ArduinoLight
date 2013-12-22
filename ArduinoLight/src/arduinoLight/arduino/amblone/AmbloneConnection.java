package arduinoLight.arduino.amblone;

import java.util.List;

import arduinoLight.arduino.SerialConnection;
import arduinoLight.mixer.Colorprovider;
import arduinoLight.util.DebugConsole;
import arduinoLight.util.RGBColor;

/**
 * Concrete implementation of 'SerialConnection' using the Amblone-protocol.
 * @see AmblonePackage
 * @author Felix
 */
public class AmbloneConnection extends SerialConnection
{		
	public AmbloneConnection(Colorprovider colorprovider)
	{
		super(colorprovider);
	}

	@Override
	protected byte[] getBytesToTransmit(List<RGBColor> colors) {
		byte[] bytes = new AmblonePackage(colors).toByteArray();
		
		StringBuilder builder = new StringBuilder(bytes.length * 4);
		for (int i = 0; i < bytes.length; i++)
		{
			builder.append(Integer.toHexString(bytes[i]) + " ");
		}
		
		DebugConsole.print("AmbloneConnection", "getBytesToTransmit", "calculated Bytes: " + builder);
		return bytes;
	}
}
