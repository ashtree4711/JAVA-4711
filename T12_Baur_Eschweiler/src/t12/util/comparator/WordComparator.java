package t12.util.comparator;

import java.util.Comparator;

import t12.spinphone.WordObject;

public class WordComparator implements Comparator<WordObject> {
	/**
	 * Der WordComparator sortiert die Wordobjekte in den Arraylisten
	 * des Lexikons alphabetisch nach dem Wort selbst.
	 * @author Mark Eschweiler
	 */
	@Override
	public int compare(WordObject word1, WordObject word2) {
		if (word1.getWord() == null && word2.getWord() == null) {
			return 0;
		 }
		if (word1.getWord() == null) {
			return 1;
		 }
		if (word2.getWord() == null) {
			return -1;
		 }
		    return word1.getWord().compareTo(word2.getWord());
	}
	
	

		
		

}
