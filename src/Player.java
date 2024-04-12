public class Player
{
	// These are the lengths of all of the ships.
	public static final int[] SHIP_LENGTHS = {2, 3, 3, 4, 5};
	public static final int NUM_OF_SHIPS = 5;

	private int currentCountShips = 0;

	public Ship[] ships;
	public Grid playerGrid;
	public Grid oppGrid;

	public Player() {
		ships = new Ship[NUM_OF_SHIPS];
		for (int i = 0; i < NUM_OF_SHIPS; i++) {
			Ship tempShip = new Ship(SHIP_LENGTHS[i]);
			ships[i] = tempShip;
		}

		playerGrid = new Grid();
		oppGrid = new Grid();
	}

	public void chooseShipLocation(Ship s, int row, int col, int direction) {
		if (currentCountShips >= NUM_OF_SHIPS) {
			return;
		}

		s.setLocation(row, col);
		s.setDirection(direction);
		playerGrid.addShip(s);

		++currentCountShips;
	}

	public void printMyShips() {
		playerGrid.printShips();
	}

	public void printOpponentGuesses() {
		System.out.println("badabdaduwadaidwiauhd");
		for (int c = 0; c < 10; ++c) {
			System.out.print(c + 1);
			if (c != 10 - 1) {
				System.out.print(' ');
			}
		}
		System.out.print('\n');

		for (int r = 0; r < 10; ++r) {
			System.out.print((char) ('A' + r) + " ");
			for (int c = 0; c < 10; ++c) {
				System.out.print(((playerGrid.getStatus(r, c) != Location.UNGUESSED) ? 'X' : '-') + " ");
			}
			System.out.print('\n');
		}
	}

	public void printMyGuesses() {
		for (int c = 0; c < 10; ++c) {
			System.out.print(c + 1);
			if (c != 10 - 1) {
				System.out.print(' ');
			}
		}
		System.out.print('\n');

		for (int r = 0; r < 10; ++r) {
			System.out.print((char) ('A' + r) + " ");
			for (int c = 0; c < 10; ++c) {
				System.out.print(((oppGrid.getStatus(r, c) != Location.UNGUESSED) ? 'X' : '-') + " ");
			}
			System.out.print('\n');
		}
	}

	public boolean recordOpponentGuess(int row, int col) {
		if (playerGrid.hasShip(row, col)) {
			playerGrid.markHit(row, col);
			return true;
		}

		playerGrid.markMiss(row, col);
		return false;
	}
}