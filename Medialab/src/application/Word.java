package application;

public class Word {
	
	public String word;
	public int len;
	
	public Word (String word) {
		this.word = word;
		this.len = word.length();
	}
	
	public void print() {
		System.out.println(this.word);
	}
	
//	public List<Pair<Character, Integer>> getLetters(List<String> words, int position) {
//		List<Pair<Character, Integer>> ret = new ArrayList<Pair<Character, Integer>>();
//		for (String word : words) {
//			char string[] = word.toCharArray();
//			for (int i=0; i<len; i++) {
//				
//			}
//		}
//	}
}
