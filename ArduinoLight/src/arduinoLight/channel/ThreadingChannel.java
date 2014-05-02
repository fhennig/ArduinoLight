package arduinoLight.channel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import arduinoLight.framework.Event;
import arduinoLight.framework.EventDispatchHandler;
import arduinoLight.util.Color;

/**
 * Implementation of the Channel interface. <br>
 * thread-safety: Delegation to thread-safe collections; final fields; volatile,
 * immutable fields.
 */
public class ThreadingChannel implements Channel {
	/** unique id, used for hashCode. Is threadsafe because final and primitive */
	private final int _id;

	/**
	 * immutable objects + volatile used to ensure visibility of changes across
	 * all threads
	 */
	private volatile Color _color = Color.BLACK;
	private volatile String _name = "Channel";

	/**
	 * final CopyOnWriteArrayList used for save concurrent access /
	 * thread-safety
	 */
	private final List<ColorListener> _colorListeners = new CopyOnWriteArrayList<>();
	private final List<NameListener> _nameListeners = new CopyOnWriteArrayList<>();

	/**
	 * @param id
	 *            a unique integer.
	 */
	public ThreadingChannel(int id) {
		_id = id;
	}

	// ---------- IChannel: Getters / Setters -------------------
	@Override
	public Color getColor() {
		return _color;
	}

	@Override
	public void setColor(Color color) {
		_color = color;
		raiseColorChangedEvent(color);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setName(String name) {
		_name = name;
		raiseNameChangedEvent(name);
	}

	@Override
	public int getId() {
		return _id;
	}

	// ---------- Events ----------------------------------------
	/**
	 * Color needs to get passed in as a parameter, to ensure that the correct
	 * color is sent to the listeners. Events are fired concurrently via the
	 * EventDispatchHandler.
	 */
	private void raiseColorChangedEvent(final Color color) {
		EventDispatchHandler.getInstance().dispatch(
				new Event(this, "ColorChanged") {
					@Override
					public void notifyListeners() {
						for (ColorListener listener : _colorListeners)
							listener.colorChanged(this, color);
					}
				});
	}

	private void raiseNameChangedEvent(final String name) {
		EventDispatchHandler.getInstance().dispatch(
				new Event(this, "NameChanged") {
					@Override
					public void notifyListeners() {
						for (NameListener listener : _nameListeners)
							listener.nameChanged(this, name);
					}
				});
	}

	@Override
	public void addColorListener(ColorListener listener) {
		_colorListeners.add(listener);
	}

	@Override
	public void removeColorListener(ColorListener listener) {
		_colorListeners.remove(listener);
	}

	@Override
	public void addNameListener(NameListener listener) {
		_nameListeners.add(listener);
	}

	@Override
	public void removeNameListener(NameListener listener) {
		_nameListeners.remove(listener);
	}

	// ---------- overridden from object -----------------------
	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (object == this) {
			return true;
		}
		if (!(object instanceof ThreadingChannel)) {
			return false;
		}
		final ThreadingChannel other = (ThreadingChannel) object;
		if (other.getColor().equals(this.getColor())
				&& other.getName().equals(this.getName())) {
			return true;
		}
		return false;
	};

	@Override
	public int hashCode() {
		return _id * 57;
	}

	@Override
	public String toString() {
		return _name + "[" + _id + "]";
	}
}