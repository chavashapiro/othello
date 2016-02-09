package othello;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class BoardGui extends JFrame{
	
	
	private static final long serialVersionUID = 1L;
	private JButton[][] gameBoard;
	private ImageIcon black;
	private ImageIcon white;
	private ImageIcon empty;
	private ImageIcon hint;
	
	private JPanel completePanel;
	private JPanel header;
	private JPanel board;
	
	private BoardGame logicBoard;
	private int player;
	
	public BoardGui(){
		setTitle("Othello");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		completePanel = new JPanel();
		completePanel.setLayout(new BorderLayout());
		
		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());
		
		board = new JPanel();
		board.setLayout(new GridLayout(8,8));
		
		
		gameBoard = new JButton[8][8];
		black = new ImageIcon("othelloBlack.png");
		white = new ImageIcon("othelloWhite.png");
		empty = new ImageIcon("othelloEmpty.PNG");
		hint = new ImageIcon("othelloMove.PNG");
		
		logicBoard = new BoardGame();
		player = 1;
		
		//create game, initial setup
		gamePieces();
		
		for (int i = 0; i < 8; i++){
			int column = i;
			for (int j = 0; j < 8; j++){
				int row = j;
				gameBoard[i][j].addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						String move = String.valueOf(column);
						move = move + String.valueOf(row);
						logicBoard.takeTurn(player, move);
						gamePieces();
						if (player == 1){
							player = 2;
						}
						else {
							player = 1;
						}
						
					}
					
				});
			}
		}
				/*gameBoard[i][j] = new JButton(empty);
				gameBoard[i][j].setPreferredSize(new Dimension(100,100));
				gameBoard[i][j].setBorder(new LineBorder(Color.BLACK, 1));
				gameBoard[i][j].addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						String move = String.valueOf(i);
						move = move + String.valueOf(j);
						logicBoard.takeTurn(1, move);
						
					}
					
				});
				board.add(gameBoard[i][j]);
			}	
		}
		gameBoard[3][4].setIcon(black);
		gameBoard[3][3].setIcon(white);
		gameBoard[4][4].setIcon(white);
		gameBoard[4][3].setIcon(black);
		*/
		completePanel.add(header, BorderLayout.NORTH);
		completePanel.add(board, BorderLayout.CENTER);
		getContentPane().add(completePanel);
		pack();
	}
	
	
	public void gamePieces(){
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if (logicBoard.getBoard()[i][j] == 0){
					gameBoard[i][j] = new JButton(empty);
					gameBoard[i][j].setPreferredSize(new Dimension(100,100));
					gameBoard[i][j].setBorder(new LineBorder(Color.BLACK, 1));
					board.add(gameBoard[i][j]);
				}
				else if (logicBoard.getBoard()[i][j] == 1){
					gameBoard[i][j] = new JButton(white);
					gameBoard[i][j].setPreferredSize(new Dimension(100,100));
					gameBoard[i][j].setBorder(new LineBorder(Color.BLACK, 1));
					board.add(gameBoard[i][j]);
				}
				else{
					gameBoard[i][j] = new JButton(black);
					gameBoard[i][j].setPreferredSize(new Dimension(100,100));
					gameBoard[i][j].setBorder(new LineBorder(Color.BLACK, 1));
					board.add(gameBoard[i][j]);
				}
			}
		}
	}
	
	public static void main(String[]args){
		BoardGui boardGui = new BoardGui();
		boardGui.setVisible(true);
	}

}
