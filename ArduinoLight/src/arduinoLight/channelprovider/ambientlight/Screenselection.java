package arduinoLight.channelprovider.ambientlight;

/**
 * Represents a relative Screenpart (i.e. 'top left quarter' == (r0c0 = true)(r0c1 = false)(... = false)).
 * @author Felix
 */
public class Screenselection
{
	private boolean[][] _matrix;
	
	
	/**
	 * Creates a new Screenselection with the specified Amount of Rows and Columns.
	 */
	public Screenselection(int columns, int rows)
	{
		if (columns < 1 || rows < 1)
		{
			throw new IllegalArgumentException();
		}
		
		_matrix = new boolean[columns][rows];
	}

	/**
	 * Sets a cell to the given 'flag'-value.
	 * @param x the Column of the cell
	 * @param y the Row of the cell
	 */
	public void setCell(int x, int y, boolean flag)
	{
		if (x >= getColumns() || x < 0 || y < 0 || y >= getRows())
		{
			throw new IllegalArgumentException();
		}
		
		_matrix[x][y] = flag;
	}
	
	/**
	 * Returns the value of a cell.
	 * @param x the Column of the cell
	 * @param y the Row of the cell
	 */
	public boolean getCell(int x, int y)
	{
		if (x >= getColumns() || x < 0 || y < 0 || y >= getRows())
		{
			throw new IllegalArgumentException();
		}
		
		return _matrix[x][y];
	}

	/**
	 * Creates a new matrix that contains the old matrix as good as possible.
	 * If the new one is bigger, the old one is copied into the new one.
	 * If the new one is smaller, the new one is a subsection of the old one.
	 * @param newColCount new Amount of Columns
	 * @param newRowCount new Amount of Rows
	 */
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

	/**
	 * Sets every cell of the matrix to false.
	 */
	public void clearMatrix()
	{
		_matrix = new boolean[_matrix.length][_matrix[0].length];
	}
	
	/**
	 * Returns the Amount of Columns.
	 */
	public int getColumns()
	{
		return _matrix[0].length;
	}
	
	/**
	 * Returns the Amount of Rows.
	 */
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
