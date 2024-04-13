import java.util.Random;

public class PlayerCPU {
	int guessNumber = -1;
	int lastRow = 0;
	int lastCol = 0;
	int lastDirection = 0;

	boolean foundShip = false;
	boolean prevFoundShip = false;

	public int chooseRowInit() {
		return Math.abs(((-(guessNumber - 1) / 2) + (guessNumber % 2) * 5) % 10);
	}

	public void setupShips(Player cpu, Random random) {
		for(int i = 0; i < Player.SHIP_LENGTHS.length; ++i) {
			Ship s = new Ship(Player.SHIP_LENGTHS[i]);

			int row = -1;
			int col = -1;
			int direction = -1;

			while (!Battleship.isValidLocation(cpu, row, col, direction, s.getLength())) {
				row = Math.abs(random.nextInt()) % 10;
				col = Math.abs(random.nextInt()) % 10;
				direction = Math.abs(random.nextInt()) % 2;
			}

			s.setLocation(row, col);
			s.setDirection(direction);

			cpu.chooseShipLocation(s, s.getRow(), s.getCol(), s.getDirection());
		}
	}

	public void cpuGuess(Player cpu, Player player, Random random) {
		if (!foundShip && !prevFoundShip) {
			int row = chooseRowInit();
			int col = guessNumber / 2;
			player.recordOpponentGuess(row, col);
			if (player.playerGrid.hasShip(row, col)) {
				foundShip = true;
				prevFoundShip = true;
				lastRow = row;
				lastCol = col;
				lastDirection = -1;
				player.playerGrid.markHit(row, col);
				cpu.oppGrid.markHit(row, col);
			} else {
				lastRow = row;
				lastCol = col;
				lastDirection = -1;
				prevFoundShip = false;
				player.playerGrid.markMiss(row, col);
				cpu.oppGrid.markMiss(row, col);
			}
			guessNumber = (guessNumber + 1) % 20;
		} else if (prevFoundShip) {
			int row = lastRow;
			int col = lastCol;
			int direction = lastDirection;
			if (direction == -1) {
				direction = random.nextInt(2);
			}

			if (direction == 0) {
				while (true) {
					if (random.nextBoolean()) {
						++col;
					} else {
						--col;
					}

					col %= 10;
					if (col < 0) {
						col *= -1;
					}

					if (Battleship.isValidLocation(cpu, row, col, direction) && !cpu.oppGrid.alreadyGuessed(row, col)) {
						break;
					}
				}
			} else {
				while (true) {
					if (random.nextBoolean()) {
						++row;
					} else {
						--row;
					}

					row %= 10;
					if (row < 0) {
						row *= -1;
					}

					if (Battleship.isValidLocation(cpu, row, col, direction) && !cpu.oppGrid.alreadyGuessed(row, col)) {
						break;
					}
				}
			}

			lastRow = row;
			lastCol = col;
			lastDirection = direction;
			player.recordOpponentGuess(row, col);
			if (player.playerGrid.hasShip(row, col)) {
				player.playerGrid.markHit(row, col);
				prevFoundShip = foundShip;
				foundShip = true;
				cpu.oppGrid.markHit(row, col);
			} else {
				player.playerGrid.markMiss(row, col);
				cpu.oppGrid.markMiss(row, col);
				prevFoundShip = foundShip;
				foundShip = false;
				lastDirection = 1 - direction;
			}
		}
		guessNumber = (guessNumber + 1) % 20;
	}
}
