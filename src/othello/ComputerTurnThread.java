package othello;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

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
			possibleMoves = logicBoard.findPossibleMoves(2);
			boolean avilMoves = hasMoves(possibleMoves);
			if (avilMoves == false) {
				// select random option of possible moves
				int numOfChoices = possibleMoves.size();
				Random rand = new Random();
				int randomNum = rand.nextInt(numOfChoices);
				String computerMove = possibleMoves.get(randomNum);
				logicBoard.takeTurn(2, computerMove);
				gamePieces();
				switchPlayers(2);
				// check for winner
				Integer winner = logicBoard.isWinner();
				if (winner != null) {
					displayWinnerDialog(winner);
					return;
				}
				possibleMoves = logicBoard.findPossibleMoves(1);
				avilMoves = hasMoves(possibleMoves);
				if (avilMoves == true) {
					displayHints(possibleMoves);
				} else {
					JOptionPane pane = new JOptionPane("You have no valid moves. Pass.", JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = pane.createDialog(null, "Pass Turn");
					dialog.setModal(false);
					dialog.setVisible(true);

					int time_visible = 800;
					new Timer(time_visible, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							dialog.setVisible(false);
						}
					}).start();
					
					switchPlayers(1);
					possibleMoves = logicBoard.findPossibleMoves(2);
					avilMoves = hasMoves(possibleMoves);
					if (avilMoves == true) {
						displayHints(possibleMoves);
						run();
					} else {
						winner = logicBoard.isWinner();
						displayWinnerDialog(winner);
						return;

					}
				}

			}
		} catch (

		InterruptedException e)

		{
			JOptionPane.showMessageDialog(null, "Error. Please restart Game");
		}

	}

	public boolean hasMoves(ArrayList<String> possibleMoves) {
		for (String move : possibleMoves) {
			int column = Integer.parseInt(String.valueOf(move.charAt(0)));
			int row = Integer.parseInt(String.valueOf(move.charAt(1)));
			if (gameBoard[column][row].getIcon() == empty) {
				return true;
			}
		}
		return false;
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
			message = "You tied the computer! Great Job!";
		} else if (winner == 1) {
			message = "You won! Great Job!";
		} else if (winner == 2) {
			message = "Computer won! Better luck next time!";
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
