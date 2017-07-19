package t12.phones.console;

import t12.phones.NoInterpreterException;
import t12.spinphone.T12Interpreter;
import t12.util.KeyConverter;

/**
 * <p>
 * Die Consolenversion des Telefons. Diese bietet sich zum testen und debuggen
 * besser an als die GUI-Variante, da hier keine Eingaben gemacht werden müssen,
 * sondern alles durch direkte Methodenaufrufe geschieht, d.h. die Kontrolle
 * darüber, wie das Programm abläuft, liegt bei Ihnen und ist in der Methode
 * "useConsolePhone" der Klasse "Application" festgelegt, wo Sie
 * selbstverständlich weitere Aufrufe hinzufügen können.
 * </p>
 * 
 * <p>
 * Ebenfalls wird diese Version des Telefons vom JUnit-Test "PhoneTests"
 * benutzt.
 * </p>
 * 
 * <p>
 * Contributors: <br>
 * Stephan Schwiebert - Initial API and implementation (2006)<br>
 * Mihail Atanassov - Refactoring (2015)
 * </p>
 * 
 * @see t12.Application#useConsolePhone
 * @see t12.tests.PhoneTests
 * 
 */
public class ConsolePhone {

	/**
	 * Der T12Interpreter, der in dieser Klasse genutzt wird.
	 * Mit der Methode "connectToT12()" wird das Telefon initialisiert.
	 * @see t12.Application#useConsolePhone
	 * @see t12.tests.PhoneTests#setUp()
	 */
	private T12Interpreter interpreter;

	/**
	 * Verbindet diese Klasse mit einem T12Interpreter. Der übergebene
	 * T12Interpreter darf nicht null sein.
	 * @param interpreter der T12Interpreter, der genutzt werden soll.
	 * @throws NoInterpreterException falls der übergebene Interpreter null ist.
	 */
	public void connectToT12(T12Interpreter interpreter) {
		if(interpreter == null) {
			throw new NoInterpreterException();
		}
		this.interpreter = interpreter;
		System.out.println("SpinPhone - Consolenversion gestartet.");
	}

	/**
	 * Erzeugt ein Lexikon aus den Dateien im Verzeichnis lexDirectory,
	 * und speichert dieses in der Datei lexFile, indem beide Parameter
	 * an die Methode generateLexicon() des T12Interpreters übergeben
	 * werden.
	 * @param lexDirectory Der Pfad zum Verzeichnis der Textdateien
	 * @param lexFile Der Pfad zur Lexikondatei
	 * @throws NoInterpreterException falls kein T12Interpreter gesetzt wurde.
	 * @throws IllegalArgumentException falls einer der Parameter null ist.
	 * @see t12.spinphone.T12Interpreter#generateLexicon(String, String)
	 */
	public void createLexicon(String lexDirectory, String lexFile) {
		if(interpreter == null) {
			throw new NoInterpreterException();
		}
		if(lexDirectory == null || lexFile == null) {
			throw new IllegalArgumentException("Die übergebenen Parameter dürfen nicht null sein!");
		}
		System.out.println("*****");
		System.out.println("ConsolePhone: Erzeuge Lexikon aus Dateien in " + lexDirectory);
		interpreter.generateLexicon(lexDirectory, lexFile);
	}

	/**
	 * Lädt das Lexikon aus der Datei mit dem übergebenen Pfad, indem
	 * die Methode loadLexicon() des T12Interpreters aufgerufen wird.
	 * 
	 * @param lexFilePath Der Pfad zur Lexikondatei
	 * @throws NoInterpreterException falls kein T12Interpreter gesetzt wurde.
	 * @throws IllegalArgumentException falls der übergebene Pfad null ist.
	 * @see t12.spinphone.T12Interpreter#loadLexicon(String)
	 */
	public void loadLexicon(String lexFilePath) {
		if(interpreter == null) {
			throw new NoInterpreterException();
		}
		if(lexFilePath == null) {
			throw new IllegalArgumentException("Der Pfad zur Lexikondatei darf nicht null sein!");
		}
		System.out.println("*****");
		System.out.println("ConsolePhone: Lade Lexikon: " + lexFilePath);
		interpreter.loadLexicon(lexFilePath);
	}

