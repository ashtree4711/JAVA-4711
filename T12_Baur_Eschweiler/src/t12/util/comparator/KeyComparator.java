package t12.util.comparator;

import java.util.Comparator;

import t12.spinphone.WordObject;

public class KeyComparator implements Comparator<WordObject> {
	
	@Override
	public int compare(WordObject word1, WordObject word2) {
		if (word1.getKey() == null && word2.getKey() == null) {
			return 0;
		 }
		if (word1.getKey() == null) {
			return 1;
		 }
		if (word2.getKey() == null) {
			return -1;
		 }
		    return word1.getKey().compareTo(word2.getKey());
	}
}
