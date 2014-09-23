package arduinoLight.channelholder.ambientlight;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.HashMap;
import java.util.Map;

import arduinoLight.channel.Channel;
import arduinoLight.util.DebugConsole;

public class ColorSetter
{
	private final Map<Channel, Areaselection> _asMap;
	
	
	
	public ColorSetter(Map<Channel, Areaselection> map)
	{
		assert map != null;
		_asMap = map;
	}
	
	
	
	public void setColors(BufferedImage image)
	{
		int[] colorArray = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		Map<Channel, AvgColorBuilder> cbMap = createBuilderMap();
		CoordinateTester cTester = new CoordinateTester(image.getWidth(), image.getHeight());
		
		for (int i = 0; i < colorArray.length; i += 100)
		{
			Coordinates coords = getCoordinates(i, image.getWidth());
			for (Channel c : _asMap.keySet())
			{
				if (cTester.containsCoordinates(_asMap.get(c), coords))
					cbMap.get(c).addColor(colorArray[i]);
			}
		}
		for (Channel c : cbMap.keySet())
			c.setColor(cbMap.get(c).getAvgColor());
		DebugConsole.print("ColorSetter", "setColors", "Colors set");
	}
	
	private Coordinates getCoordinates(int index, int w)
	{
		int x = index % w;
		int y = (int)(index / w); //Cast to round down
		return new Coordinates(x, y);
	}
	
	private Map<Channel, AvgColorBuilder> createBuilderMap()
	{
		Map<Channel, AvgColorBuilder> map = new HashMap<>();
		for (Channel c : _asMap.keySet())
		{
			map.put(c, new AvgColorBuilder());
		}
		return map;
	}
}
