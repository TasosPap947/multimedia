package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Dictionary {

	public String ID;
	private List<String> words;
	public int totalWords;
	public double shortPrc;
	public double midPrc;
	public double longPrc;
	
	public Dictionary(String ID) throws FileNotFoundException {
		
		this.ID = ID;
		
		this.words = new ArrayList<>();
		Scanner scanner = new Scanner(new File("medialab/hangman_"+ID+".txt"));
		while(scanner.hasNext()) {
			words.add(scanner.nextLine());
		}
		
		this.totalWords = words.size();
		
		int shortInt = 0, midInt = 0, longInt = 0;
		for (String word : words) {
			int len = word.length();
			if (len == 6) {
				shortInt++;
			}
			else if (len < 10) {
				midInt++;
			}
			else longInt++;
		}
		double div = (double)(words.size());
		
		this.shortPrc = shortInt/div;
		this.midPrc = midInt/div;
		this.longPrc = longInt/div;
	}
	
	public String pickWord() {
		Random rand = new Random();
		String word = words.get(rand.nextInt(words.size()));
		return word;
	}
	
	public List<String> getWordsOfLen(int len) {
		List<String> ret = new ArrayList<>();
		for (String word : words) {
			if (word.length() == len) {
				ret.add(word);
			}
		}
		return ret;
	}
	
	
	
}
