import java.util.*;

public class Helper{
	String p2w = ("[\\d]");
	String w2p = ("[\\w]");
	Pattern p2wPattern;
	Pattern w2pPattern;
	Matcher p2wMatcher;
	Matcher w2pMatcher;
	Word2Phone w2p;
	Phone2Word p2w;
	public helper(Word2Phone w2p, Phone2Word p2w){
		w2p = new Word2Phone(mapper);
		p2w = new Phone2Word(dictionary,mapper,w2p);
		p2wPattern = Pattern.compile(p2w);
		w2pPattern = Pattern.compile(w2p);
	}
	public void input(String line){
		p2wMatcher = p2wPattern.matcher(line);
		w2pMatcher = w2pPattern.matcher(line);
		if(p2wMatcher.find()){

		}
	}
}