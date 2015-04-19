import java.util.*;

class Phone2Word {
	Word2PhoneMapper mapper;
	Dictionary dictionary;
	Iterator<String> iterator;
	ArrayList<String> result;
	Word2Phone w2p;

	public Phone2Word(Dictionary dictionary, Word2PhoneMapper mapper, Word2Phone w2p){
		this.mapper = mapper;
		this.dictionary = dictionary;
		this.iterator = dictionary.iterator();
		this.w2p = w2p;
	}
	public void find(String number){
		String word, changed2Num;
		ArrayList<String> contain = new ArrayList<String>();
		while(iterator.hasNext()){
			word = iterator.next();
			w2p.change(word);
			changed2Num = w2p.result();
			if(number.compareTo(changed2Num)==0){
				contain.add(word);
			}
		}
		result = contain;
	}
	public ArrayList<String> result(){
		return result;
	}
}