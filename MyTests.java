import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import org.junit.Before;
import org.junit.Test;



public class MyTests {

	//Move position testing
	Board board;
	QuestionSet questions;
	
	@Before 
	public void setUp() throws IOException{
		final JLabel rollnum = new JLabel("Number Rolled");
		final JLabel currentuser = new JLabel("Current Turn: Player 1");
		board = new Board(rollnum, currentuser);
		questions = new QuestionSet();
	}
	
	@Test
	public void testNormalMovement() {
		Player p1 = new Player(18, 0, 1);
		List<Tile> expected = new ArrayList<Tile>();
		expected.add(new Tile(QType.ART));
		expected.add(new Tile(QType.GEOGRAPHY));
		assertEquals("move two spaces different categories", 
				board.moveOptions(2, board.getPlayerPos(), p1), expected);
		
	}
	
	@Test
	public void testSameTileMovement() {
		Player p1 = new Player(12, 0, 1);
		List<Tile> expected = new ArrayList<Tile>();
		expected.add(new Tile(QType.ENTERTAINMENT));
		expected.add(new Tile(QType.ENTERTAINMENT));
		assertEquals("move three spaces same categories", 
				board.moveOptions(3, board.getPlayerPos(), p1), expected);
		
	}

	@Test
	public void testEndofArrayMovement() {
		Player p1 = new Player(40, 0, 1);
		List<Tile> expected = new ArrayList<Tile>();
		expected.add(new Tile(QType.SPORTS));
		expected.add(new Tile(QType.SPORTS));
		assertEquals("move three spaces same categories", 
				board.moveOptions(3, board.getPlayerPos(), p1), expected);
		
	}
	
	@Test
	public void testBeginningofArrayMovement() {
		Player p1 = new Player(2, 0, 1);
		List<Tile> expected = new ArrayList<Tile>();
		expected.add(new Tile(QType.SCIENCE));
		expected.add(new Tile(QType.GEOGRAPHY));
		assertEquals("move three spaces same categories", 
				board.moveOptions(4, board.getPlayerPos(), p1), expected);
		
	}
	
	@Test
	public void testWedgeTile() {
		Player p1 = new Player(12, 0, 1);
		List<Tile> expected = new ArrayList<Tile>();
		expected.add(new WedgeTile(QType.ART));
		expected.add(new Tile(QType.GEOGRAPHY));
		assertEquals("move two spaces, one wedge, different categories", 
				board.moveOptions(2, board.getPlayerPos(), p1), expected);
	}
	
	@Test
	public void testWedgeTileSameCategory() {
		Player p1 = new Player(11, 0, 1);
		List<Tile> expected = new ArrayList<Tile>();
		expected.add(new WedgeTile(QType.ART));
		expected.add(new Tile(QType.ART));
		assertEquals("move two spaces, one wedge, same categories", 
				board.moveOptions(3, board.getPlayerPos(), p1), expected);
	}
	
	@Test
	public void testInnerTileProperly() {
		Player p1 = new Player(14, 0, 1);
		p1.addWedge(QType.SPORTS);
		p1.addWedge(QType.ART);
		p1.addWedge(QType.ENTERTAINMENT);
		p1.addWedge(QType.SCIENCE);
		p1.addWedge(QType.GEOGRAPHY);
		p1.addWedge(QType.HISTORY);
		List<Tile> expected = new ArrayList<Tile>();
		expected.add(new InnerTile(QType.GEOGRAPHY));
		assertEquals("move two spaces, into board", 
				board.moveOptions(2, board.getPlayerPos(), p1), expected);
	}
	
	@Test
	public void testWinTileProperly() {
		Player p1 = new Player(14, 0, 1);
		p1.addWedge(QType.SPORTS);
		p1.addWedge(QType.ART);
		p1.addWedge(QType.ENTERTAINMENT);
		p1.addWedge(QType.SCIENCE);
		p1.addWedge(QType.GEOGRAPHY);
		p1.addWedge(QType.HISTORY);
		List<Tile> expected = new ArrayList<Tile>();
		expected.add(new WinningTile());
		assertEquals("move to center of board", 
				board.moveOptions(6, board.getPlayerPos(), p1), expected);
	}
	
