package arduinoLight.channelholder.ambientlight;


/**
 * Represents a relative part of a 2D Area (i.e. 'top left quarter' == (r0c0 = true)(r0c1 = false)(... = false)). <br>
 * thread-safety: yes, methods are synchronized.
 */
public class Areaselection
{
	private boolean[][] _matrix;
	
	
	/**
	 * Creates a new Areaselection with the specified Amount of rows and columns.
	 */
	public Areaselection(int rows, int columns)
	{
		if (columns < 1 || rows < 1)
		{
			throw new IllegalArgumentException("Arguments cannot be less than or equal to 0.");
		}
		
		_matrix = new boolean[rows][columns];
	}

	/**
	 * Sets a cell to the given 'flag'-value.
	 * @param x the Column of the cell
	 * @param y the Row of the cell
	 */
	public synchronized void setCell(int x, int y, boolean flag)
	{
		validateCoordinates(x, y);
		
		_matrix[y][x] = flag;
	}
	
	/**
	 * Returns the flag of a cell which is specified through the given coordinates
	 * @param x the Column of the cell
	 * @param y the Row of the cell
	 */
	public synchronized boolean getCell(int x, int y)
	{
		validateCoordinates(x, y);
		
		return _matrix[y][x];
	}

	/**
	 * Creates a new matrix that contains the old matrix as good as possible.
	 * If the new one is bigger, the old one is copied into the new one.
	 * If the new one is smaller, the new one is a subsection of the old one.
	 * @param newRowCount new Amount of Rows
	 * @param newColCount new Amount of Columns
	 */
	public synchronized void changeSize(int newRowCount, int newColCount)
	{
		if (newColCount == getColumns() && newRowCount == getRows())
			return;
		
		if (newColCount < 1 || newRowCount < 1)
		{
			throw new IllegalArgumentException();
		}
		boolean[][] newMatrix = new boolean[newRowCount][newColCount];
		int smallestColCount = Math.min(newColCount, getColumns());
		int smallestRowCount = Math.min(newRowCount, getRows());
		
		for (int r = 0; r < smallestRowCount; r++)
		{
			for (int c = 0; c < smallestColCount; c++)
			{
				newMatrix[r][c] = _matrix[r][c];
			}
		}
		_matrix = newMatrix;
	}

	/**
	 * Sets every cell of the matrix to false.
	 */
	public void setAll(boolean flag)
	{
		synchronized (_matrix)
		{
			for (int y = 0; y < getRows(); y++)
			{
				for (int x = 0; x < getColumns(); x++)
				{
					_matrix[y][x] = flag;
				}
			}
		}
	}
	
	/**
	 * Returns the Amount of Columns.
	 */
	public synchronized int getColumns()
	{
		return _matrix[0].length;
	}
	
	/**
	 * Returns the Amount of Rows.
	 */
	public synchronized int getRows()
	{
		return _matrix.length;
	}
	
	
	
	/** private helper-method that is used to throw events if given arguments are invalid */
	private void validateCoordinates(int x, int y)
	{
		if (x < 0 || x >= getColumns())
			throw new IllegalArgumentException("x should be between 0 and " + getColumns() + " but was " + x);
		if (y < 0 || y >= getRows())
			throw new IllegalArgumentException("y should be between 0 and " + getRows() + " but was " + y);
	}
}