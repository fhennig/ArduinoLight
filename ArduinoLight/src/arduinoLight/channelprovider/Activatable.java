package arduinoLight.channelprovider;

public interface Activatable
{
	public boolean setActive(boolean newActive);
	public void addActiveStateListener(ActiveStateListener listener);
	public void removeActiveStateListener(ActiveStateListener listener);
}
