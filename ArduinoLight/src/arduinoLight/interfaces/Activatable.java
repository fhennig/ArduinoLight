package arduinoLight.interfaces;

import arduinoLight.interfaces.propertyListeners.ActiveListener;


/**
 * Gets implemented by the Channelprovider.
 * This interface enables the UI-Designer to have a more modular design,
 * as there can be a Panel that only needs an 'Activatable' and not a full blown Channelprovider.
 * Also this leads to more distinguished notifications.
 * In the Channelprovider, you can now subscribe single Events, not 3 Events at once.
 * @author Felix
 */
public interface Activatable
{
	public boolean setActive(boolean newActive);
	public void addActiveStateListener(ActiveListener listener);
	public void removeActiveStateListener(ActiveListener listener);
}
