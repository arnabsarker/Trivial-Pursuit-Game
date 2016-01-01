import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MoveListener implements ActionListener {

	private Board board;
	private JPanel moveoption_panel;
	private JPanel q_panel;
	private boolean clockwise;
	private Player currentplayer;
	
	public MoveListener(Board b, JPanel m, JPanel q, boolean cw){
		board = b;
		moveoption_panel = m;
		q_panel = q;
		clockwise = cw;
		currentplayer = b.currentPlayer();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		moveoption_panel.setVisible(false);
		q_panel.setVisible(true);
		if(currentplayer.numberWedges() == 6) {
			board.moveCurrentPlayer(currentplayer.getPos(),
			Math.min(currentplayer.getInnerPos() + board.getCurrentRoll(), 6));
			board.repaint();
			return;
		}
		if (clockwise){
			board.moveCurrentPlayer((currentplayer.getPos() - board.getCurrentRoll() + 42) % 42, 0);
		} else{
			board.moveCurrentPlayer((currentplayer.getPos() + board.getCurrentRoll()) % 42, 0);
		}
		board.repaint();
	}

}
