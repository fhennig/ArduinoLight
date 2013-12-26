package arduinoLight.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A Channel holds a color and a name. the name should be unique!
 * @author Felix
 */
public class Channel implements IChannel
{
	private static final AtomicInteger _instances = new AtomicInteger(0); //Used to generate Ids.
	private final int _id;
	private Color _color;
	
	
	
	public Channel()
	{
		this(new Color());		
	}
	
	public Channel(Color color)
	{
		setColor(color);
		_id = _instances.getAndIncrement();
	}

	
	
	@Override
	public int getId()
	{
		//no synchronizing needed here, because _id is final.
		return _id;
	}
	
	@Override
	public synchronized Color getColor()
	{
		return _color;
	}

	@Override
	public synchronized void setColor(Color color)
	{
		_color = color;
	}

	//---------- overridden from object ------------------------
	@Override
	public int hashCode() {
		return _id * 137;
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
	
	@Override
	public String toString()
	{
		return _id + ": " + _color.toString();
	}
}
