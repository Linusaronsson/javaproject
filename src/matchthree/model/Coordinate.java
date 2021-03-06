package matchthree.model;

/**
 * Stores a two-dimensional coordinate, limited to the first quadrant.
 *
 * @author Erik Selstam
 */
public class Coordinate
{
	/** X-coordinate. */
	private int x = 0;
	
	/** Y-coordinate. */
	private int y = 0;
	
	/**
	 * Constructor for `Coordinate`.
	 *
	 * @author Erik Selstam
	 * @param x X-coordinate. Must be positive.
	 * @param y Y-coordinate. Must be positive.
	 */
	public Coordinate(final int x, final int y) {
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get X-coordinate.
	 *
	 * @author Erik Selstam
	 * @return The X-coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get Y-coordinate.
	 *
	 * @author Erik Selstam
	 * @return The Y-coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Set X-coordinate.
	 *
	 * @author Erik Selstam
	 * @param x New X-coordinate.
	 */
	public void setX(final int x) {
		if (x < 0) {
			throw new IllegalArgumentException();
		}
		
		this.x = x;
	}
	
	/**
	 * Set Y-coordinate.
	 *
	 * @author Erik Selstam
	 * @param y New Y-coordinate.
	 */
	public void setY(final int y) {
		if (y < 0) {
			throw new IllegalArgumentException();
		}
		
		this.y = y;
	}
}
