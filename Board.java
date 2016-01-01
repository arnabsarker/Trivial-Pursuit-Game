import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Board extends JPanel {
	public static final String img_file = "TrivialPursuitBoard.png";
	public static final int BOARD_WIDTH = 400;
	public static final int INTERVAL = 35;
	private Player player1;
	private Player player2;
	private Tile[][] gameboard;
	private QuestionSet questions;
	private boolean iswon;
	private static BufferedImage img;
	private JLabel rollnumber;
	private int currentroll;
	private boolean p1turn;
	private Player currentplayer;
	private JLabel currentuser;
	
	public Board(JLabel rolled, JLabel user){
		player1 = new Player(0, 0, 1);
		player2 = new Player(21, 0, 2);
		/*Testing purposes only
		player1.addWedge(QType.SPORTS);
		player1.addWedge(QType.ART);
		player1.addWedge(QType.ENTERTAINMENT);
		player1.addWedge(QType.SCIENCE);
		player1.addWedge(QType.GEOGRAPHY);
		player1.addWedge(QType.HISTORY);
		player2.addWedge(QType.SPORTS);
		player2.addWedge(QType.ART);
		player2.addWedge(QType.ENTERTAINMENT);
		player2.addWedge(QType.SCIENCE);
		player2.addWedge(QType.GEOGRAPHY);
		player2.addWedge(QType.HISTORY);
		*/
		p1turn = true;
		currentplayer = player1;
		try{
		questions = new QuestionSet();
		if (img == null) {
			img = ImageIO.read(new File(img_file));
		}
		} catch (IOException e){
			System.out.println("Unexpected IO error");
		}
		iswon = false;
		
		/*Construct Uneven 2D Array to represent board
		 *Array has perimeter consisting of normal tiles
		 *The wedge tiles occur at every seventh tile
		 */
		gameboard = new Tile[42][];
		for(int i = 0; i < gameboard.length; i++){
			if(i % 7 == 0){
				gameboard[i] = new Tile[7];
				gameboard[i][0] = new WedgeTile(int_to_category(i / 7));
				for(int j = 1; j < gameboard[i].length - 1; j ++){
					gameboard[i][j] = new InnerTile(int_to_category((j + i) % 6));
				}
				gameboard[i][6] = new WinningTile();
			} else {
				gameboard[i] = new Tile[1];
				gameboard[i][0] = new Tile(int_to_category(i % 6));
			}
		}
		
		currentroll = 0;
		rollnumber = rolled;
		currentuser = user;
	}
	
	/**
	 * helper function to create the board
	 * @param n
	 * @return a category corresponding to the int
	 */
	private QType int_to_category(int n){
		switch (n){
		case 0: return QType.SCIENCE;
		case 1: return QType.SPORTS;
		case 2: return QType.ART;
		case 3: return QType.ENTERTAINMENT;
		case 4: return QType.GEOGRAPHY;
		case 5: return QType.HISTORY;
		default: throw new IllegalArgumentException("Int not between 0 and 5");
		}
	}
	
	public int roll(){
		int r = (int) ((Math.random() * 6) + 1);
		currentroll = r;
		rollnumber.setText(r + "");
		return r;
	}
	
	public int getCurrentRoll(){
		return currentroll;
	}
	
	public List<Tile> moveOptions(int roll, Tile on, Player p){
		List<Tile> possibletiles = new ArrayList<Tile>();
		if(on instanceof WedgeTile && p.numberWedges() == 6){
			possibletiles.add(gameboard[p.getPos()][roll]);
		} else if (on instanceof InnerTile){
			possibletiles.add(gameboard[p.getPos()][Math.min(p.getInnerPos() + roll, 6)]);
		} else if (on instanceof WinningTile){
			possibletiles.add(gameboard[p.getPos()][6]);
		} else {
			possibletiles.add(gameboard[(p.getPos() + roll) % 42][0]);
			possibletiles.add(gameboard[(p.getPos() - roll + 42) % 42][0]);
		}
		return possibletiles;
	}
	
	public void switchTurn(){
		if(p1turn){
			currentplayer = player2;
		} else{
			currentplayer = player1;
		}
		p1turn = !p1turn;
	}
	
	public boolean isPlayer1Turn(){
		return p1turn;
	}
	
	public Tile getPlayerPos(){
		return gameboard[currentplayer.getPos()][currentplayer.getInnerPos()];
	}
	
	public Player currentPlayer(){
		return currentplayer;
	}
	
	public void moveCurrentPlayer(int x, int y){
		currentplayer.move(x, y);
	}
	
	
	public void winGame() {
		iswon = true; 
	}
	
	public boolean isWon(){
		return iswon;
	}
	
	public Question getRandomQuestion(Tile t) throws IOException, Question.FormatError{
		return questions.getRandomQuestion(t.getType());
	}
	
	public String instructions() {
		String msg = "Welcome to Trivial Pursuit! "
				+ "In this game, you will answer trivia questions in order to win. "
				+ "To start your move, click the \"Roll\" button. "
				+ "Then decide where you would like to move by clicking your choice, "
				+ "either clockwise (CW) or counter-clockwise (CCW). Answer the question "
				+ "in the box provided, and press \"Enter\" to submit your answer. "
				+ "If you answer the question correctly, you can roll again! "
				+ "If you land on a wedge tile, you get the opportunity to play "
				+ "for a wedge! To get a wedge, just answer the wedge question correctly. "
				+ "The first player to go around the board and "
				+ "get all the wedges will be allowed to go to the middle of the "
				+ "board. The first player to answer a question from the "
				+ "center of the board wins! Good Luck!";
		String ans = "<html>";
		for(int i = 0; i < msg.length(); i += 56){
			if(i + 56 >= msg.length()){
				ans += msg.substring(i);
			} else {
				ans+= msg.substring(i, i + 56);
				if(msg.charAt(i + 56) != ' ' && msg.charAt(i + 55) != ' '){
					ans += "-";
				}
				ans += "<br>";
			}
		}
		ans +="<br>(note: Numbers should be typed in as words, so 2 is \"two\")";
		ans += "</html>";
		return ans;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img, 10, 10, BOARD_WIDTH, BOARD_WIDTH, null);
		player1.draw(g, BOARD_WIDTH, Color.BLACK);
		player2.draw(g, BOARD_WIDTH, Color.RED);
		if(p1turn){
			currentuser.setText("<html> Current Turn: " + player1.toString());
		} else {
			currentuser.setText("<html> Current Turn: " + player2.toString());
		}
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(BOARD_WIDTH + 20, BOARD_WIDTH + 20);
	}
	
}
