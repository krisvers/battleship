public class Ship
{
	private int row = UNSET;
	private int col = UNSET;
	private int length = 0;
	private int direction = UNSET;

	// Direction constants
	private static final int UNSET = -1;
	private static final int HORIZONTAL = 0;
	private static final int VERTICAL = 1;

	// Constructor. Create a ship and set the length.
	public Ship(int len) {
		length = len;
	}

	// Has the location been initialized
	public boolean isLocationSet() {
		return (row != UNSET && col != UNSET);
	}

	// Has the direction been initialized
	public boolean isDirectionSet() {
		return (direction != UNSET);
	}

	// Set the location of the ship
	public void setLocation(int r, int c) {
		row = r;
		col = c;
	}

	// Set the direction of the ship
	public void setDirection(int dir) {
		direction = dir;
	}

	// Getter for the row value
	public int getRow() {
		return row;
	}

	// Getter for the column value
	public int getCol() {
		return col;
	}

	// Getter for the length of the ship
	public int getLength() {
		return length;
	}

	// Getter for the direction
	public int getDirection() {
		return direction;
	}

	// Helper method to get a string value from the direction
	private String directionToString() {
		if (direction == UNSET) {
			return "unset direction";
		} else if (direction == VERTICAL) {
			return "vertical";
		} else if (direction == HORIZONTAL) {
			return "horizontal";
		}

		return null;
	}

	// Helper method to get a (row, col) string value from the location
	private String locationToString() {
		if (row == UNSET || col == UNSET) {
			return "(unset location)";
		}
		return "(" + row + ", " + col + ")";
	}

	// toString value for this Ship
	public String toString() {
		return directionToString() + " ship of length " + length + " at " + locationToString();
	}
}