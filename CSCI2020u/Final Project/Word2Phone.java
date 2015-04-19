import java.util.*;

public class Word2Phone{
	Word2PhoneMapper mapper;
	String result = "";
	public Word2Phone(Word2PhoneMapper mapper){
		this.mapper = mapper;
	}
	synchronized public void change(String word){
		word = word.toLowerCase();
		char[] letters = word.toCharArray();
		for(int x=0;x<letters.length;x++){
			letters[x] = mapper.get(letters[x]);
		}
		result = new String(letters);
	}
	public String result(){
		return result;
	}
}