	/**
	 * Der übergebene String wird zunächst in T9-Zahlencodes
	 * konvertiert und anschließend Zahl für Zahl "eingegeben",
	 * indem für jede Zahl die Methode buttonPressed() des
	 * T12Interpreters aufgerufen wird. Nachdem alle Zahlen
	 * eingegeben wurden (und auch bei Whitespaces im übergebenen
	 * Wort), wird die Methode wordCompleted() des T12Interpreters
	 * aufgerufen. Schließlich wird der String, der zuletzt von
	 * der Methode buttonPressed() zurückgegeben wurde, von dieser
	 * Methode zurückgegeben.
	 * @param word Ein Wort, das eingegeben werden soll, z.B. "hallo".
	 * @return Das Wort, das erkannt wurde, z.B. "hallo", oder null, falls kein Wort gefunden wurde.
	 * @throws NoInterpreterException falls kein T12Interpreter gesetzt wurde.
	 * @throws IllegalArgumentException falls word == null.
	 * @see t12.spinphone.T12Interpreter#buttonPressed(int)
	 * @see t12.spinphone.T12Interpreter#wordCompleted()
	 */
	public String typeAsNumbers(String word) {
		if(interpreter == null) {
			throw new NoInterpreterException();
		}
		if(word == null) {
			throw new IllegalArgumentException("Das übergebene Wort darf nicht null sein!");
		}
		System.out.println("*****");
		word = word.toLowerCase();
		System.out.println("ConsolePhone: Tippe diesen String ein: " + word);
		String current = null;
		for(int i = 0; i < word.length(); i++) {
			int number = KeyConverter.convertToNumber(word.charAt(i));
			if(number == -1) {
				interpreter.wordCompleted();
				System.out.println("ConsolePhone: Wortende erreicht.");
			} else {
				current = interpreter.buttonPressed(number);
				System.out.println("ConsolePhone: " + current);
			}
		}
		interpreter.wordCompleted();
		return current;
	}

	/**
	 * Diese Methode versucht, eine Alternative zum zuvor eingegebenen
	 * Wort zu finden und diese auszugeben. Dafür wird die Methode
	 * "getAlternative()" des T12Interpreters genutzt. 
	 * @return Eine Alternative zum aktuellen Wort, oder null, falls es keine Alternative im Lexikon gibt.
	 * @see t12.spinphone.T12Interpreter#getAlternative()
	 * @throws NoInterpreterException falls kein T12Interpreter gesetzt wurde.
	 */
	public String displayAlternative() {
		if(interpreter == null) {
			throw new NoInterpreterException();
		}
		System.out.println("*****");
		System.out.println("ConsolePhone: Suche nach Alternative");
		String alternative = interpreter.getAlternative();
		System.out.println("ConsolePhone: Alternative: " + alternative);
		return alternative;
	}

	/**
	 * Diese Methode dient dazu, ein neues Wort lernen zu lassen, indem
	 * das übergebene Wort an die Methode "learn" des T12Interpreters
	 * weitergeleitet wird. Es wird davon ausgegangen, dass das Wort
	 * den Anforderungen entspricht, d.h. das es keine Satzzeichen oder
	 * Whitespaces enthält usw.
	 * @param newWord Das Wort, das gelernt werden soll.
	 * @throws NoInterpreterException falls kein T12Interpreter gesetzt wurde.
	 * @throws IllegalArgumentException falls das übergebene Wort null ist.
	 * @see t12.spinphone.T12Interpreter#learn(String)
	 */
	public void learn(String newWord) {
		if(interpreter == null) {
			throw new NoInterpreterException();
		}
		if(newWord == null) {
			throw new IllegalArgumentException("Das übergebene Wort darf nicht null sein!");
		}
		System.out.println("*****");
		System.out.println("ConsolePhone: Neues Wort lernen: " + newWord);
		interpreter.learn(newWord.toLowerCase());
		System.out.println("ConsolePhone: Wort gelernt.");
	}
	
	

}
