package arduinoLight.channelprovider.modifier;

import java.util.List;

import arduinoLight.channelprovider.Channelprovider;
import arduinoLight.util.Color;
import arduinoLight.util.IChannel;

public class ColorcorrectionDecorator extends ProviderDecorator
{
	private double _a = 1;
	private double _r = 1;
	private double _g = 1;
	private double _b = 1;
	
	
	
	public ColorcorrectionDecorator(Channelprovider channelprovider)
	{
		super(channelprovider);
	}


	
	public void setAPercent(double a)
	{
		if (a > 1 || a < 0)
			throw new IllegalArgumentException();
		_a = a;
	}
	
	public void setRPercent(double r)
	{
		if (r > 1 || r < 0)
			throw new IllegalArgumentException();
		_r = r;
	}
	
	public void setGPercent(double g)
	{
		if (g > 1 || g < 0)
			throw new IllegalArgumentException();
		_g = g;
	}
	
	public void setBPercent(double b)
	{
		if (b > 1 || b < 0)
			throw new IllegalArgumentException();
		_b = b;
	}
	
	@Override
	protected void updateChannels(List<IChannel> newChannels)
	{
		_channels.clear();
		
		Color color;
		int newA;
		int newR;
		int newG;
		int newB;
		for (IChannel channel : newChannels)
		{
			newA = (int) Math.round(channel.getColor().getA() * _a);
		}
		
	}

}
