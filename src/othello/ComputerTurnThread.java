package othello;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ComputerTurnThread extends Thread{
	
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
	
	public ComputerTurnThread(BoardGame logicBoard, JButton[][] board, JLabel whitePoints, JLabel blackPoints, JLabel playersTurn){
		this.logicBoard = logicBoard;
		this.gameBoard = board;
		this.playersTurn = playersTurn;

		black = new ImageIcon("othelloBlack.png");
		white = new ImageIcon("othelloWhite.png");
		empty = new ImageIcon("othelloEmpty.PNG");
		hintSpot = new ImageIcon("othelloMove.PNG");
		
	}
	public void run(){
		try {
			Thread.sleep(1000);
			// need to make delay for computer's turn
			ArrayList<String> possibleMoves = logicBoard.findPossibleMoves(player);
			int numOfChoices = possibleMoves.size();
			Random rand = new Random();
			int randomNum = rand.nextInt(numOfChoices);
			String computerMove = possibleMoves.get(randomNum);
			System.out.println(randomNum);
			logicBoard.takeTurn(player, computerMove);
			gamePieces();
			if (player == 1) {
				player = 2;
			} else {
				player = 1;
			}
		} catch (NoMovesException e) {
			System.out.println("no moves");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void gamePieces() {
		int whiteScore = 0;
		int blackScore = 0;
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
		//whitePoints.setText(String.valueOf(whiteScore));
		//blackPoints.setText(String.valueOf(blackScore));

		
			if (player == 1) {
				player = 2;
				playersTurn.setText("Black Player's Turn");
			} else {
				player = 1;
				playersTurn.setText("White Player's Turn");
			}
			try {
			ArrayList<String> possibleMoves = logicBoard.findPossibleMoves(player);
			for (String hint : possibleMoves) {
				int column = Integer.parseInt(String.valueOf(hint.charAt(0)));
				int row = Integer.parseInt(String.valueOf(hint.charAt(1)));
				if (gameBoard[column][row].getIcon() == empty) {
					gameBoard[column][row].setIcon(hintSpot);
				}
			}
		} catch (NoMovesException e) {
			System.out.println("game over");
		}

	}


}
