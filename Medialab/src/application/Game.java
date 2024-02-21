package application;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <h1>The Game class implements all the necessary utilities of the hangman game. </h1>
 * <p>
 * The Game object represents a whole game from start to finish. It is 
 * characterized by the word that has been chosen randomly from the dictionary.
 * It keeps track of the current state of the letters that have been guessed 
 * by the player, the total points, the total right Guesses, the percentage 
 * of the tight Guesses of the player. 
 * 
 * The basic utility is the "guess" method which is called avery time the 
 * player makes a move in order to go to the next state.</p>
 * 
 * @author Anastasios Papazafeiropoulos, 03118079
 */

public class Game {
	
	public int lives;
	public int points;
	public double percentange;
	public Word word;
	public List<ListPair> letters;
	public List<String> words;
	public List<Character> state;
	public int tries;
	public int rightGuesses;
	
	/**
	 * <p> It initializes all the values of the class
	 * and saves in the words array all the words of length
	 * equal to the word (the answer of the game). It also
	 * initializes the initial state (List of characters) 
	 * to "_". Each character in the list symbolizes the state
	 * of the letter. If it is "_" then the player has not found
	 * the letter. If it is an actual letter then the player
	 * has found it.
	 *  
	 * @param String The answer of the game
	 * @param Dictionary The dictionary used in game
	 * @return null
	 */
	public Game(String word, Dictionary dict) {
		
		this.tries = 0;
		this.rightGuesses = 0;
		this.lives = 6;
		this.points = 0;
		this.percentange = 0;
		this.word = new Word(word);
		this.words = dict.getWordsOfLen(this.word.len);
		this.state=new ArrayList<Character>();
		for (int i=0; i<this.word.len; i++) {
			this.state.add('_');
		}
	}
	
	/**
	 * <h1> Implements the letter guess move of player</h1>
	 * <p> First it increases the total number of tries by one.
	 * Then it checks if the answer is true or false by checking
	 * directly the letter of the according position in word
	 * string of answer. Lastly, initialize a hash set which will
	 * be needed in order to calculate the new words list which 
	 * contains the possible answers.</p>
	 * <h2> If the answer is true</h2>
	 * <p> Increase rightGuesses by one. Then, check for the words 
	 * in the hash set which have different letter in the position
	 * of the guessed one. If it is different then remove words form 
	 * the set. In other words, we update the "words" list accordingly.
	 * Then, calculate the possibility of the guessed letter and add 
	 * points accordingly.</p>
	 * <h2>If the answer is false</h2>
	 * <p> Update the possible words list by implementing the opposite 
	 * logic of that above. Then, decrease lives by one and also the 
	 * total points by 15. <p>
	 * <p>
	 * At the end update the possible letters that are shown to the player
	 * by calling the createLetters method of the same class. Lastly, 
	 * calculate/update the percentage of right guesses.</p>
	 * 
	 * 
	 * @param Integer Integer indicating position of letter guess
	 * @param Character Character actually guessed by the player
	 * @return Boolean Boolean : if the guess is right. False if the guess 
	 * is wrong
	 */
	public boolean guess(int p, Character c) {
		
		this.tries++;
		boolean win = false;
		if (this.word.word.charAt(p) == c) {
			win = true;
		}
		Set<String> set = new HashSet<>(this.words);
		if (win) {
			this.rightGuesses++;
			for (String word : this.words) {
				if (word.charAt(p) != c) {
					set.remove(word);
				}
			}
			double letterPossibility = 0;
			for (Pair pair : letters.get(p).list) {
				if (pair.c == c) {
					letterPossibility = pair.freq/(double)this.words.size();
				}
			}
			System.out.println("Letter possibility: " + letterPossibility);
			if (letterPossibility >= 0.6) {
				this.points += 5;
			}
			else if (letterPossibility >= 0.4) {
				this.points += 10;
			}
			else if (letterPossibility >= 0.25) {
				this.points += 15;
			}
			else {
				this.points += 30;
			}
			this.state.set(p,c);
		}
		else {
			for (String word : this.words) {
				if (word.charAt(p) == c) {
					set.remove(word);
				}
			}
			this.lives--;
			points = Math.max(points-15, 0);
		}
		this.words = new ArrayList<>(set);
		this.createLetters();
		this.percentange = (this.tries-(6-this.lives))/(double)(this.tries);
		return win;
	}
	
