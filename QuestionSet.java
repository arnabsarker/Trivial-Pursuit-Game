import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 
 * @author arnabsarker
 * 
 *         Class that parses through several text files to store sets of
 *         questions that will be mapped.
 */
public class QuestionSet {
	private Map<QType, Set<Question>> questionset;

	/**
	 * Initializes a question database with a map going to each questionset
	 * 
	 * @throws IOException
	 */
	public QuestionSet() throws IOException {
		questionset = new TreeMap<QType, Set<Question>>();
		questionset.put(QType.ART, createQSet("ArtandLiteratureQuestions.txt"));
		questionset.put(QType.ENTERTAINMENT, createQSet("EntertainmentQuestions.txt"));
		questionset.put(QType.GEOGRAPHY, createQSet("GeographyQuestions.txt"));
		questionset.put(QType.HISTORY, createQSet("HistoryQuestions.txt"));
		questionset.put(QType.SCIENCE, createQSet("ScienceandNatureQuestions.txt"));
		questionset.put(QType.SPORTS, createQSet("SportsandLeisureQuestions.txt"));
		questionset.put(QType.WIN, createQSet("WinningQuestions.txt"));
	}

	/**
	 * Creates a set of questions based on the text in a file INVARIANT: Each
	 * line in a file contains a question followed by an answer in parentheses.
	 * Questions can not have parentheses in them
	 * 
	 * @param text
	 * @return a set of questions from the text file
	 * @throws IOException
	 */
	private Set<Question> createQSet(String text) throws IOException {
		Set<Question> questions = new TreeSet<Question>();
		try {
			Reader r = new FileReader(text);
			BufferedReader in = new BufferedReader(r);

			String currentline = in.readLine();
			while (currentline != null) {
				int endofq = currentline.indexOf('(');
				String question = currentline.substring(0, endofq);
				int endofans = currentline.indexOf(')');
				String ans = currentline.substring(endofq + 1, endofans);

				Question q = new Question(question, ans);
				questions.add(q);
				currentline = in.readLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (Question.FormatError e) {
			System.out.println(e.getMessage());
		}
		return questions;
	}

	/**
	 * Gets a random question from the question set based on the type of
	 * question Removes questions after they are played to reduce redundancy If
	 * the question set is empty, replace it with the original set of questions
	 * 
	 * @param c
	 * @return a random question of the same type as input
	 * @throws IOException
	 * @throws Question.FormatError
	 */

	public Question getRandomQuestion(QType c) throws IOException, Question.FormatError {
		Set<Question> cquestions = null;
		if (questionset.get(c).size() == 0) {
			questionset.put(c, replaceQSet(c));
		}
		cquestions = questionset.get(c);
		int counter = 0;
		int random = (int) (Math.random() * cquestions.size());
		for (Question turn : cquestions) {
			if (counter == random) {
				Question q = turn;
				cquestions.remove(q);
				questionset.get(QType.WIN).remove(q);
				return q;
			}
			counter++;
		}
		throw new Question.FormatError("No questions available");
	}

	/**
	 * if a set is empty, replaces it with the original set established at the
	 * top of the file.
	 * 
	 * @param c
	 * @return
	 * @throws IOException
	 * @throws Question.FormatError 
	 */
	private Set<Question> replaceQSet(QType c) throws IOException, Question.FormatError {
		if (c != null) {
			switch (c) {
			case ART:
				return createQSet("ArtandLiteratureQuestions.txt");
			case ENTERTAINMENT:
				return createQSet("EntertainmentQuestions.txt");
			case GEOGRAPHY:
				return createQSet("GeographyQuestions.txt");
			case HISTORY:
				return createQSet("HistoryQuestions.txt");
			case SCIENCE:
				return createQSet("ScienceandNatureQuestions.txt");
			case SPORTS:
				return createQSet("SportsandLeisureQuestions.txt");
			case WIN:
				return createQSet("WinningQuestions.txt");
			}
		} else if (c == null) {
			throw new IllegalArgumentException();
		}
		return new TreeSet<Question>();
	}
	
	public int getSize(QType c){
		return questionset.get(c).size();
	}
}
