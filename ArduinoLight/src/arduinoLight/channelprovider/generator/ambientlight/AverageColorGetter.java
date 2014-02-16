package arduinoLight.channelprovider.generator.ambientlight;

import java.util.ArrayList;
import java.util.List;

import arduinoLight.util.Color;

public class AverageColorGetter
{
	private Image _image;

	public AverageColorGetter(Image image)
	{
		_image = image;
	}
	
	public void setImage(Image image)
	{
		_image = image;
	}
	
	public Color getAverageColor(Areaselection selection)
	{
		List<Color> selectedPixels = getAverageColors(selection);
		
		return Color.getAverageColor(selectedPixels);
	}
	
	public List<Color> getAverageColors(Areaselection selection)
	{
		if (selection == null)
			return new ArrayList<Color>();
		
		List<Color> averageColors = new ArrayList<>(selection.getColumns() * selection.getRows());
		double partWidth = (double)_image.getWidth() / selection.getColumns();
		double partHeight = (double)_image.getHeight() / selection.getRows();
		//DebugConsole.print("Image", "getSelectedPixels", "width: " + getWidth() + "   sel-cols: " + selection.getColumns() + "   partW: " + partWidth);
		//DebugConsole.print("Image", "getSelectedPixels", "height: " + getHeight() + "   sel-rows: " + selection.getRows() + "   partH: " + partHeight);
		System.out.println();
		for (int y = 0; y < selection.getRows(); y++)
		{
			for (int x = 0; x < selection.getColumns(); x++)
			{
				if (selection.getCell(x, y))
				{
					int imgX1 = (int) Math.round(x * partWidth);
					int imgY1 = (int) Math.round(y * partHeight);
					int imgX2 = (int) Math.round((x + 1) * partWidth) - 1;
					int imgY2 = (int) Math.round((y + 1) * partHeight - 1);
					
					averageColors.add(getAverageColor(imgX1, imgY1, imgX2, imgY2));
				}
			}
		}
		
		return averageColors;
	}
	
	/**
	 * Returns a List of Colors that contains the Colors that are inside
	 * the rectangle that is specified by the given points (x1|y1), (x2|y2).
	 * The given points are included in the rectangle.
	 */
	public Color getAverageColor(int x1, int y1, int x2, int y2)
	{
		_image.validateCoordinates(x1, y1);
		_image.validateCoordinates(x2, y2);
		
		if (x1 > x2)
		{
			int temp = x2;
			x2 = x1;
			x1 = temp;
		}
		if (y1 > y2)
		{
			int temp = y2;
			y2 = y1;
			y1 = temp;
		}
		
		long aSum = 0;
		long rSum = 0;
		long gSum = 0;
		long bSum = 0;
		long colors = 0;
		
		Color color;
		for (int y = y1; y <= y2; y++)
		{
			for (int x = x1; x <= x2; x++)
			{
				color = _image.getPixel(x, y);
				aSum += color.getA();
				rSum += color.getR();
				gSum += color.getG();
				bSum += color.getB();
				colors++;
			}
		}
		
		int aAvg = (int)(((double)aSum) / colors);
		int rAvg = (int)(((double)rSum) / colors);
		int gAvg = (int)(((double)gSum) / colors);
		int bAvg = (int)(((double)bSum) / colors);
		
		return new Color(aAvg, rAvg, gAvg, bAvg);
	}

}
