import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class QuestionListener implements ActionListener {

	private Board board;
	private JPanel panel;
	private Tile tile;
	private JLabel question;
	private JTextField answer;
	private JButton roll;
	
	public QuestionListener(Board b, JPanel qp, Tile t, JLabel q, JTextField a, JButton r){
		board = b;
		panel = qp;
		tile = t;
		question = q;
		answer = a;
		roll = r;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Question currentquestion = board.getRandomQuestion(tile);
			question.setText(currentquestion.toString());
			answer.setVisible(true);
			answer.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(currentquestion.isCorrect(answer.getText())){
						if(tile instanceof WedgeTile){
							((WedgeTile) tile).giveWedge(board.currentPlayer());
						} else if(tile instanceof WinningTile){
							board.winGame();
						}
						question.setText("Correct! Roll Again!");
					} else{
						board.switchTurn();
						question.setText("<html>So sad! The correct answer was: "
						           + currentquestion.getAns() + 
						           "<br>Give the other player a turn</html>");
					}
					roll.setVisible(true);
					answer.setVisible(false);
					board.repaint();
				}
			});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Question.FormatError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
