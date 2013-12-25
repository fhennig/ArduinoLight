package arduinoLight.util;

/**
 * A Channel holds a color and a name. the name should be unique!
 * @author Felix
 */
public class Channel implements IChannel
{
	private static int _instances = 0; //used to generate unique names.
	private final int _id;
	private Color _color;
	
	public Channel()
	{
		this(new Color());		
	}
	
	public Channel(Color color)
	{
		setColor(color);
		_id = _instances;
		_instances++;
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
	public int getId() {
		return _id;
	}

	@Override
	public int hashCode() {
		return _id;
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


		if (this._id != other._id) {
			return false;
		}
		return true;
	}
}
