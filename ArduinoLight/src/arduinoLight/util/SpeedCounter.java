package arduinoLight.util;

import java.util.*;

/**
 * This is a simple class that aids in measuring the packages per second currently transmitted.
 * @author Felix
 */
public class SpeedCounter
{
	private int _ticksSinceStart = 0;
	private int _speed = 0;
	private long _startTime = System.currentTimeMillis();
	
	private List<SpeedChangeListener> _listeners = new ArrayList<>();
	
	
	
	
	public void tick()
	{
		_ticksSinceStart++;
		long currentTime = System.currentTimeMillis();
		
		if (currentTime - _startTime >= 1000)
		{
			_speed = _ticksSinceStart;
			_ticksSinceStart = 0;
			_startTime = currentTime;
			fireSpeedChangeEvent(_speed);
		}
	}
	
	public void reset()
	{
		_speed = 0;
		_ticksSinceStart = 0;
		_startTime = System.currentTimeMillis();
		fireSpeedChangeEvent(_speed);
	}
	
	public int getSpeed()
	{
		return _speed;
	}
	
	private void fireSpeedChangeEvent(int newSpeed)
	{
		for (SpeedChangeListener listener : _listeners)
		{
			listener.speedChanged(newSpeed);
		}
	}
	
	public void addSpeedChangeListener(SpeedChangeListener listener)
	{
		_listeners.add(listener);
	}
	
	public void removeSpeedChangeListener(SpeedChangeListener listener)
	{
		_listeners.remove(listener);
	}
}
