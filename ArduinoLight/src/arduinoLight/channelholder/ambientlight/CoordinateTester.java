package arduinoLight.channelholder.ambientlight;

public class CoordinateTester
{
	private final int _width, _height;
	
	
	
	public CoordinateTester(int w, int h)
	{
		_width = w;
		_height = h;
	}
	
	
	
	public boolean containsCoordinates(Areaselection selection, Coordinates coords)
	{
		double rowHeight = ((double)_height) / selection.getRows();
		int rowNumber = (int) (coords.getY() / rowHeight);
		
		double cWidth = (double)_width / selection.getColumns();
		int cNumber = (int) (coords.getX() / cWidth);
				
		return selection.getCell(cNumber, rowNumber);
	}
}
