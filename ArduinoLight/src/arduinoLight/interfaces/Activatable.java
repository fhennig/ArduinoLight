package arduinoLight.channelprovider;

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
	public void addActiveStateListener(ActiveStateListener listener);
	public void removeActiveStateListener(ActiveStateListener listener);
}
