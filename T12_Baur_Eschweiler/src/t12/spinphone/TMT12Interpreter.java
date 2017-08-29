package t12.spinphone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import t12.phones.LexiconSerializationException;
import t12.util.LexFilter;
import t12.util.comparator.FrequencyComparator;
import t12.util.comparator.KeyComparator;
import t12.util.comparator.WordComparator;

public class TMT12Interpreter implements T12Interpreter {
	
	public Corpus corpus = new Corpus();
	public Lexicon lexicon = new Lexicon();
	
	public List<String> index = new ArrayList<String>();
	private LexiconList lexiconList;
	private String currentWord;
	private Lexicon currentLexicon;
	private int lastNumber;
	private int positionCounter;
	private int alternativeCounter;

	@Override
	public String buttonPressed(int number) {
		System.out.println("Überprüfe aktuelle Lexikongröße: "+this.lexicon.size()+" Wörter");
		this.lastNumber=number;
		this.alternativeCounter=0; //siehe getAlternative()
		if (this.positionCounter==0) {
			LexFilter lf = new LexFilter();
			this.currentLexicon=this.lexicon;
			
			this.lexiconList = new LexiconList();
			
			this.currentLexicon=lf.filtering(this.currentLexicon, this.positionCounter, number);
			lexiconList.add(this.currentLexicon); //4. add currentLexicon zu lexiconList
			
			this.currentWord=lf.getMostFrequencyWord(currentLexicon, positionCounter, this.currentWord, number);
			
			
		}
		else {
LexFilter lf = new LexFilter();
System.out.println("LexAll: "+this.lexicon.size());
			if(this.currentLexicon.size()!=0) {
			this.currentLexicon=lf.filtering(this.currentLexicon, positionCounter, number); // 2. hole gesamtes Lexicon (this.lexicon) 3. gib Lexicon in Filter (lexicon, position, buttonINT) --> RETURN FilteredLexicon
			lexiconList.add(this.currentLexicon);
			System.out.println("this.current: "+this.currentLexicon.size());
			this.currentWord=lf.getMostFrequencyWord(currentLexicon, positionCounter, this.currentWord, number);
			}
			else {
				System.out.println("Keine Weiteren Einträge vorhanden");
				//wordCompleted();
			}
			
		}
		System.out.println(number);
		
		/*
		 * 1. erhalte Nummer, CHECK!
		 * 2. hole gesamtes Lexicon
		 * 3. gib Lexicon in Filter (lexicon, buttonINT, positionCounter) --> RETURN currentLexicon
		 * 4. add currentLexicon zu lexiconList
		 * 5. sortiere currentLexicon nach Häufigkeit
		 * 6. gib häufigster Wort || Wort darf nur so lang sein, wieviel Zahlen eingetippt worden sind
		 * 
		 *
		 * 
		 */
		
		this.positionCounter++; //immer wenn eine Zahl gedrückt wird erhält das Wort eine neue Stelle
		return this.currentWord;
	}

