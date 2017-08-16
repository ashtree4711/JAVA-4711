package t12.spinphone;

import java.io.Serializable;

import t12.util.KeyConverter;
import t12.util.SimpleConverter;

public class WordObject implements Serializable{
	private String word;
	private String codedNumber;
	private int frequency;
	public WordObject(String word) {
		super();
		this.word = word;
		this.codedNumber = convertCodedNumber(word);
		this.frequency = 1;
		
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String convertCodedNumber(String word) {
		SimpleConverter sc = new SimpleConverter();
		String codedNumber=sc.doConvert(word);
		
		
		/*KeyConverter kc = new KeyConverter();
		String codedNumber;
		for (int i = 0; i < word.length(); i++) {
			int number;
			number=kc.convertToNumber(word.charAt(i));
			
			
		}
		kc.*/
		
		return codedNumber;
	}
	public String getCodedNumber() {
		return codedNumber;
	}
		
	public void setCodedNumber(String codedNumber) {
		this.codedNumber = codedNumber;
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
		this.frequency = frequency++;
	}
	@Override
	public String toString() {
		return "WordObject [codedNumber=" + codedNumber + "]";
	}

	
	
}
