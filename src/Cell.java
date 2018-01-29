import javax.swing.JButton;

/**
 * Board cell.
 */
@SuppressWarnings("serial")
class Cell
	extends JButton
{
	private Coordinate position = null;
	
	/**
	 * Constructor for `Cell`.
	 *
	 * @param position Coordinates of the cell.
	 */
	public Cell(Coordinate position)
	{
		super();
		
		// TODO: Validate argument.
		
		this.position = position;
	}
	
	/**
	 * Constructor for `Cell`.
	 *
	 * @param x X-coordinate of the cell.
	 * @param y Y-coordinate of the cell.
	 */
	public Cell(int x, int y)
	{
		super();
		
		position = new Coordinate(x, y);
	}
	
	/**
	 * Get the coordinates of the cell.
	 *
	 * @return The coordinates.
	 */
	public Coordinate getPosition()
	{
		return position;
	}
	
	/**
	 * Get the X-coordinate of the cell.
	 *
	 * @return The X-coordinate.
	 */
	public int getPositionX()
	{
		return position.x;
	}
	
	/**
	 * Get the Y-coordinate of the cell.
	 *
	 * @return The Y-coordinate.
	 */
	public int getPositionY()
	{
		return position.y;
	}
}