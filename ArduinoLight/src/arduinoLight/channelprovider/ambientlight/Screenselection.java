package arduinoLight.channelprovider.ambientlight;

/**
 * Represents a relative Screenpart (i.e. 'top left quarter' == (r0c0 = true)(r0c1 = false)(... = false)).
 * 
 * @author Felix
 *
 */
public class Screenselection
{
	private boolean[][] _matrix;
	
	
	
	public Screenselection(int columns, int rows)
	{
		if (columns < 1 || rows < 1)
		{
			throw new IllegalArgumentException();
		}
		
		_matrix = new boolean[columns][rows];
	}

	
	
	public void setCell(int x, int y, boolean flag)
	{
		if (x >= getColumns() || x < 0 || y < 0 || y >= getRows())
		{
			throw new IllegalArgumentException();
		}
		
		_matrix[x][y] = flag;
	}
	
	public boolean getCell(int x, int y)
	{
		if (x >= getColumns() || x < 0 || y < 0 || y >= getRows())
		{
			throw new IllegalArgumentException();
		}
		
		return _matrix[x][y];
	}

	public void changeMatrixsize(int newColCount, int newRowCount)
	{
		if (newColCount < 1 || newRowCount < 1)
		{
			throw new IllegalArgumentException();
		}
		boolean[][] newMatrix = new boolean[newColCount][newRowCount];
		int smallestColCount = getSmallerInt(newColCount, getColumns());
		int smallestRowCount = getSmallerInt(newRowCount, getRows());
		
		for (int c = 0; c < smallestColCount; c++)
		{
			for (int r = 0; r < smallestRowCount; r++)
			{
				newMatrix[c][r] = _matrix[c][r];
			}
		}
		
		_matrix = newMatrix;
	}

	public void clearMatrix()
	{
		_matrix = new boolean[_matrix.length][_matrix[0].length];
	}
	
	public int getColumns()
	{
		return _matrix[0].length;
	}
	
	
	public int getRows()
	{
		return _matrix.length;
	}
	
	private int getSmallerInt(int i1, int i2)
	{
		if (i1 > i2)
		{
			return i2;
		}
		else
		{
			return i1;
		}
	}
}
