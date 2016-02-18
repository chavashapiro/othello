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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
	private JButton againstComHard;
	private JButton twoPlayers;
	private boolean againstComputer;
	private boolean againstComputerHard;
	private boolean againstPlayer;
	private JButton restart;

	private ArrayList<String> possibleMoves;
	private boolean haveMoves;

	public BoardGui() {

		// SETTING UP BOARD GUI//
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
		againstCom = new JButton("Play Against Computer - EASY");
		againstCom.setAlignmentX(Component.CENTER_ALIGNMENT);
		againstCom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				againstComputer = true;
				againstComputerHard = false;
				againstPlayer = false;
				againstComHard.setEnabled(false);
				twoPlayers.setEnabled(false);

			}

		});

		againstComputerHard = false;
		againstComHard = new JButton("Play Against Computer - HARD");
		againstComHard.setAlignmentX(Component.CENTER_ALIGNMENT);
		againstComHard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				againstComputerHard = true;
				againstComputer = false;
				againstPlayer = false;
				twoPlayers.setEnabled(false);
				againstCom.setEnabled(false);
			}

		});

		againstPlayer = false;
		twoPlayers = new JButton("Play with 2 Plalyers");
		twoPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
		twoPlayers.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				againstPlayer = true;
				againstComputerHard = false;
				againstComputer = false;
				againstCom.setEnabled(false);
				againstComHard.setEnabled(false);
			}
		});

		JPanel buttonPanel = new JPanel();
		// buttonPanel.add(againstCom);
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
		buttons.add(twoPlayers);
		buttons.add(Box.createRigidArea(new Dimension(10, 10)));
		buttons.add(againstCom);
		buttons.add(Box.createRigidArea(new Dimension(10, 10)));
		buttons.add(againstComHard);
		buttons.add(Box.createRigidArea(new Dimension(10, 10)));
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

		// set each spot on board as button
		for (int i = 0; i < 8; i++) {
			int row = i;
			for (int j = 0; j < 8; j++) {
				int column = j;
				gameBoard[i][j].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						if (againstComputer == false && againstComputerHard == false) {

							// get valid moves
							possibleMoves = logicBoard.findPossibleMoves(player);
							String move = String.valueOf(row) + String.valueOf(column);
							// check if spot is valid
							if (logicBoard.getBoard()[row][column] != 1 && logicBoard.getBoard()[row][column] != 2
									&& possibleMoves.contains(move)) {
								// spot is valid so take turn
								logicBoard.takeTurn(player, move);
								// reset gui
								gamePieces();
								// check if there is a winner
								Integer winner = logicBoard.isWinner();
								if (winner != null) {
									displayWinnerDialog(winner);
									return;
								} else {
									// if there is no winner
									// switch players
									switchPlayers(player);
									// set hints for next player
									possibleMoves = logicBoard.findPossibleMoves(player);
									// if no valid moves for next player
									haveMoves = noMoves(possibleMoves);
									if (haveMoves == false) {
										JOptionPane.showMessageDialog(completePanel, "No valid moves. Pass");
										// switch players
										switchPlayers(player);
										// set hints for next player
										possibleMoves = logicBoard.findPossibleMoves(player);
										// displayHints(player);
										// if no valid moves for player - game
										// over-
										// no
										// players have moves - display game
										// winner
										haveMoves = noMoves(possibleMoves);
										if (haveMoves == false) {
											// check for winner
											winner = logicBoard.isWinner();
											displayWinnerDialog(winner);
											return;
										} else {
											displayHints(player);
										}
										displayHints(player);
									}
									// display valid moves for next player
									else {
										displayHints(player);
									}
								}
							}
						} else if (againstComputer == true) {// PLAYING AGAINST
																// COMPUTER -
																// EASY
							// get valid moves display hints

							Integer winners = logicBoard.isWinner();
							if (winners != null) {
								displayWinnerDialog(winners);
								return;
							}
							gamePieces();
							player = 2;
							switchPlayers(player);
							possibleMoves = logicBoard.findPossibleMoves(1);

							displayHints(1);
							String tempmove = String.valueOf(row) + String.valueOf(column);
							// check if spot is valid
							if (logicBoard.getBoard()[row][column] != 1 && logicBoard.getBoard()[row][column] != 2
									&& possibleMoves.contains(tempmove)) {
								// spot is valid so take turn
								logicBoard.takeTurn(1, tempmove);
								// reset gui
								gamePieces();
								switchPlayers(player);
								// check if there is a winner
								winners = logicBoard.isWinner();
								if (winners != null) {
									displayWinnerDialog(winners);
									return;
								}

								// displays computer's hints
								displayHints(2);
								// computer takes turn
								ComputerTurnThread thread = new ComputerTurnThread(logicBoard, gameBoard, whitePoints,
										blackPoints, whiteScore, blackScore, playersTurn);
								thread.start();
								gamePieces();
							}
						} // PLAYING AGAINST COMPUTER - HARD
						else if (againstComputerHard == true) {

							// get valid moves display hints
							Integer winners = logicBoard.isWinner();
							if (winners != null) {
								displayWinnerDialog(winners);
								return;
							}
							gamePieces();
							player = 2;
							switchPlayers(player);
							possibleMoves = logicBoard.findPossibleMoves(1);

							displayHints(1);
							String tempmove = String.valueOf(row) + String.valueOf(column);
							// check if spot is valid
							if (logicBoard.getBoard()[row][column] != 1 && logicBoard.getBoard()[row][column] != 2
									&& possibleMoves.contains(tempmove)) {
								// spot is valid so take turn
								logicBoard.takeTurn(1, tempmove);
								// reset gui
								gamePieces();
								switchPlayers(player);
								// check if there is a winner
								winners = logicBoard.isWinner();
								if (winners != null) {
									displayWinnerDialog(winners);
									return;
								}

								// displays computer's hints
								displayHints(2);
								// computer takes turn
								ComputerHardThread thread = new ComputerHardThread(logicBoard, gameBoard, whitePoints,
										blackPoints, whiteScore, blackScore, playersTurn);
								thread.start();
							}
						}
						// spot is not valid - TAKE NO ACTION
					}
				});
			}
		}

		completePanel.add(sideBar, BorderLayout.WEST);
		completePanel.add(board, BorderLayout.CENTER);

		getContentPane().add(completePanel);
		pack();
	}

	public boolean noMoves(ArrayList<String> possibleMoves) {
		for (String move : possibleMoves) {
			int column = Integer.parseInt(String.valueOf(move.charAt(0)));
			int row = Integer.parseInt(String.valueOf(move.charAt(1)));
			if (gameBoard[column][row].getIcon() == empty) {
				return true;
			}
		}
		return false;
	}

	public void displayHints(int player) {
		ArrayList<String> possibleMoves = logicBoard.findPossibleMoves(player);
		for (String hint : possibleMoves) {
			int column = Integer.parseInt(String.valueOf(hint.charAt(0)));
			int row = Integer.parseInt(String.valueOf(hint.charAt(1)));
			if (gameBoard[column][row].getIcon() == empty) {
				gameBoard[column][row].setIcon(hintSpot);
			}
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

		// set hints for first round
		ArrayList<String> possibleMoves = logicBoard.findPossibleMoves(player);
		for (String hint : possibleMoves) {
			int column = Integer.parseInt(String.valueOf(hint.charAt(0)));
			int row = Integer.parseInt(String.valueOf(hint.charAt(1)));
			if (gameBoard[column][row].getIcon() == empty) {
				gameBoard[column][row].setIcon(hintSpot);
			}
		}
	}

	public void restartGame() {
		logicBoard = new BoardGame();
		gamePieces();
		player = 1;
		playersTurn.setText("White Player's Turn");
		displayHints(player);
		againstComputer = false;
		againstCom.setEnabled(true);
		againstComputerHard = false;
		againstComHard.setEnabled(true);
	}

	public static void main(String[] args) {
		BoardGui boardGui = new BoardGui();
		boardGui.setVisible(true);
	}

}
