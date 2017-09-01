package t12.util;

import t12.spinphone.Lexicon;
import t12.util.comparator.FrequencyComparator;

public class LexiconToolbox {
	/**
	 * Die Klasse dient als Werkzeugkiste um Lexika zu filtern, zu sortieren, Wortobjekte zu suchen
	 * und bereitzustellen.
	 * @author Mark Eschweiler
	 */
	
	/**
	 * Mit dieser Methode wird das aktuelle Lexikon eingegrenzt. 
	 * @param currentLex : Übergeben wird beim 1. Mal das ganze Lexikon und ab dem 2. ein bereits 
	 * gefiltertes Lexikon, welches bereits diese Methode durchlaufen hat
	 * 
	 * @param charPos gibt an, an welcher Stelle das Lexikon gefiltert werden soll und gleichermaßen an welcher
	 * Stelle sich die Eingabe des Wortes derzeit befindet
	 * 
	 * @param button Ist der Input der SpinPhone-GUI und bestimmt, nach welcher Zahl an bestimmter Stelle gefiltert werden soll
	 * 
	 * Die Idee ist es, mithilfe dieser Funktion, das Lexikon immer wieder zu verkleinern, bis kein Wort mehr mit der 
	 * Zahlenkombination übereinstimmt oder die Eingabe beendet wird. 
	 * 
	 * @return Lexicon filteredLex: Das eingabe-spezifische Lexikon wird in @see t12.phones.TMT12Interpreter.typeWord() als 
	 * aktuelles Lexikon und in einer ArrayList aus Lexika abgespeichert. So kann mit jedem Aufruf entsprechend des letzten 
	 * Inputs das Lexikon vorbereitet werden.
	 */
	public Lexicon filtering(Lexicon currentLex, int charPos, int button) {
		Lexicon filteredLex = new Lexicon();
		IntToChar itc = new IntToChar();
		char charButton=itc.intToChar(button);
		for (int i = 0; i < currentLex.size(); i++) {
			if (currentLex.get(i).getWord().length()>charPos) {
				if (currentLex.get(i).getKey().charAt(charPos)==charButton) {
				filteredLex.add(currentLex.get(i));
				}
			}
		}
		FrequencyComparator fc = new FrequencyComparator();
		System.out.println("Zahl der mögl. Wörter: "+filteredLex.size());
		filteredLex.sort(fc.reversed());
		
		return filteredLex;
		
	}
	/**
	 * Die Methode bestimmt anhand des aktuellen gefilterten Lexikons @see filtering(), der aktuellen Wortlänge
	 * sowie des letzten Wortes, das nächste Wort vorschlagen bzw. übergeben, als auch die Suche nach
	 * Alternativen @see getAlternatives() vorbereiten.
	 * 
	 * @param currentLex : Wird benötigt, damit die zuvor gefilterte Stelle in einem möglichen Wort nicht nochmal 
	 * überprüft werden muss. Es enthält alle nun möglichen Worte
	 * 
	 * @param characterSize : Gibt der Methode die Größe des aktuellen Wortes und ist obligatorisch, um das temporäre
	 * <code>rankedLex</code> anzulegen.
	 * 
	 * Um das aktuelle Wort zu erhalten, wird die ArrayList <code>currentLexikon</code> iteriert und jedes Wort, dass die selbe
	 * Länge, wie die aktuelle Eingabegröße in die <code>rankedList</code> hinzugefügt. Ist kein solches Wort vorhanden, aber 
	 * <code>currentLexikon</code> nicht leer, so wird aus dem <code>currentLexikon</code> das Wort bis der größten
	 * Häufigkeit geholt, jedoch nur in der aktuellen Wortlänge als String erschaffen und übergeben..
	 * @return String currentWord
	 * 
	 * @author Mark Eschweiler
	 */
	public String getMostFrequencyWord(Lexicon currentLex, int characterSize, String currentWord) {
		FrequencyComparator fc = new FrequencyComparator();
		currentLex.sort(fc);
		Lexicon rankedLex = new Lexicon();
		if(currentLex.size()!=0) {
			for (int i = 0; i < currentLex.size(); i++) {
				if (currentLex.get(i).getWord().length()==characterSize) {
					rankedLex.add(currentLex.get(i));
					System.out.println("Rank: " +(i+1)+" | "+currentLex.get(i).getWord()+" | "+currentLex.get(i).getFrequency());
				}
			}
			if(rankedLex.size()==0) {
				currentWord=currentLex.get(0).getWord().substring(0, characterSize+1);	
			}
			rankedLex.sort(fc.reversed());
			if (rankedLex.isEmpty()) {
				return currentWord;
			}else {
				return currentWord = rankedLex.get(0).getWord();
			}
		}
		
		else {
			return null;
		}	
	}
public String getAlternativeWord(Lexicon currentLex, int characterSize, String currentWord, int alternativeCounter) {
		
		
		
		Lexicon rankedLex = new Lexicon(); 
		if(currentLex.size()!=0&&currentLex.size()>alternativeCounter) {
			for (int i = 0; i < currentLex.size(); i++) {
				if (currentLex.get(i).getWord().length()==characterSize) {
					rankedLex.add(currentLex.get(i));
				}
			}
			FrequencyComparator fc = new FrequencyComparator();
			rankedLex.sort(fc.reversed());
			if (rankedLex.size()>alternativeCounter) {
				return currentWord = rankedLex.get(alternativeCounter).getWord();
			}
			return currentWord;
		}
		else {
			return currentWord;
		}
		
		
		
		
		
	}
}
