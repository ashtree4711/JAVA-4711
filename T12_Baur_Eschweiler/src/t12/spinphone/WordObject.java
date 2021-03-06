package t12.spinphone;

import java.io.Serializable;

import t12.util.KeyConverter;
import t12.util.SimpleConverter;

public class WordObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6049759241541530700L;
	private String word;
	private String key;
	private int frequency;
	
	/**
	 * Beim Aufruf des Konstrukturs, der für jedes Wort aus dem Corpus nur einmal aufgerufen wird. Wird das Wort
	 * unter @param word eingetragen, sofort ein Key generiert @param key und die Häufigkeit @param frequency auf "1"
	 * gesetzt. Taucht ein Wort ein zweites mal auf, soll nur die Häufigkeit des Wort-Objektes erhöht werden. Des Weiteren verfügt
	 * die Klasse über Setter und Getter.
	 * @param word: Das eigentliche Word wird als String übergeben
	 */
	public WordObject(String word) {
		super();
		this.word = word;
		this.key = convertToKey(word);
		this.frequency = 1;
		
		
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	/**
	 * Das eigentliche Wort als String wird übergeben und in einen String Key konvertiert.
	 * Die Nutzung von Strings ist sinnvoll, da mit den Keys keine mathematischen Rechnungen erfolgen und
	 * mithilfe der .charAt() nach einzelnen Stellen gefiltert werden kann.
	 * @param word
	 * @return key
	 */
	public String convertToKey(String word) {
		SimpleConverter sc = new SimpleConverter();
		String key=sc.doConvert(word);
		
		return key;
	}
	
	public String getKey() {
		return key;
	}
		
	public void setCodedNumber(String key) {
		this.key = key;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	/**
	 * Erhöht bei Aufruf die @param frequency . Die Funktion wird nur aufgerufen, 
	 * wenn das Wortobjekt @param WordObject 
	 * bereits im Lexikon @param Lexicon existiert.
	 * @author Mark Eschweiler
	 */
	public void raiseFrequency() {
		this.frequency++;
	}
	
	@Override
	public String toString() {
		return "WordObject [key=" + key + "]";
	}
	
	

	
	
}
