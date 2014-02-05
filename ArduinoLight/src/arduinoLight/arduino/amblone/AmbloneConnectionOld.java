package arduinoLight.arduino.amblone;

import java.util.List;

import arduinoLight.arduino.SerialConnectionOld;
import arduinoLight.channelprovider.generator.Channelgenerator;
import arduinoLight.util.DebugConsole;
import arduinoLight.util.RGBColor;

/**
 * Concrete implementation of 'SerialConnection' using the Amblone-protocol.
 * @see AmblonePackage
 * @author Felix
 */
public class AmbloneConnectionOld extends SerialConnectionOld
{		
	public AmbloneConnectionOld(Channelgenerator channelprovider)
	{
		super(channelprovider);
	}

	@Override
	protected byte[] getBytesToTransmit(List<RGBColor> colors) {
		byte[] bytes = new AmblonePackage(colors).toByteArray();
		
		//Only for debugging:
		StringBuilder builder = new StringBuilder(bytes.length * 4);
		for (int i = 0; i < bytes.length; i++)
		{
			builder.append(bytes[i] + " ");
		}
		
		DebugConsole.print("AmbloneConnection", "getBytesToTransmit", "calculated Bytes: " + builder);
		return bytes;
	}
}
