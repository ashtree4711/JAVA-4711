package t12.util;

/**
 * Eine Hilfsklasse, die die Konvertierung von Buchstaben zu "Tasten", also
 * Zahlen, übernimmt. Sie ist dabei nicht vollständig, d.h. nicht alle
 * Buchstaben werden berücksichtigt.
 * 
 * <p>
 * Contributors: <br>
 * Stephan Schwiebert - Initial API and implementation (2006)<br>
 * Mihail Atanassov - Refactoring (2015)
 * </p>
 * 
 */
public class KeyConverter {

	/**
	 * Statische Methode, die zu einem gegebenen Zeichen die entsprechende
	 * Zahl zurückgibt, d.h. 'a', 'b', 'c' oder 'ä' werden zu 2, 'd', 'e', 'f' zu 3
	 * usw. Erwartet werden Kleinbuchstaben, bei unbekannten Buchstaben
	 * (alle Großbuchstaben, Satzzeichen, Whitespaces etc.) wird -1 zurückgegeben.
	 * 
	 * @param key Der zu interpretierende Buchstabe
	 * @return Der Zahlencode des Zeichens, oder -1 bei unbekannten Zeichen
	 */
	public static int convertToNumber(char key) {
		switch(key) {
	        case 'a': case 'ä': case 'b': case 'c': return 2;
	        case 'd': case 'e': case 'f': return 3;
	        case 'g': case 'h': case 'i': return 4;
	        case 'j': case 'k': case 'l': return 5;
	        case 'm': case 'n': case 'o': case 'ö': return 6;
	        case 'p': case 'q': case 'r': case 's': case 'ß': return 7;
	        case 't': case 'u': case 'v': case 'ü': return 8;
	        case 'w': case 'x': case 'y': case 'z': return 9;
	        default: return -1;
		}
	}

	public static String convertToKey(int number) {
		System.out.println("convertToKey:: " + number);
		switch(number) {
	        case 2: return "a";
	        case 3: return "d";
	        case 4: return "g";
	        case 5: return "j";
	        case 6: return "m";
	        case 7: return "p";
	        case 8: return "t";
	        case 9: return "w";
	        default: return "";
		}
	}

}
