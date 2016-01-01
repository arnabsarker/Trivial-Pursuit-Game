import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class Question implements Comparable<Question> {
     private String question;
     private String answer;
     
     
     /**
      * Error that occurs if a question or answer contains parentheses.
      *
      */
     @SuppressWarnings("serial")
	public static class FormatError extends Exception {
    	 public FormatError(String msg){
    		 super(msg);
    	 }
     }
     
     public Question(String q, String ans) throws FormatError{
    	 if(q.contains("(") || q.contains(")")){
    		 throw new FormatError("Question Contains a Parenthesis");
    	 } else if(ans.contains("(") || ans.contains(")")){
    		 throw new FormatError("Answer Contains a Parenthesis");
    	 }
    	 question = q;
    	 answer = ans;
     }
     
     public String getQuestion(){
    	 return question;
     }
     
     public String getAns(){
    	 return answer;
     }
     
     /**
      * A guess is considered correct if it is the same word
      * @param guess
      * @return true if the guess is correct, false otherwise
      */
     public boolean isCorrect(String guess){
    	 if(guess.trim().length() == 0){
    		 return false;
    	 }
    	 String adjustguess = retainAlphanumeric(guess.toLowerCase().trim());
    	 String adjustans = retainAlphanumeric(answer.toLowerCase().trim());
    	 return (adjustguess.equals(adjustans) || hasSameChars(adjustguess, adjustans));
     }

	@Override
	public int compareTo(Question o) {
		return question.compareTo(o.getQuestion());
	}
	
	@Override
	public String toString() {
		String out = "<html>";
		for(int i = 0; i < question.length(); i += 50){
			if(i + 50 >= question.length()){
				out += question.substring(i);
			} else {
				out+= question.substring(i, i + 50);
				if(question.charAt(i + 50) != ' ' && question.charAt(i + 49) != ' '){
					out += "-";
				}
				out += "<br>";
			}
		}
		out += "</html>";
		return out;
	}
	
	private boolean hasSameChars(String a, String g){
		char[] gchars = g.toCharArray();
		char[] achars = a.toCharArray();
		Set<Character> gset = new TreeSet<Character>();
		Set<Character> aset = new TreeSet<Character>();
		for(int i = 0; i < Math.min(gchars.length, achars.length); i++){
			gset.add(gchars[i]);
			aset.add(achars[i]);
		}
		return gset.containsAll(aset) && aset.containsAll(gset);
	}
	
	private String retainAlphanumeric(String s){
		char[] schar = s.toCharArray();
		String ans = "";
		for(int i = 0; i < schar.length; i++){
			if(Character.isLetterOrDigit(schar[i])){
				ans += schar[i];
			} else if (i == '&') {
				ans += "and";
			}
		}
		return ans;
	}
}
