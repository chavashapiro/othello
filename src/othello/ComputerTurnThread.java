package othello;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ComputerTurnThread extends Thread {

	private BoardGame logicBoard;
	int player = 2;
	private JButton[][] gameBoard;
	private ImageIcon black;
	private ImageIcon white;
	private ImageIcon empty;
	private ImageIcon hintSpot;
	private JLabel whitePoints;
	private JLabel blackPoints;
	private JLabel playersTurn;
	private int whiteScore;
	private int blackScore;
	
	private ArrayList<String> possibleMoves;

	public ComputerTurnThread(BoardGame logicBoard, JButton[][] board, JLabel whitePoints, JLabel blackPoints,
			int whiteScore, int blackScore, JLabel playersTurn) {
		this.logicBoard = logicBoard;
		this.gameBoard = board;
		this.playersTurn = playersTurn;
		this.whiteScore = whiteScore;
		this.blackScore = blackScore;
		this.whitePoints = new JLabel(String.valueOf(this.whiteScore));
		this.blackPoints = new JLabel(String.valueOf(this.whiteScore));

		black = new ImageIcon("othelloBlack.png");
		white = new ImageIcon("othelloWhite.png");
		empty = new ImageIcon("othelloEmpty.PNG");
		hintSpot = new ImageIcon("othelloMove.PNG");

	}

	public void run() {

		// delay
		try {
			Thread.sleep(1000);

			// get possible moves
			possibleMoves = logicBoard.findPossibleMoves(player);

			if (possibleMoves.size() != 0) {
				// select random option of possible moves
				int numOfChoices = possibleMoves.size();
				Random rand = new Random();
				int randomNum = rand.nextInt(numOfChoices);
				String computerMove = possibleMoves.get(randomNum);

				logicBoard.takeTurn(player, computerMove);

				gamePieces();
				//check for winner
				Integer winner = logicBoard.isWinner();
				if (winner != null) {
					displayWinnerDialog(winner);
				}
				else{
				// switchPlayers
				switchPlayers(player);
				// display hints for user
				possibleMoves = logicBoard.findPossibleMoves(player);

				// if no valid moves for next player
				if (possibleMoves.size() == 0) {
					JOptionPane.showMessageDialog(null, "You have no valid moves. Pass");
					// switch players
					switchPlayers(player);
					// set hints for computer
					possibleMoves = logicBoard.findPossibleMoves(player);
					// if no valid moves for computer - game over- no
					// players have moves - display game winner
					if (possibleMoves.size() == 0) {
						// check for winner
						winner = logicBoard.isWinner();
						displayWinnerDialog(winner);
					} else {
						run();
					}
				}
				// display valid moves for next player
				else {
					displayHints(possibleMoves);
				}
				}
			}
			//computer has no possible moves
			else{
				//check if game is over
				Integer winner = logicBoard.isWinner();
				if (winner != null){
					displayWinnerDialog(winner);
				}
				else{
				JOptionPane.showMessageDialog(null, "Computer has no legal moves. Pass.");
				switchPlayers(player);
				possibleMoves = logicBoard.findPossibleMoves(player);
				displayHints(possibleMoves);
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void switchPlayers(int playerNum) {
		this.player = playerNum;
		if (player == 1) {
			player = 2;
			playersTurn.setText("Black Player's Turn");
		} else {
			player = 1;
			playersTurn.setText("White Player's Turn");
		}
	}

	public void displayWinnerDialog(int winner) {
		String message;
		if (winner == 0) {
			message = "Players tied! Great Job!";
		} else if (winner == 1) {
			message = "White player won! Great Job!";
		} else if (winner == 2) {
			message = "Black player won! Great Job!";
		} else {
			message = "Error.  Please replay game!";
		}
		JOptionPane.showMessageDialog(null, message);
	}

	public void displayHints(ArrayList<String> possibleMoves) {
		for (String hint : possibleMoves) {
			int column = Integer.parseInt(String.valueOf(hint.charAt(0)));
			int row = Integer.parseInt(String.valueOf(hint.charAt(1)));
			if (gameBoard[column][row].getIcon() == empty) {
				gameBoard[column][row].setIcon(hintSpot);
			}
		}
	}

	public void gamePieces() {
		whiteScore = 0;
		blackScore = 0;
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				if (logicBoard.getBoard()[row][column] == 0) {
					gameBoard[row][column].setIcon(empty);
				} else if (logicBoard.getBoard()[row][column] == 1) {
					gameBoard[row][column].setIcon(white);
					whiteScore++;
				} else {
					gameBoard[row][column].setIcon(black);
					blackScore++;
				}
			}
		}
		whitePoints.setText(String.valueOf(whiteScore));
		blackPoints.setText(String.valueOf(blackScore));
	}

}
