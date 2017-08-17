package t12.spinphone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.omg.Messaging.SyncScopeHelper;

public class TMT12Interpreter implements T12Interpreter {
	
	public Corpus corpus = new Corpus();
	
	public List<String> index = new ArrayList<String>();

	@Override
	public String buttonPressed(int number) {
		// TODO Auto-generated method stub
		return null;
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
		
		Lexicon lexicon = new Lexicon();
		for (int i = 0; i < index.size(); i++) {
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
						//System.out.println("Gotcha!"+lexicon.get(j).getWord()+"|"+lexicon.get(j).getFrequency());
					}	
				}
				if (found == false)
				{
					lexicon.add(word);
				}
			}
	// Er tut was, es dauert!
			System.out.println("Corpus: "+i+"/"+index.size()+" :::::: Lexikonaufnahme: "+lexicon.size()+"/"+i);
			}
			
		for (int j = 0; j < lexicon.size(); j++) {
			System.out.println(lexicon.get(j).getWord()+"|"+lexicon.get(j).getKey()+"|"+lexicon.get(j).getFrequency());
			
		}
		System.out.println("Lexikongröße: "+lexicon.size()+" Wörter");
		//saveLexicon();
		
	}

	@Override
	public void loadLexicon(String lexFilePath) {
		
		// TODO Auto-generated method stub
		
	}
	// Mock-Funktion
	public Lexicon saveLexicon() {
		
		//hier wird ein Lexikon-Object einfach so gespeichert
		return null;
		
	}
	
	
	@Override
	public String getAlternative() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void learn(String newWord) {
		// TODO Auto-generated method stub
		// 1. Word-String in bestehendes Lexikon hinzufügen
		// 2. Lexikon speichern
		//saveLexicon()
		
	}

	@Override
	public void asteriskButtonPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void numberSignButtonPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String delButtonPressed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void wordCompleted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAuthorName() {
		
		String authors = "Mark Eschweiler und Thomas Baur";
		return authors;
	}

}
