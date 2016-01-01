import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TurnListener implements ActionListener {
    private int i = 0;
	private Board board;
	private JPanel moveoption_panel;
	private JPanel q_panel;
	private JButton m_option1;
	private JButton m_option2;
	private JLabel question;
	private JTextField answer;
	private JButton roll;
	
	public TurnListener(Board b, JPanel mo, JPanel qp, JButton o1, JButton o2, JLabel q, 
			                                                     JTextField ans, JButton r){
		board = b;
		moveoption_panel = mo;
		q_panel = qp;
		m_option1 = o1;
		m_option2 = o2;
		question = q;
		answer = ans;
		roll = r;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		removelisteners(m_option1);
		removelisteners(m_option2);
		removelisteners(answer);
		roll.setVisible(false);
		answer.setText("");
		board.roll();
		q_panel.setVisible(false);
		moveoption_panel.setVisible(true);
		List<Tile> optionsformove = board.moveOptions(board.getCurrentRoll(), board.getPlayerPos(),
		        board.currentPlayer());
		if (optionsformove.size() == 1) {
			m_option2.setVisible(false);
		} else {
			m_option2.setVisible(true);
		}
		if (optionsformove.size() >= 1) {
			if(optionsformove.size() == 1){
				m_option1.setText(optionsformove.get(0).toString());
			} else {
				m_option1.setText("CCW: " + optionsformove.get(0).toString());
			}
			m_option1.addActionListener(new MoveListener(board, moveoption_panel, q_panel, false));
			m_option1.addActionListener(new QuestionListener(board, q_panel, optionsformove.get(0),
					                           question, answer, roll));
		}
		if (optionsformove.size() == 2) {
			m_option2.setText("CW: " + optionsformove.get(1).toString());
			m_option2.addActionListener(new MoveListener(board, moveoption_panel, q_panel, true));
			m_option2.addActionListener(new QuestionListener(board, q_panel, optionsformove.get(1),
					                           question, answer, roll));
		}
	}
	
	private void removelisteners(JButton m){
		ActionListener[] als = m.getActionListeners();
		for(ActionListener a : als){
			m.removeActionListener(a);
		}
	}

	private void removelisteners(JTextField m){
		ActionListener[] als = m.getActionListeners();
		for(ActionListener a : als){
			m.removeActionListener(a);
		}
	}
}
