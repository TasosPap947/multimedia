package application;

import java.util.Set;

import exceptions.UnbalancedException;
import exceptions.UndersizeException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class MakeDictionary {
	
	private Set<String> set;
	
	public MakeDictionary(String openLibraryID, String dictionaryID) {
		this.set = new HashSet<String>();
		Book book = new Book(openLibraryID);
		String text = book.getText();
		String word = null;
		
		int totalWords = 0;
		int nineLetters = 0;	
		
		try (Scanner scanner = new Scanner(text)) {
		    
			word = scanner.useDelimiter("[^A-Za-z]+").next();
		    
			while(scanner.hasNext()) {
		    	int len = word.length();
				if (len > 5) {
		    		this.set.add(word);
		    		totalWords++;
		    		
		    		if (word.length() > 8) {
		    			nineLetters++;
		    		}
		    	}
		    	word = scanner.next();
			}
		}
		
		try {
			
			if (totalWords < 20) { 
				throw new UndersizeException();
			}
			if ((nineLetters/(double)(totalWords)) < 0.2) { 
				throw new UnbalancedException();
			}
		} catch (Exception e) {
			System.out.println("Unable to make Dictionary file");
			return;
			
		} finally {
			System.out.println("Total: " + totalWords);
			System.out.println("9 >  : " + nineLetters);
			System.out.println("Ratio: " + (nineLetters/(double)(totalWords)));
		}	
		
		//if Dictionary is valid then write to file
		new CreateFile(dictionaryID);
		new WriteToFile(dictionaryID, this.dictionaryString());
    }
	
	public String dictionaryString() {
		String result = new String();
		for (String word : set) {
			result = result + word.toUpperCase() + "\n";
		}
		return result;
	}
	
	class CreateFile {
		  CreateFile(String dictionaryID) {
			  
		    	try  {	
					File txtFile = new File("medialab/hangman_" + dictionaryID + ".txt");
					if (txtFile.createNewFile()) {
						System.out.println("File created: " + txtFile.getName());
					} else {
						System.out.println("File already exists.");
					}
				}
		    	catch (IOException e) {
		    		System.out.println("An error occurred.");
		    		e.printStackTrace();
		    }
		}
	}
	
	class WriteToFile {
		  WriteToFile(String filename, String text) {
		    try {
		      FileWriter writer = new FileWriter("medialab/hangman_"+filename + ".txt");
		      writer.write(text);
		      writer.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		  }
	}
	
}