	@Test
	public void testGoToWinFromInside() {
		Player p1 = new Player(14, 0, 1);
		p1.addWedge(QType.SPORTS);
		p1.addWedge(QType.ART);
		p1.addWedge(QType.ENTERTAINMENT);
		p1.addWedge(QType.SCIENCE);
		p1.addWedge(QType.GEOGRAPHY);
		p1.addWedge(QType.HISTORY);
		p1.move(14, 3);
		List<Tile> expected = new ArrayList<Tile>();
		expected.add(new WinningTile());
		assertEquals("move to center of board", 
				board.moveOptions(6, board.getPlayerPos(), p1), expected);
	}
	
	//Question Formatting Tests
	@Test
	public void testQuestionSetSize() throws IOException, Question.FormatError {
		assertEquals("55 Art Questions", 55, questions.getSize(QType.ART));
		assertEquals("46 Entertainment Questions", 46, questions.getSize(QType.ENTERTAINMENT));
		assertEquals("62 Geography Questions", 62, questions.getSize(QType.GEOGRAPHY));
		assertEquals("50 History Questions", 50, questions.getSize(QType.HISTORY));
		assertEquals("51 Science Questions", 51, questions.getSize(QType.SCIENCE));
		assertEquals("39 Sports Questions", 39, questions.getSize(QType.SPORTS));
	}
	
	@Test
	public void testQuestionSetSizeAfterOne() throws IOException, Question.FormatError {
		assertEquals("55 Art Questions Before", 55, questions.getSize(QType.ART));
		questions.getRandomQuestion(QType.ART);
		assertEquals("54 Art Questions After", 54, questions.getSize(QType.ART));
	}
	
	@Test
	public void testQuestionSetSizeAfterAll() throws IOException, Question.FormatError {
		assertEquals("55 Art Questions Before", 55, questions.getSize(QType.ART));
		for(int i = 1; i <= 55; i++){
			questions.getRandomQuestion(QType.ART);
		}
		assertEquals("0 Art Questions After", 0, questions.getSize(QType.ART));
	}
	
	@Test
	public void testQuestionSetSizeAfterAllandOne() throws IOException, Question.FormatError {
		assertEquals("55 Art Questions Before", 55, questions.getSize(QType.ART));
		for(int i = 1; i <= 56; i++){
			questions.getRandomQuestion(QType.ART);
		}
		assertEquals("54 Art Questions After", 54, questions.getSize(QType.ART));
	}
	
	@Test
	public void testWinQuestionSet() throws IOException, Question.FormatError {
		assertEquals("304 Win Questions Before", 304, questions.getSize(QType.WIN));
		questions.getRandomQuestion(QType.WIN);
		assertEquals("303 Questions After", 303, questions.getSize(QType.WIN));
	}
	
	@Test
	public void testOthersEffectOnWin() throws IOException, Question.FormatError {
		assertEquals("304 Win Questions Before", 304, questions.getSize(QType.WIN));
		questions.getRandomQuestion(QType.ART);
		assertEquals("303 Questions After", 303, questions.getSize(QType.WIN));
	}
	
	@Test
	public void testWinAllQuestions() throws IOException, Question.FormatError {
		assertEquals("304 Win Questions Before", 304, questions.getSize(QType.WIN));
		for(int i = 1; i <= 305; i++){
			questions.getRandomQuestion(QType.WIN);
		}
		assertEquals("303 Questions After", 303, questions.getSize(QType.WIN));
	}
	
	@Test
	public void testAmpersandQuestion() throws Question.FormatError {
		Question q = new Question("who were two explorers?", "Lewis & Clark");
		q.isCorrect("Lewis and Clark");
	}
}
