import java.util.Scanner;
import java.util.Random;

public class Battleship
{
	public boolean isValidLocation(Player player, int row, int column, int direction, int length) {
		int value = column;
		if (direction == 1) {
			value = row;
		}

		int i = value;
		value += length;
		for (; i < value; ++i) {
			if (direction == 0) {
				if (player.playerGrid.hasShip(row, i)) {
					return false;
				}
			} else {
				if (player.playerGrid.hasShip(i, column)) {
					return false;
				}
			}
		}

		return true;
	}

	public void run()
	{
		Random random = new Random();
		Player[] players = new Player[2];

		int player = 0;

		Scanner scanner = new Scanner(System.in);
		players[player] = new Player();
		players[player].printMyShips();

		for (int i = 0; i < Player.NUM_OF_SHIPS; ++i) {
			Ship s = new Ship(Player.SHIP_LENGTHS[i]);
			System.out.println("Enter on 3 different lines (row, column) and direction for ship of length " + s.getLength() + "\nFor example: B 5 V\n");
			int row = scanner.next().charAt(0) - 'A';
			if (row < 0 || row >= 10) {
				System.out.println("Invalid row");
				--i;
				continue;
			}

			int col = scanner.nextInt() - 1;
			if (col < 0 || col >= 10) {
				System.out.println("Invalid column");
				--i;
				continue;
			}

			char directionChar = scanner.next().charAt(0);
			int direction = -1;
			if (directionChar == 'H') {
				direction = 0;
			} else if (directionChar == 'V') {
				direction = 1;
			}

			if (direction != 0 && direction != 1) {
				System.out.println("Invalid direction");
				--i;
				continue;
			}

			if (direction == 0) {
				if (col + s.getLength() >= 10) {
					System.out.println("Ship out of bounds");
					--i;
					continue;
				}
			} else {
				if (row + s.getLength() >= 10) {
					System.out.println("Ship out of bounds");
					--i;
					continue;
				}
			}

			if (!isValidLocation(players[player], row, col, direction, s.getLength())) {
				System.out.println("Invalid location");
				continue;
			}

			s.setLocation(row, col);
			s.setDirection(direction);
			players[player].chooseShipLocation(s, s.getRow(), s.getCol(), s.getDirection());

			System.out.println("Your ships:");
			players[player].printMyShips();
		}

		++player;
		players[player] = new Player();
		for (int i = 0; i < Player.SHIP_LENGTHS.length; ++i) {
			Ship s = new Ship(Player.SHIP_LENGTHS[i]);

			int row = 0;
			int col = 0;
			int direction = 0;

			while (!isValidLocation(players[player], row, col, direction, s.getLength())) {
				row = Math.abs(random.nextInt()) % 10;
				col = Math.abs(random.nextInt()) % 10;
				direction = Math.abs(random.nextInt()) % 2;
			}

			s.setLocation(row, col);
			s.setDirection(direction);

			System.out.println("Opponent placed ship of length " + s.getLength() + " at (" + s.getRow() + ", " + s.getCol() + ") and direction " + s.getDirection() + ".");
			players[player].chooseShipLocation(s, s.getRow(), s.getCol(), s.getDirection());
		}

		System.out.println("Opponent's ships:");
		players[player].printMyShips();

		while (true) {


			player = (player + 1) % 2;
		}
	}
}