	/**
	 * Get the positions remaining to be guessed right. This is helpful
	 * for updating the drop down menu for position select in 
	 * the GUI.
	 * 
	 * @return List<Integer> Returns a list of all the positions
	 * remaining not found.
	 */
	public List<Integer> getPositions() {
		
		List<Integer> ret = new ArrayList<>();
		for (int i=0; i<this.word.len; i++) {
			if (this.state.get(i) == '_') {
				ret.add(i);
			}
		}
		return ret;
	}
	
	
	/**
	 * Get the letters that are possible answer for a specific
	 * position. It is used in the drop down menu for letter
	 * select in the GUI.
	 * 
	 * @param int The specific position
	 * @return List<Character> Return a list of possible letters
	 * of specific position 
	 */
	public List<Character> getLetters(int position) {
		
		List<Character> ret = new ArrayList<>();
		for (Pair p : this.letters.get(position).list) {
			ret.add(p.c);
		}
		return ret;
	}
	
	
	/**
	 * Create array of List of Pairs where the Pair is a letter
	 * and its frequency and List corresponds to the Pairs of
	 * specific position.
	 * 
	 * @reutrn null
	 */
	public void createLetters() {
		
		this.letters = new ArrayList<>();
		for (int i=0; i<this.word.len; i++) {
			this.letters.add(new ListPair(createLettersPosition(i)));
		}
	}
	
	
	/**
	 * Create the list of Pairs of specific positions. In each
	 * pair keep the letter and the frequency that it appears
	 * in the possible words. Then sort the list of pairs 
	 * in decreasing frequency.
	 * 
	 * @return null
	 */
	private List<Pair> createLettersPosition(int position) {
		
		List<Pair> ret = new ArrayList<Pair>();
		for (String word : this.words) {
				boolean found = false;
				for (Pair pair : ret) {
					if (pair.c == word.charAt(position)) {
						pair.freq++;
						found = true;
						break;
					}
				}
				if (!found) ret.add(new Pair(word.charAt(position), 1));
		}
		Collections.sort(ret);
		return ret;
	}
	
	/**
	 * Create the String that is going to appear in the 
	 * GUI. It represents all the possible letters
	 * of each position, sorted in descending frequency
	 * order. It uses the letters array of the class.
	 * 
	 * @return String 
	 */
	public String lettersString() {
		String ret = new String();
		for (int i=0; i<this.word.len; i++) {	
			ret = ret + Integer.toString(i) + ": ";
			for (Pair pair : this.letters.get(i).list) {
				ret = ret + pair.c + ", "; 
			}
			ret = ret + "\n" ;
		}
		return ret;
	}
	
	/** 
	 * Method to print all possible letters of 
	 * specific position. Used in debugging.
	 */
	public void printLetters(int position) {
		ListPair listpair = this.letters.get(position);
			for (Pair p : listpair.list) {
				p.print();
			}
	}
	
	/**
	 * Method to print all remaining words. Used in 
	 * debugging.
	 */
	public void printWords() {
		for (String w : this.words) {
			System.out.println(w);
		}
	}
	
	/**
	 * class that represents a list of pairs used by
	 * the Game class to create the possible letters
	 * of each position. 
	 */
	private class ListPair {
		public List<Pair> list;
		public ListPair (List<Pair> a) {
			this.list = a;
		}
	}
	
	/**
	 * class that represents a Pair <lettter, Frequency>. 
	 * It is used by the ListPair class.
	 */
	private class Pair implements Comparable<Pair> {
		public Character c;
		public int freq;
		
		public Pair(Character c, int freq) {
			this.c = c;
			this.freq = freq;
		}
		
		/**
		 * Method that is used in sorting a List of pairs according
		 * to frequency.
		 */
		public int compareTo(Pair otherPair) {
			return this.freq > otherPair.freq ? -1 : this.freq < otherPair.freq ? 1 : 0;
		}
		
		/**
		 * Method for printing a pair. Used in debugging.
		 */
		public void print() {
			System.out.print(this.c+" "+this.freq+"\n");
		}
	}
	
}
