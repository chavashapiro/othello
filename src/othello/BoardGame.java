package othello;

import java.util.ArrayList;

public class BoardGame {

	private int[][] board;
	private int white;
	private int black;

	public BoardGame() {
		board = new int[8][8];
		white = 1;
		black = 2;
		board[3][3] = white;
		board[3][4] = black;
		board[4][4] = white;
		board[4][3] = black;
	}

	public int[][] getBoard() {
		return board;
	}

	public ArrayList<String> findPossibleMoves(int currentPlayer)
			throws NoMovesException {
		ArrayList<String> moves = new ArrayList<String>();
		int row, column;
		String move;

		int otherPlayer;
		if (currentPlayer == black) {
			otherPlayer = white;
		} else {
			otherPlayer = black;
		}

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == currentPlayer) {
					// test up
					row = i;
					column = j;
					while (row - 1 >= 0
							&& board[row - 1][column] == otherPlayer) {
						row--;
					}
					if (row - 1 >= 0) {
						move = String.valueOf(row - 1) + String.valueOf(column);
						moves.add(move);
					}

					// test down
					row = i;
					column = j;
					while (row + 1 < board.length
							&& board[row + 1][column] == otherPlayer) {
						row++;
					}
					if (row + 1 < board.length) {
						move = String.valueOf(row + 1) + String.valueOf(column);
						moves.add(move);
					}

					// test left
					row = i;
					column = j;
					while (column - 1 >= 0
							&& board[row][column - 1] == otherPlayer) {
						column--;
					}
					if (column - 1 >= 0) {
						move = String.valueOf(row) + String.valueOf(column - 1);
						moves.add(move);
					}

					// test right
					row = i;
					column = j;
					while (column + 1 < board[0].length
							&& board[row][column + 1] == otherPlayer) {
						column++;
					}
					if (column + 1 < board[0].length) {
						move = String.valueOf(row) + String.valueOf(column + 1);
						moves.add(move);
					}

					// test diagonal up and left
					row = i;
					column = j;
					while (row - 1 >= 0 && column - 1 >= 0
							&& board[row - 1][column - 1] == otherPlayer) {
						row--;
						column--;
					}
					if (row - 1 >= 0 && column - 1 >= 0) {
						move = String.valueOf(row - 1)
								+ String.valueOf(column - 1);
						moves.add(move);
					}

					// test diagonal down and left
					row = i;
					column = j;
					while (row + 1 < board.length && column - 1 >= 0
							&& board[row + 1][column - 1] == otherPlayer) {
						row++;
						column--;
					}
					if (row + 1 < board.length && column - 1 >= 0) {
						move = String.valueOf(row + 1)
								+ String.valueOf(column - 1);
						moves.add(move);
					}

					// test diagonal up and right
					row = i;
					column = j;
					while (row - 1 >= 0 && column + 1 < board[0].length
							&& board[row - 1][column + 1] == otherPlayer) {
						row--;
						column++;
					}
					if (row - 1 >= 0 && column + 1 < board[0].length) {
						move = String.valueOf(row - 1)
								+ String.valueOf(column + 1);
						moves.add(move);
					}

					// test diagonal down and right
					row = i;
					column = j;
					while (row + 1 < board.length
							&& column + 1 < board[0].length
							&& board[row + 1][column + 1] == otherPlayer) {
						row++;
						column++;
					}
					if (row + 1 < board.length && column + 1 < board[0].length) {
						move = String.valueOf(row + 1)
								+ String.valueOf(column + 1);
						moves.add(move);
					}
				}
			}
		}

		if (moves.size() == 0) {
			throw new NoMovesException();
		}
		return moves;
	}

	public void takeTurn(int currentPlayer, String move) {
		ArrayList<String> spaces = new ArrayList<String>();
		int otherPlayer, row, column;

		if (currentPlayer == black) {
			otherPlayer = white;
		} else {
			otherPlayer = black;
		}

		String[] moveTokens = move.split("");
		int moveRow = Integer.parseInt(moveTokens[0]);
		int moveColumn = Integer.parseInt(moveTokens[1]);

		// up
		row = moveRow;
		column = moveColumn;

		if (row - 1 > 0) {
			while (board[row - 1][column] == otherPlayer) {
				row--;
			}
		}
		if (row - 1 >= 0 && board[row - 1][column] == currentPlayer) {
			while (row < moveRow) {
				spaces.add(String.valueOf(row) + String.valueOf(column));
				row++;
			}
		}

		// down
		row = moveRow;
		column = moveColumn;
		if (row + 1 < board.length - 1) {
			while (board[row + 1][column] == otherPlayer) {
				row++;
			}
		}
		if (row + 1 < board.length && board[row + 1][column] == currentPlayer) {
			while (row > moveRow) {
				spaces.add(String.valueOf(row) + String.valueOf(column));
				row--;
			}
		}

		// left
		row = moveRow;
		column = moveColumn;
		if (column - 1 > 0) {
			while (board[row][column - 1] == otherPlayer) {
				column--;
			}
		}
		if (column - 1 >= 0 && board[row][column - 1] == currentPlayer) {
			while (column < moveColumn) {
				spaces.add(String.valueOf(row) + String.valueOf(column));
				column++;
			}
		}

		// right
		row = moveRow;
		column = moveColumn;
		if (column + 1 < board[0].length - 1) {
			while (board[row][column + 1] == otherPlayer) {
				column++;
			}
		}
		if (column + 1 < board[0].length
				&& board[row][column + 1] == currentPlayer) {
			while (column > moveColumn) {
				spaces.add(String.valueOf(row) + String.valueOf(column));
				column--;
			}
		}

		// up and left
		row = moveRow;
		column = moveColumn;
		if (row - 1 > 0 && column - 1 > 0) {
			while (board[row - 1][column - 1] == otherPlayer) {
				row--;
				column--;
			}
		}
		if (row - 1 >= 0 && column - 1 >= 0
				&& board[row - 1][column - 1] == currentPlayer) {
			while (row < moveRow) {
				spaces.add(String.valueOf(row) + String.valueOf(column));
				row++;
				column++;
			}
		}

		// down and left
		row = moveRow;
		column = moveColumn;
		if (row + 1 < board.length - 1 && column - 1 > 0) {
			while (board[row + 1][column - 1] == otherPlayer) {
				row++;
				column--;
			}
		}
		if (row + 1 < board.length && column - 1 >= 0
				&& board[row + 1][column - 1] == currentPlayer) {
			while (row > moveRow) {
				spaces.add(String.valueOf(row) + String.valueOf(column));
				row--;
				column++;
			}
		}

		// up and right
		row = moveRow;
		column = moveColumn;
		if (row - 1 > 0 && column + 1 < board[0].length - 1) {
			while (board[row - 1][column + 1] == otherPlayer) {
				row--;
				column++;
			}
		}
		if (row - 1 > 0 && column + 1 < board[0].length - 1
				&& board[row - 1][column + 1] == currentPlayer) {
			while (row < moveRow) {
				spaces.add(String.valueOf(row) + String.valueOf(column));
				row++;
				column--;
			}
		}

		// down and right
		row = moveRow;
		column = moveColumn;
		if (row + 1 < board.length - 1 && column + 1 < board[0].length - 1) {
			while (board[row + 1][column + 1] == otherPlayer) {
				row++;
				column++;
			}
		}
		if (row + 1 < board.length && column + 1 <= board[0].length - 1
				&& board[row + 1][column + 1] == currentPlayer) {
			while (row > moveRow) {
				spaces.add(String.valueOf(row) + String.valueOf(column));
				row--;
				column--;
			}
		}

		// if spaces size is not 0 meaning there are pieces to be flipped, flip
		// them and change the piece the player clicked
		if (spaces.size() != 0) {
			// switch all spaces in the array to currentPlayer's color
			for (String space : spaces) {
				String[] spaceTokens = space.split("");
				row = Integer.parseInt(spaceTokens[0]);
				column = Integer.parseInt(spaceTokens[1]);
				board[row][column] = currentPlayer;
			}
			// change the space the player clicked to currentPlayer's color
			board[moveRow][moveColumn] = currentPlayer;
		}
		// return spaces;
	}

}
