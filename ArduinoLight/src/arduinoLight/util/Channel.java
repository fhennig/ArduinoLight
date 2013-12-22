package arduinoLight.util;

/**
 * A Channel holds a color and a name. the name should be unique!
 * @author Felix
 */
public class Channel implements IChannel
{
	private static int _instances; //used to generate unique names.
	private Color _color;
	private final String _name;
	
	public Channel()
	{
		this("channel" + _instances);
		_instances++;
	}
	
	public Channel(String name)
	{
		this(name, new Color());
	}
	
	public Channel(String name, Color color)
	{
		_name = name;
		_color = color;
	}
	
	@Override
	public Color getColor() {
		return _color;
	}

	@Override
	public void setColor(Color color)
	{
		_color = color;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		final int prime = 37;
		int result = 1;
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Channel)) {
			return false;
		}
		Channel other = (Channel) obj;


		if (_name == null) {
			if (other._name != null) {
				return false;
			}
		} else if (!_name.equals(other._name)) {
			return false;
		}
		return true;
	}
}
