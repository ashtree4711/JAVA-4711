package t12.util;

import t12.spinphone.Lexicon;
import t12.util.comparator.FrequencyComparator;

public class LexFilter {
	/**
	 * Mit dieser Funktion wird das aktuelle Lexikon eingegrenzt. 
	 * @param currentLex : Übergeben wird beim 1. Mal das ganze Lexikon und ab dem 2. ein bereits 
	 * gefiltertes Lexikon
	 * 
	 * @param charPos gibt an, an welcher Stelle das Lexikon gefiltert werden soll und gleichermaßen an welcher
	 * Stelle sich die Eingabe des Wortes derzeit befindet
	 * 
	 * @param button Ist der Input der SpinPhone-Gui und bestimmt, nach welcher Zahl an bestimmter Stelle gefiltert werden soll
	 * 
	 * @all Die Idee ist es, mithilfe dieser Funktion, das Lexikon immer wieder zu verkleinern, bis kein Wort mehr mit der Zahlenkombination
	 * übereinstimmt oder die Eingabe beendet ist und die Wörter nach Häufigkeit sortiert vorgeschlagen werden können.
	 * 
	 * @return Der Return-Wert ist ein gefiltertes Lexikon, welches nur noch die Wörter enhält, die die
	 * Zahlenkombination zulassen
	 */
	
	public Lexicon filtering(Lexicon currentLex, int charPos, int button) {
		Lexicon filteredLex = new Lexicon();
		
		IntToChar itc = new IntToChar();
		char charButton=itc.intToChar(button);
		
		for (int i = 0; i < currentLex.size(); i++) {
			if (currentLex.get(i).getWord().length()>charPos) {
			if (currentLex.get(i).getKey().charAt(charPos)==charButton) {
				filteredLex.add(currentLex.get(i));
				//System.out.println("Rank: " +(i+1)+" | "+currentLex.get(i).getWord()+" | "+currentLex.get(i).getFrequency());
			}
			}
		}
		FrequencyComparator fc = new FrequencyComparator();
		System.out.println("Zahl der mögl. Wörter: "+filteredLex.size());
		filteredLex.sort(fc.reversed());
		
		return filteredLex;
		
	}
	
	public String getMostFrequencyWord(Lexicon currentLex, int characterSize, String currentWord, int input) {
		
		KeyConverter kc = new KeyConverter();
		String currentLetter=kc.convertToKey(input);
		System.out.println("Aktuelle Lexikongröße: "+currentLex.size()); //derzeit 0! Warum?
		Lexicon rankedLex = new Lexicon();
		if(currentLex.size()!=0) {
			for (int i = 0; i < currentLex.size(); i++) {
				if (currentLex.get(i).getWord().length()==characterSize) {
					rankedLex.add(currentLex.get(i));
					System.out.println("Rank: " +(i+1)+" | "+currentLex.get(i).getWord()+" | "+currentLex.get(i).getFrequency());
				}
			}
			if(rankedLex.size()==0)
			{
				for (int i = 0; i < currentLex.size(); i++) {
					if (currentLex.get(i).getWord().length()==characterSize+1) {
					
						rankedLex.add(currentLex.get(i));
						System.out.println("Rank: " +(i+1)+" | "+currentLex.get(i).getWord()+" | "+currentLex.get(i).getFrequency());
					}
				
			}
			}
			if(rankedLex.size()==0) {
				currentWord=currentWord+currentLetter;
			}
			FrequencyComparator fc = new FrequencyComparator();
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
