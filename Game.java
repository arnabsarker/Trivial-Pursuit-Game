import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public void run() {
		// NOTE : recall that the 'final' keyword notes inmutability
		// even for local variables.

		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		final JFrame frame = new JFrame("TRIVIAL PURSUIT");
		frame.setLocation(300, 300);

		// Gameplay panel
		final JPanel gp_panel = new JPanel();
		gp_panel.setMaximumSize(new Dimension(300, 300));
		gp_panel.setLayout(new GridLayout(0,2));
		frame.add(gp_panel, BorderLayout.EAST);
		
		//Panel within gameplay panel to control rolls
		final JPanel roll_panel = new JPanel();
		gp_panel.add(roll_panel, BorderLayout.NORTH);
		
		final JLabel rollnum = new JLabel("Number Rolled");
		roll_panel.add(rollnum);
		
		//Move option Panel to control options on where to move
		final JPanel moveoption_panel = new JPanel();
		moveoption_panel.setPreferredSize(new Dimension(300, 200));
		gp_panel.add(moveoption_panel, BorderLayout.CENTER);
		moveoption_panel.setVisible(false);
		
		final JLabel moveoption = new JLabel("Options to Move:");
		moveoption_panel.add(moveoption);
		final JButton m_option1 = new JButton("Option 1");
		moveoption_panel.add(m_option1);
		final JButton m_option2 = new JButton("Option 2");
		moveoption_panel.add(m_option2);
		
		//Question Panel containing area where questions are asked and
		//answered.
		final JPanel q_panel = new JPanel();
		q_panel.setPreferredSize(new Dimension(350, 230));
		gp_panel.add(q_panel, BorderLayout.SOUTH);
		q_panel.setVisible(false);
		
		final JLabel question = new JLabel("Question: ");
		question.setPreferredSize(new Dimension (350, 100));
		q_panel.add(question);
		final JTextField answerspace = new JTextField(20);
		q_panel.add(answerspace);
		
		// Board containing user position
		final JPanel boardpanel = new JPanel();
        final JLabel currentuser = new JLabel("Current Turn: Player 1");
		final Board board = new Board(rollnum, currentuser);
		frame.add(boardpanel, BorderLayout.CENTER);
		boardpanel.add(board, BorderLayout.CENTER);
		boardpanel.add(currentuser, BorderLayout.SOUTH);
		
		//Button containing game instructions
		final JLabel instructions = new JLabel(board.instructions());
	    gp_panel.add(instructions);
		
		
				
		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is
		// an instance of ActionListener with its actionPerformed()
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.
		final JButton roll = new JButton("Roll");
		roll.addActionListener(new TurnListener(board, moveoption_panel, q_panel, m_option1,
				                                     m_option2, question, answerspace, roll));
		roll_panel.add(roll);

		// Put the frame on the screen
	    frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Timer to check if game is won.
		Timer timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (board.isWon()){
					boardpanel.setVisible(false);
					gp_panel.setVisible(false);
					roll_panel.setVisible(false);
					frame.add(new JLabel("<html>Game over! The winner is " +
					           board.currentPlayer() + "</html>"), BorderLayout.CENTER);
					JButton restart = new JButton("Play Again!");
					restart.setPreferredSize(new Dimension(200, 200));
					frame.add(restart, BorderLayout.SOUTH);
					restart.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							run();
						}
					});
				}
			}
		});
		timer.start();
		
	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
