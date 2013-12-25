package arduinoLight.channelprovider.generator.ambientlight;

import arduinoLight.util.Channel;

/**
 * Specialized Channel-class that also holds a reference to a Screenselection that is attached to this channel.
 * @author Felix
 */
public class AmbientlightChannel extends Channel
{
	private Screenselection _screenselection;
	
	public Screenselection getScreenselection()
	{
		return _screenselection;
	}
	
	public void setScreenselection(Screenselection selection)
	{
		_screenselection = selection;
	}
}
