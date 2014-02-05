package arduinoLight.channelprovider;

import arduinoLight.channelprovider.generator.Channelgenerator;

/**
 * This class is a dummy. Just a placeholder for the GUI.
 */
public class Dummyprovider extends Channelgenerator
{
	@Override
	protected boolean activate()
	{
		return true;
	}
	
	@Override
	protected boolean deactivate()
	{
		return true;
	}
}
