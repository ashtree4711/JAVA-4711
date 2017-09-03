package t12.spinphone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import t12.phones.LexiconSerializationException;
import t12.util.LexiconToolbox;
import t12.util.comparator.FrequencyComparator;

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
	private int buttonClicked = 0;
	private boolean numberModus;
	private boolean upperMode;

	/**
	 * Die Methode ist vom Interface vorgegeben und wird immer aufgerufen,
	 * wenn die Zahlentasten in der GUI benutzt werden.
	 * Bei jedem Aufruf werden zunächst Kontrollvariablen in der Console angezeigt.
	 * 
	 * this.alternativeCounter wird neu initialisiert @see getAlternative() 
	 * 
	 * @param this.numbermodus: Bei TRUE wird "number" an die @see typeNumber()-Methode und bei FALSE
	 * an die @see typeWord()
	 * @author Mark Eschweiler
	 */
	@Override
	public String buttonPressed(int number) {
		System.out.println("Überprüfe aktuelle Lexikongröße: "+this.lexicon.size()+" Wörter");
		System.out.println("Nummermodus: "+this.numberModus);
		System.out.println("Input: "+number);
		this.lastNumber=number;
		this.alternativeCounter=0; //siehe getAlternative()
		
		if (this.numberModus==true) {
			this.currentWord=typeNumbers(number);
		} else {
			this.currentWord=typeWord(number);
		}
		if (this.currentWord!=null&&this.numberModus!=true) {
			if (this.upperMode == true) {
				System.out.println(currentWord);
				this.currentWord=this.currentWord.toUpperCase();
			} else {
				this.currentWord=this.currentWord.toLowerCase();
			}
		}
		
		
		return this.currentWord;
	}
	/**
	 * Die Funktion delegiert den Umgang mit dem Input im Wortmodus. 
	 * Bei jedem neuen Wort ist der <code>this.positionCounter</code> auf 0 gesetzt. Die Methode 
	 * erzeugt in diesem Fall eine neue LexiconToolbox()-Klasse, die weitere Methoden zur Bearbeitung von 
	 * Input und Output bereitstellt.
	 * 
	 * Gleichzeitig wird das Hauptlexikon <code>this.lexicon</code> initial auf das <code>this.currentlexicon</code>
	 * kopiert. <code>this.currentLexicon</code> ist ein ständig kleiner werdendes Lexikon je größer die Eingabe wird. 
	 * Dies geschieht unter @see LexiconToolbox.filtering(), sodass in diesem Lexikon nur noch potenzielle Wörter entsprechend
	 * der aktuellen Zahlenfolge und auf dieser Basis noch alle möglichen enthalten sein sollen.
	 * 
	 * Die <code>this.lexiconList</code> ist eine ArrayList aus Lexika, die wiederum selbst eine ArrayList sind. Sie dient
	 * im Grunde als Backup, wenn Buchstaben gelöscht werden, muss ein gefiltertes Lexikon nicht neu generiert werden, sondern
	 * man greift einfach auf den vorherigen Stand zurück. 
	 * 
	 * @see filtering(): Das aktuelle/gefilterte Lexikon, die aktuelle Position und @param number werden übergeben, um das Lexikon
	 * zu verkleinern entsprechend des letzten Inputs und der Inputs zuvor.
	 * @see getMostFrequencyWord(): Hier wird häufigste Wort mithilfe des gefilterten Lexikons geholt und letztendlich an die GUI über buttonPressed() zurückgeholt
	 * 
	 * Zum Abschluss wird noch der positionCounter um 1 erhöht, damit die LexiconToolbox wissen, nach welcher Stelle gefiltert und
	 * sortiert werden soll.
	 * @param number
	 * @return String this.currentWord
	 * @author Mark Eschweiler
	 */
	private String typeWord(int number) {
		if (this.positionCounter==0) {
			LexiconToolbox lf = new LexiconToolbox();
			this.currentLexicon=this.lexicon;
			
			this.lexiconList = new LexiconList();
			
			this.currentLexicon=lf.filtering(this.currentLexicon, this.positionCounter, number);
			lexiconList.add(this.currentLexicon); 
			
			this.currentWord=lf.getMostFrequencyWord(currentLexicon, positionCounter, this.currentWord);
			System.out.print("Laenge " +this.currentWord.length());
		}
		else {
			LexiconToolbox lf = new LexiconToolbox();
			if(this.currentLexicon.size()!=0) {
				this.currentLexicon=lf.filtering(this.currentLexicon, positionCounter, number); // 2. hole gesamtes Lexicon (this.lexicon) 3. gib Lexicon in Filter (lexicon, position, buttonINT) --> RETURN FilteredLexicon
				lexiconList.add(this.currentLexicon);
				System.out.println("this.current: "+this.currentLexicon.size());
				this.currentWord=lf.getMostFrequencyWord(currentLexicon, positionCounter, this.currentWord);				
			}
			else {
				System.out.println("Keine Weiteren Einträge vorhanden");
			}
		}
		this.positionCounter++; //immer wenn eine Zahl gedrückt wird erhält das Wort eine neue Stelle
		return this.currentWord;
	}
	/**
	 * Die Funktion delegiert den Umgang mit dem Input im Zahlenmodus. 
	 * 
	 * @param number wird zunächst in einen String umgewandelt. Ist der String des aktuellen Wortes null, 
	 * ist es also noch kein Wort vorhanden. So wird der String des Inputs als aktuelles Wort gesetzt. Ist ein Wort bereits
	 * vorhanden wird ganz einfach der String des Inputs an das aktuelle Wort angefügt.
	 * @return String this.currentWord
	 * @author Mark Eschweiler
	 */
	private String typeNumbers(int number) {
		
		String newNumber=Integer.toString(number);
		if(this.currentWord==null) {
			this.currentWord=newNumber;
		}
		else {
			this.currentWord=this.currentWord+newNumber;
		}
		return this.currentWord;
	}
	
	/**
	 * Generiert das Lexikon anhand der .txt-Dateien. Dabei wird ein Pfad zu den txt-Dateien übergeben
	 * und die lexFileDestination, dem Speicherort an dem die Binärdatei die Daten der einzelnen Wörter
	 * aus dem Korpus abspeichert
	 * 
	 * @param  pathToTexts
	 * @param  lexFileDestination
	 * @author Thomas Baur
	 */
	@Override
	public void generateLexicon(String pathToTexts, String lexFileDestination) {
		// TODO Auto-generated method stub
		List<File> files = corpus.crawlFilesFromPath(pathToTexts);
		try {
			this.index = corpus.getTokensFromFiles(files, true);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		System.out.println(index.size());
//		for (int i=0; i < index.size(); i++) {
//			System.out.println(index.get(i));
//		}
		/*
		 * Während der Lexikonaufnahme wird überprüft, ob das Wort bereits vorhanden ist. Wenn nicht, wird es eingetragen,
		 * wenn doch, wird die Häufigkeit im passenden Wort-Objekt erhöht und kein neues Objekt erzeugt. Aktuell wird jedes 10. Objekt ins Lexikon aufgenommen
		 */
		
		//Lexicon lexicon = new Lexicon();
		//index.size() aus Testgründen auf 20000 geändert
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
						
						
						
					}	
				}
				if (found == false)
				{
					lexicon.add(word);
				}
			}
			
	/*
	 * Konsolenanzeige während Generierung
	 */
	
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
	
	/**
	 * Lädt das aktuelle Lexikon.
	 * 
	 * @param  lexFilePath Pfad zur lex-Datei, die alle generierten Binärdaten des Lexikons beinhaltet
	 * @author Thomas Baur
	 */
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
	
	/**
	 * Speichert ein Lexikon. Ruft die Methode @see {@link Lexicon#saveLexicon(Lexicon, String)} auf, die
	 * als Eingabeparameter das Lexicon-Object und den Dateipfad zur .lex-Datei bekommt, in der das Object
	 * als Binärdatei gespeichert werden soll.
	 * 
	 * @param lexicon
	 * @param lexFilePath
	 * @author Thomas Baur
	 */
	public void saveLexicon(Lexicon lexicon, String lexFilePath) {
		
		
		try {
			Lexicon.saveLexicon(lexicon, lexFilePath);
		} catch (LexiconSerializationException e) {			
			e.printStackTrace();
		}
				
	}
	
	/**
	 * Mit der getAlternative() wird die LexiconToolbox-Methode @see getAlternativeWord() angefordert. 
	 * Gleichzeitig zählt ein den <code>this.alternativeCounter</code> hoch, der bei jedem ButtonPressed()-Methodenaufruf wieder 
	 * auf 0 gestellt werden soll.
	 * @author Mark Eschweiler
	 */
	@Override
	public String getAlternative() {
		this.alternativeCounter++;
		LexiconToolbox lf = new LexiconToolbox();
		this.currentWord=lf.getAlternativeWord(this.currentLexicon, this.positionCounter, this.currentWord, this.alternativeCounter);
		return this.currentWord;
	}

	/**
	 * Die Methode dient ausschließlich dem Erlernen eines neuen Wortes und nicht der Erhöhung der Häufigkeit. Dies wird nur
	 * in wordCompleted() realisiert.
	 * @param newWord wird übergeben um das Wort abzuspeichern
	 * Das zu erlernende Wort wird zunächst in einen lowerCase verwandelt. Dann wird ein neues Wort-Object mit dem
	 * neuen Wort angelegt. Das Wort-Objekt wird dem Gesamtlexikon angefügt und sofort abgespeichert.
	 * 
	 * @author Mark Eschweiler
	 */
	@Override
	public void learn(String newWord) {
		
		this.alternativeCounter=0; //siehe getAlternative()
		newWord=newWord.toLowerCase();
		WordObject word = new WordObject(newWord);
		this.lexicon.add(word);
		saveLexicon(lexicon, "SpinPhone.lex");
		System.out.println("Ein neues Wort wurde erfolgreich eingetragen, die neue Lexikongröße beträgt "+this.lexicon.size()+" Wörter");
		
	}
	
	/**
	 * Es wird hier nur ein Schalter mit dem "*"-Button umgelegt, der zwischen Nummermodus und T9-Modus bestimmt. Alles weitere wird
	 * in @see buttonPressed() behandelt
	 * 
	 * @author Mark Eschweiler
	 */
	@Override
	public void asteriskButtonPressed() {
		this.alternativeCounter=0;
		if (this.numberModus==true) {
			this.numberModus=false;
		}
		else {
			this.numberModus=true;
		}
		System.out.println("Nummermodus: "+this.numberModus);
		
		
	}

	/**
	 * Hier soll auf Groß-/Kleinschreibung umgeschaltet werden
	 * 
	 * @author Thomas Baur
	 */
	@Override
	public void numberSignButtonPressed() {
		this.alternativeCounter=0; //siehe getAlternative()
		//Zähler für Buttonklicks hochzählen bei jedem Klick
		this.buttonClicked += 1;
		if (buttonClicked % 2 == 0) {
			upperMode = false;
		} else {
			upperMode = true;
		}
		
		System.out.println("aktuell: " + currentWord + " / " + buttonClicked + "  " + upperMode);		
		
		
	}
	
	/**
	 * Der Vorteil einer ArrayList aus ArrayLists erschließt insbesondere hier. Wird ein Buchstabe gelöscht, wird der
	 * vorletzte Eintrag anstatt des letzten Eintrages der <code>this.lexiconList</code> zum <code>this.currentLexicon</code>.
	 * Mithilfe des neuen "alten" <code>this.currentLexicon</code> wird wiederum das wahrscheinlichste Wort mit @see getMostFrequencyWord()
	 * angezeigt. 
	 * 
	 * Abschließend wird das letzte Lexikon aus der ArrayList gelöscht.
	 * 
	 * Insofern die <code>this.lexiconList</code> kleiner oder gleich 1 ist, wird die <code>this.lexiconList</code>
	 * zurückgesetzt und das <code>this.currentWord</code> leergelassen.
	 * 
	 * @author Mark Eschweiler
	 */
	@Override
	public String delButtonPressed() {
		this.alternativeCounter=0; //siehe getAlternative()
		
		if(this.lexiconList.size()<=1) {
			System.out.println("DeleteButton on no or one char");
			this.positionCounter=0; 
			this.currentWord="";
			this.currentLexicon=lexicon;
			this.lexiconList.clear();
		}
		else {
			System.out.println("DeleteButton on multiple chars");
			this.positionCounter--;
			this.currentLexicon = this.lexiconList.get(this.lexiconList.size()-2);
			LexiconToolbox lf = new LexiconToolbox();
			this.currentWord = lf.getMostFrequencyWord(this.currentLexicon, this.lexiconList.size()-2, this.currentWord); 
			this.lexiconList.remove(lexiconList.size()-1);	
		}
		return this.currentWord;
	}
	
	/**
	 * Wird aufgerufen wenn "0" gedrückt wird. Gemäß der Anforderungen wird das geschriebene Wort versiegelt und muss
	 * deshalb nicht mehr editierbar sein. Aus diesem Grund muss die Methode alles nur auf Anfang setzen, damit ein neues
	 * Wort geschrieben werden kann.
	 * 
	 * Zusätzlich erhöht die Methode die Häufigkeit des benutzten Wortes 
	 * @author Mark Eschweiler
	 */
	@Override
	public void wordCompleted() {
		
		for (int i = 0; i < this.lexicon.size(); i++) {
			if(this.lexicon.get(i).getWord()==this.currentWord) {
				System.out.println("Häufigkeit zuvor: "+this.lexicon.get(i).getFrequency());
				this.lexicon.get(i).raiseFrequency();
				System.out.println("Häufigkeit danach: "+this.lexicon.get(i).getFrequency());
			}	
		}
		saveLexicon(this.lexicon, "SpinPhone.lex");
		this.alternativeCounter=0;
		this.positionCounter=0; 
		this.currentWord=null;
		this.lexiconList.clear();
	}

	/**
	 * Zeigt die Namen der Programmautoren an, wenn man auf den Menüpunkt auf "Über dieses Programm..." klickt.
	 * 
	 * @author Thomas Baur
	 */
	@Override
	public String getAuthorName() {
		
		String authors = "Mark Eschweiler und Thomas Baur";
		return authors;
	}

}