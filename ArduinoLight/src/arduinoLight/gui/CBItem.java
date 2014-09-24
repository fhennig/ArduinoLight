package arduinoLight.gui;

public class CBItem<T>
{
	public interface TextGetter<T>
	{
		public String getText(T item);
	}
	
	private final TextGetter<T> _getter;
	private final T _item;
	
	
	
	public CBItem(T item, TextGetter<T> getter)
	{
		_item = item;
		_getter = getter;
	}
	
	public T getItem()
	{
		return _item;
	}
	
	@Override
	public String toString()
	{
		return _getter.getText(_item);
	}
}
