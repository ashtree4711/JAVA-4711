package t12.util;

public class SimpleConverter {
	
		/**
		 * Zur besseren Verarbeitung im späteren Verlauf wird das Wort als String zu einem String @param key konvertiert.
		 * Ein Integer ist nicht nötig, da die Variable keine mathematischen Aufgabe zu lösen hat. 
		 * @param word
		 * @return
		 */
	public String doConvert(String word) {
		word=word.toLowerCase();
		word=word.replaceAll("[a-c]", "2").replaceAll("ä", "2");
		word=word.replaceAll("[d-f]", "3");
		word=word.replaceAll("[g-i]", "4");
		word=word.replaceAll("[j-l]", "5");
		word=word.replaceAll("[m-o]", "6").replaceAll("ö", "6");
		word=word.replaceAll("[p-s]", "7").replaceAll("ß", "7");
		word=word.replaceAll("[t-v]", "8").replaceAll("ü", "8");
		word=word.replaceAll("[w-z]", "9");
		String key = word;
		return key;
	
	}
}