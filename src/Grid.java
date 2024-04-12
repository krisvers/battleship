public class Grid
{
	private Location[][] grid;

	public static final int NUM_ROWS = 10;
	public static final int NUM_COLS = 10;

	public Grid() {
		grid = new Location[NUM_ROWS][NUM_COLS];
		for (int r = 0; r < NUM_ROWS; ++r) {
			for (int c = 0; c < NUM_COLS; ++c) {
				grid[r][c] = new Location();
			}
		}
	}

	public void addShip(Ship s) {
		int r = s.getRow();
		int c = s.getCol();

		for (int i = 0; i < s.getLength(); ++i) {
			if ((s.getDirection() == 1 && r + i > NUM_ROWS) || (s.getDirection() == 0 && c + i > NUM_COLS)) {
				throw new IllegalArgumentException("Ship out of bounds (" + r + ", " + c + ") direction " + s.getDirection() + " length " + s.getLength());
			}

			if (s.getDirection() == 0) {
				grid[r][c + i].setShip(true);
			} else {
				grid[r + i][c].setShip(true);
			}
		}
	}

	// Mark a hit in this location by calling the markHit method
	// on the Location object.
	public void markHit(int row, int col) {
		grid[row][col].markHit();
	}

	// Mark a miss on this location.
	public void markMiss(int row, int col) {
		grid[row][col].markMiss();
	}

	// Set the status of this location object.
	public void setStatus(int row, int col, int status) {
		grid[row][col].setStatus(status);
	}

	// Get the status of this location in the grid
	public int getStatus(int row, int col) {
		return grid[row][col].getStatus();
	}

	// Return whether or not this Location has already been guessed.
	public boolean alreadyGuessed(int row, int col) {
		return !grid[row][col].isUnguessed();
	}

	// Set whether or not there is a ship at this location to the val
	public void setShip(int row, int col, boolean val) {
		grid[row][col].setShip(val);
	}

	// Return whether or not there is a ship here
	public boolean hasShip(int row, int col) {
		return grid[row][col].hasShip();
	}


	// Get the Location object at this row and column position
	public Location get(int row, int col) {
		return grid[row][col];
	}

	// Return the number of rows in the Grid
	public int numRows() {
		return NUM_ROWS;
	}

	// Return the number of columns in the grid
	public int numCols() {
		return NUM_COLS;
	}


	// Print the Grid status including a header at the top
	// that shows the columns 1-10 as well as letters across
	// the side for A-J
	// If there is no guess print a -
	// If it was a miss print a O
	// If it was a hit, print an X
	// A sample print out would look something like this:
	//
	//   1 2 3 4 5 6 7 8 9 10
	// A - - - - - - - - - -
	// B - - - - - - - - - -
	// C - - - O - - - - - -
	// D - O - - - - - - - -
	// E - X - - - - - - - -
	// F - X - - - - - - - -
	// G - X - - - - - - - -
	// H - O - - - - - - - -
	// I - - - - - - - - - -
	// J - - - - - - - - - -
	public void printStatus() {
		System.out.print("  ");
		for (int c = 0; c < NUM_COLS; ++c) {
			System.out.print(c + 1);
			if (c != NUM_COLS - 1) {
				System.out.print(' ');
			}
		}
		System.out.print('\n');

		for (int r = 0; r < NUM_ROWS; ++r) {
			System.out.print((char) ('A' + r) + " ");
			for (int c = 0; c < NUM_COLS; ++c) {
				System.out.print(statusAsChar(grid[r][c].getStatus()) + " ");
			}
			System.out.print('\n');
		}
	}

	// Print the grid and whether there is a ship at each location.
	// If there is no ship, you will print a - and if there is a
	// ship you will print a X. You can find out if there was a ship
	// by calling the hasShip method.
	//
	//   1 2 3 4 5 6 7 8 9 10
	// A - - - - - - - - - -
	// B - X - - - - - - - -
	// C - X - - - - - - - -
	// D - - - - - - - - - -
	// E X X X - - - - - - -
	// F - - - - - - - - - -
	// G - - - - - - - - - -
	// H - - - X X X X - X -
	// I - - - - - - - - X -
	// J - - - - - - - - X -
	public void printShips() {
		System.out.print("  ");
		for (int c = 0; c < NUM_COLS; ++c) {
			System.out.print(c + 1);
			if (c != NUM_COLS - 1) {
				System.out.print(' ');
			}
		}
		System.out.print('\n');

		for (int r = 0; r < NUM_ROWS; ++r) {
			System.out.print((char) ('A' + r) + " ");
			for (int c = 0; c < NUM_COLS; ++c) {
				System.out.print(((grid[r][c].hasShip()) ? 'X' : '-') + " ");
			}
			System.out.print('\n');
		}
	}

	private static char statusAsChar(int status) {
		switch (status) {
			case Location.UNGUESSED: return '-';
			case Location.HIT: return 'X';
			case Location.MISSED: return 'O';
		}
		return ' ';
	}
}