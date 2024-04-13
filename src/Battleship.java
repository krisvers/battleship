import java.util.Scanner;
import java.util.Random;

/*
test input:
A
4
H
B
7
V
C
2
H
D
6
V
E
1
V

 */

public class Battleship extends ConsoleProgram
{
	private boolean debug = true;

	public static boolean isValidLocation(Player player, int row, int column, int direction, int length) {
		if (row < 0 || row >= 10 || column < 0 || column >= 10) {
			return false;
		}
		if (direction != 0 && direction != 1) {
			return false;
		}

		int value = column;
		if (direction == 1) {
			value = row;
		}

		int i = value;
		value += length;
		for (; i < value; ++i) {
			if (i >= 10) {
				return false;
			}

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

	public static boolean isValidLocation(Player player, int row, int column, int direction) {
		return isValidLocation(player, row, column, direction, 1);
	}

	public void run()
	{
		Random random = new Random();
		Player[] players = new Player[2];
		PlayerCPU cpu = new PlayerCPU();

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
		cpu.setupShips(players[player], random);

		if (debug) {
			System.out.println("Opponent ships:");
			players[1].printMyShips();
		}

		while (true) {
			player = 0;
			System.out.println("Player " + (player + 1) + "'s turn");

			if (debug) {
				System.out.println("Opponent's board:");
				players[player].printOpponentGuesses();

				System.out.println("Opponent's ships:");
				players[(player + 1) % 2].printMyShips();
			}

			System.out.println("Your board:");
			players[player].printMyShips();

			System.out.println("Enter a row and column to attack");
			int row = scanner.next().charAt(0) - 'A';
			if (row < 0 || row >= 10) {
				System.out.println("Invalid row");
				continue;
			}

			int col = scanner.nextInt() - 1;
			if (col < 0 || col >= 10) {
				System.out.println("Invalid column");
				continue;
			}

			if (players[(player + 1) % 2].playerGrid.alreadyGuessed(row, col)) {
				System.out.println("You have already guessed this location");
				continue;
			}

			boolean hit = players[(player + 1) % 2].playerGrid.hasShip(row, col);
			if (hit) {
				System.out.println("Hit!");
				players[(player + 1) % 2].playerGrid.markHit(row, col);
				players[player].oppGrid.markHit(row, col);
			} else {
				System.out.println("Miss!");
				players[(player + 1) % 2].playerGrid.markMiss(row, col);
				players[player].oppGrid.markMiss(row, col);
			}

			if (players[(player + 1) % 2].playerGrid.allSunk()) {
				System.out.println("Player " + (player + 1) + " wins!");
				break;
			}

			System.out.println("Your guesses:");
			players[player].printMyGuesses();

			System.out.println("Press enter to continue");
			scanner.nextLine();
			scanner.nextLine();

			// CPU turn
			player = 1;
			System.out.println("Player " + (player + 1) + "'s turn");

			cpu.cpuGuess(players[player], players[(player + 1) % 2], random);

			System.out.println("Opponents guesses:");
			players[player].printMyGuesses();

			if (players[(player + 1) % 2].playerGrid.allSunk()) {
				System.out.println("Player " + (player + 1) + " wins!");
				break;
			}

			System.out.println("Press enter to continue");
			scanner.nextLine();
			scanner.nextLine();
		}
	}
}