	@Override
	public void generateLexicon(String pathToTexts, String lexFileDestination) {
		// TODO Auto-generated method stub
		List<File> files = corpus.crawlFilesFromPath(pathToTexts);
		try {
			this.index = corpus.getTokensFromFiles(files, true);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		/*
		 * Während der Lexikonaufnahme wird überprüft, ob das Wort bereits vorhanden ist. Wenn nicht, wird es eingetragen,
		 * wenn doch, wird die Häufigkeit im passenden Wort-Objekt erhöht und kein neues Objekt erzeugt. Aktuell wird jedes 10. Objekt ins Lexikon aufgenommen
		 */
		
		//Lexicon lexicon = new Lexicon();
		//index.size() aus Testgründen auf 20000 geändert
		for (int i = 0; i < 100000; i++) {
			WordObject word = new WordObject(index.get(i));
			boolean found = false;
			if (i==0) {
				lexicon.add(word);
			}
			else if (i>0) {
				for (int j = 0; j < lexicon.size(); j++) {
					if(index.get(i).equals(lexicon.get(j).getWord())) {
						lexicon.get(j).raiseFrequency();
						found = true;
						
						
						
					}	
				}
				if (found == false)
				{
					lexicon.add(word);
				}
			}
			
	/*
	 * TESTUMGEBUNG FÜR DIE KONSOLE, SPÄTER LÖSCHEN!!!
	 */
	// Er tut was, es dauert!
			System.out.println("Corpus: "+i+"/"+index.size()+" :::::: Lexikonaufnahme: "+lexicon.size()+"/"+i);
			}
		
	//currentLexicon nimmt nur alle Wörter auf, die mindestens zweimal im Corpus vorkommen
		Lexicon currentLexicon = new Lexicon();
		for (int i = 0; i < lexicon.size(); i++) {
			if (lexicon.get(i).getFrequency()>1) {
				currentLexicon.add(lexicon.get(i));
			}
			
		}
		
		
		FrequencyComparator fc = new FrequencyComparator();
		
		
		System.out.println("Sorting...");
		lexicon.sort(fc);
		
		
		
		System.out.println("Lexikongröße beträgt "+lexicon.size()+" Wörter");
		
		try {
			Lexicon.saveLexicon(lexicon, "SpinPhone.lex");
		} catch (LexiconSerializationException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void loadLexicon(String lexFilePath) {
		
		System.out.println(lexFilePath);
		if (!lexFilePath.isEmpty()) {
			try {
				this.lexicon=Lexicon.loadLexicon();
			} catch (ClassNotFoundException e) {				
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
		
	}
	
	// Mock-Funktion
	public void saveLexicon(Lexicon lexicon, String lexFilePath) {
		
		//hier wird ein Lexikon-Object einfach so gespeichert
		try {
			Lexicon.saveLexicon(lexicon, lexFilePath);
		} catch (LexiconSerializationException e) {			
			e.printStackTrace();
		}
		//return null;
		
	}
	
	/**
	 * Mit der getAlternative() wird die LexFilter-Methode getAlternativeWord() übergeben. Gleichzeitig zählt ein @param den AlternativeCounter hoch, der bei jedem
	 * anderen Button wieder auf 0 gestellt werden soll.
	 */
	@Override
	public String getAlternative() {
		System.out.println("GA.LEXIKON: "+this.lexicon.size()+"GA.LASTNUMBER: "+this.lastNumber+"GA.currentWord: "+this.currentWord);
		this.alternativeCounter++;
		LexFilter lf = new LexFilter();
		System.out.println("getAlternative Word1: "+this.currentWord);
		this.currentWord=lf.getAlternativeWord(this.currentLexicon, this.positionCounter, this.currentWord, this.alternativeCounter);
		System.out.println("getAlternative Word2: "+this.currentWord);
		return this.currentWord;
	}

	@Override
	public void learn(String newWord) {
		
		this.alternativeCounter=0; //siehe getAlternative()
		System.out.println(this.lexicon.size());
		WordObject word = new WordObject(newWord);
		this.lexicon.add(word);
		saveLexicon(lexicon, "SpinPhone.lex");
		System.out.println(this.lexicon.size());
		System.out.println("learn()");
		
		
		
		
		
	}

	@Override
	public void asteriskButtonPressed() {
		this.alternativeCounter=0; //siehe getAlternative()
		// TODO *-Button = Zahlenmodus
		
	}

	@Override
	public void numberSignButtonPressed() {
		this.alternativeCounter=0; //siehe getAlternative()
		// TODO #-Button = Groß- und Kleinschreibung
		
		
	}

	@Override
	public String delButtonPressed() {
		this.alternativeCounter=0; //siehe getAlternative()
		
		if(this.lexiconList.size()<=1) {
			System.out.println("DeleteButton on no or one char");
			this.positionCounter=0; 
			this.currentWord=null;
			this.currentLexicon=lexicon;
			this.lexiconList.clear();
		}
	
		
		else {
			System.out.println("DeleteButton on multiple chars");
			this.positionCounter--;
			this.currentLexicon = this.lexiconList.get(this.lexiconList.size()-2);
			LexFilter lf = new LexFilter();
			this.currentWord = lf.getMostFrequencyWord(this.currentLexicon, this.lexiconList.size()-2, this.currentWord, this.lastNumber); //Änderung
			this.lexiconList.remove(lexiconList.size()-1);
			
		}
		 //wenn der LÖSCHEN-BUTTON gedrückt wird, muss eine Stelle abgezogen werden
		return this.currentWord;
	}

	@Override
	public void wordCompleted() {
		this.alternativeCounter=0; //siehe getAlternative()
		this.positionCounter=0; 
		this.currentWord=null;
		this.lexiconList.clear();
		//ist ein Wort komplettiert, kommt ein neues Wort, die Position wird auf 0 gestellt
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAuthorName() {
		
		String authors = "Mark Eschweiler und Thomas Baur";
		return authors;
	}

}
