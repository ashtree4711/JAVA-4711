package t12.util.comparator;

import java.util.Comparator;

import t12.spinphone.WordObject;

public class FrequencyComparator implements Comparator<WordObject>{
	/**
	 * Der FrequencyComparator sortiert die Wordobjekte in den Arraylisten
	 * des Lexikons nach der Häufigkeit.
	 * @author Mark Eschweiler
	 */
	@Override
	public int compare(WordObject word1, WordObject word2) {
		if (word1.getFrequency() == 0 && word2.getFrequency() == 0) {
			return 0;
		 }
		if (word1.getFrequency() == 0) {
			return 1;
		 }
		if (word2.getFrequency() == 0) {
			return -1;
		 }
			
		    return Integer.compare(word1.getFrequency(),word2.getFrequency());
	}

	


}
