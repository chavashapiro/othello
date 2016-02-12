package othello;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class BoardGui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton[][] gameBoard;
	private ImageIcon black;
	private ImageIcon white;
	private ImageIcon empty;
	private ImageIcon hintSpot;

	private JPanel completePanel;
	private JPanel sideBar;
	private JPanel board;
	private JPanel scores;
	private JPanel centerSideBar;

	private BoardGame logicBoard;
	private int player;
	private int blackScore;
	private int whiteScore;
	private JLabel whitePoints;
	private JLabel blackPoints;

	private JLabel playersTurn;

	private JButton againstCom;
	private boolean againstComputer;
	private JButton restart;

	public BoardGui() {
		setTitle("Othello");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		completePanel = new JPanel();
		completePanel.setLayout(new BorderLayout());

		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());

		board = new JPanel();
		board.setLayout(new GridLayout(8, 8));

		centerSideBar = new JPanel();
		centerSideBar.setLayout(new BorderLayout());
		centerSideBar.setBackground(Color.BLACK);

		scores = new JPanel();
		scores.setLayout(new FlowLayout());
		scores.setBackground(Color.BLACK);

		gameBoard = new JButton[8][8];
		black = new ImageIcon("othelloBlack.png");
		white = new ImageIcon("othelloWhite.png");
		empty = new ImageIcon("othelloEmpty.PNG");
		hintSpot = new ImageIcon("othelloMove.PNG");
		blackScore = 0;
		whiteScore = 0;

		sideBar = new JPanel();
		sideBar.setLayout(new BorderLayout());

		restart = new JButton("Restart");
		restart.setAlignmentX(Component.CENTER_ALIGNMENT);
		restart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				restartGame();
			}
		});

		againstComputer = false;
		againstCom = new JButton("Play Against Computer");
		againstCom.setAlignmentX(Component.CENTER_ALIGNMENT);
		againstCom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				againstComputer = true;

			}

		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(againstCom);
		buttonPanel.setBackground(Color.BLACK);

		JPanel buttonPanelRestart = new JPanel();
		buttonPanelRestart.add(restart);
		buttonPanelRestart.setBackground(Color.BLACK);

		JLabel othello = new JLabel(" OTHELLO ");
		othello.setForeground(Color.WHITE);
		othello.setFont(new Font(othello.getFont().getName(), othello.getFont().getStyle(), 48));

		String text = "\n Object of the game is to get the majority of the color disks at the end of "
				+ "the game. \n To Play: Click on one of the boxes to place disk. At least one disk "
				+ "must flip each turn.  If there are no legal moves player takes a pass.  \n End of "
				+ "game: All the places are filled or there are no more legal moves. \n";
		JTextArea info = new JTextArea(text);
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		info.setFont(new Font(info.getFont().getName(), info.getFont().getStyle(), 16));
		info.setForeground(Color.WHITE);
		info.setBackground(Color.BLACK);

		Border border = BorderFactory.createLineBorder(Color.BLACK);
		info.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JLabel whitePiece = new JLabel(white);
		whitePoints = new JLabel(String.valueOf(whiteScore));
		whitePoints.setForeground(Color.BLACK);
		whitePoints.setFont(new Font(whitePoints.getFont().getName(), whitePoints.getFont().getStyle(), 24));
		whitePoints.setHorizontalAlignment(JLabel.CENTER);

		whitePiece.setLayout(new BorderLayout());
		whitePiece.add(whitePoints);

		JLabel blackPiece = new JLabel(black);
		blackPoints = new JLabel(String.valueOf(blackScore));
		blackPoints.setForeground(Color.WHITE);
		blackPoints.setFont(new Font(blackPoints.getFont().getName(), blackPoints.getFont().getStyle(), 24));
		blackPoints.setHorizontalAlignment(JLabel.CENTER);

		blackPiece.setLayout(new BorderLayout());
		blackPiece.add(blackPoints);

		JPanel blackBox = new JPanel();
		blackBox.setBackground(Color.BLACK);
		blackBox.setLayout(new BoxLayout(blackBox, BoxLayout.Y_AXIS));
		blackBox.add(blackPiece);

		JPanel whiteBox = new JPanel();
		whiteBox.setBackground(Color.BLACK);
		whiteBox.setLayout(new BoxLayout(whiteBox, BoxLayout.Y_AXIS));
		whiteBox.add(whitePiece);

		playersTurn = new JLabel("White Player's Turn");
		playersTurn.setForeground(Color.WHITE);
		playersTurn.setFont(new Font(playersTurn.getFont().getName(), playersTurn.getFont().getStyle(), 18));

		JPanel turnPlayer = new JPanel();
		turnPlayer.add(playersTurn);
		turnPlayer.setBackground(Color.BLACK);

		scores.add(whiteBox);
		scores.add(blackBox);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.setBackground(Color.BLACK);
		buttons.add(againstCom);
		buttons.add(Box.createRigidArea(new Dimension(10,10)));
		buttons.add(restart);

		centerSideBar.add(turnPlayer, BorderLayout.NORTH);
		centerSideBar.add(scores, BorderLayout.CENTER);
		centerSideBar.add(buttons, BorderLayout.SOUTH);

		sideBar.add(othello, BorderLayout.NORTH);
		sideBar.add(info, BorderLayout.SOUTH);
		sideBar.add(centerSideBar, BorderLayout.CENTER);
		sideBar.setBackground(Color.BLACK);

		logicBoard = new BoardGame();
		player = 1;

		// create game, initial setup
		initialSetup();

		for (int i = 0; i < 8; i++) {
			int row = i;
			for (int j = 0; j < 8; j++) {
				int column = j;
				gameBoard[i][j].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						String move = String.valueOf(row) + String.valueOf(column);
						if (logicBoard.getBoard()[row][column] != 1 && logicBoard.getBoard()[row][column] != 2) {
							logicBoard.takeTurn(player, move);
							int tempWhiteScore = whiteScore;
							int tempBlackScore = blackScore;
							gamePieces();
							// if move not valid, give player another turn
							if (tempWhiteScore == whiteScore && tempBlackScore == blackScore) {
								if (player == 1) {
									player = 2;

								} else {
									player = 1;
								}
							}
							if (againstComputer == true) {
								computersTurn();
							}
						}
					}
				});
			}
		}

		completePanel.add(sideBar, BorderLayout.WEST);
		completePanel.add(board, BorderLayout.CENTER);
		getContentPane().add(completePanel);
		pack();
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

		try {
			if (player == 1) {
				player = 2;
				playersTurn.setText("Black Player's Turn");
			} else {
				player = 1;
				playersTurn.setText("White Player's Turn");
			}
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

	public void initialSetup() {
		whiteScore = 0;
		blackScore = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (logicBoard.getBoard()[i][j] == 0) {
					gameBoard[i][j] = new JButton(empty);
					gameBoard[i][j].setPreferredSize(new Dimension(80, 80));
					gameBoard[i][j].setBorder(new LineBorder(Color.BLACK, 1));
					board.add(gameBoard[i][j]);
				} else if (logicBoard.getBoard()[i][j] == 1) {
					gameBoard[i][j] = new JButton(white);
					gameBoard[i][j].setPreferredSize(new Dimension(80, 80));
					gameBoard[i][j].setBorder(new LineBorder(Color.BLACK, 1));
					whiteScore++;
					board.add(gameBoard[i][j]);
				} else {
					gameBoard[i][j] = new JButton(black);
					gameBoard[i][j].setPreferredSize(new Dimension(80, 80));
					gameBoard[i][j].setBorder(new LineBorder(Color.BLACK, 1));
					blackScore++;
					board.add(gameBoard[i][j]);
				}
			}
		}
		whitePoints.setText(String.valueOf(whiteScore));
		blackPoints.setText(String.valueOf(blackScore));

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

	public void computersTurn() {
		try {

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
		}
	}

	public void restartGame() {
		logicBoard = new BoardGame();
		gamePieces();
		player = 1;
	}

	public static void main(String[] args) {
		BoardGui boardGui = new BoardGui();
		boardGui.setVisible(true);
	}

}
