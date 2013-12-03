package arduino.amblone;

import java.util.List;

import arduino.SerialConnection;
import colorprovider.Colorprovider;
import colorprovider.ColorsChangedListener;
import util.IRGBColor;

/**
 * Complete implementation of 'SerialConnection' using the Amblone-protocol
 * @author Felix
 */
public class AmbloneConnection extends SerialConnection implements ColorsChangedListener
{		
	private List<IRGBColor> _colors;
	
	public AmbloneConnection(Colorprovider c)
	{
		super(c);
	}

	@Override
	public void colorsChanged()
	{
		_colors = _colorprovider.getColors();
		byte[] packageAsArray = new AmblonePackage(_colors).toByteArray();
		transmit(packageAsArray);
	}

	@Override
	protected void subscribeEvents()
	{
		_colorprovider.addColorsChangedListener(this);
	}

	@Override
	protected void unsubscribeEvents()
	{
		_colorprovider.removeColorsChangedListener(this);
	}
}
