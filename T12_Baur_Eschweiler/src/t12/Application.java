package t12;

import t12.phones.console.ConsolePhone;
import t12.phones.gui.SpinPhone;
import t12.spinphone.T12Interpreter;

/**
 * <p>
 * Diese Klasse enthält die main-Methode des Programms. Hier werden GUI und
 * Programmlogik miteinander verknüpft.
 * </p>
 * 
 * <p>
 * Eine Alternative finden Sie im JUnit-Test "PhoneTests", mit der Sie
 * verschiedene Funktionen komfortabel testen können.
 * </p>
 * 
 * <p>
 * Contributors:<br>
 * Stephan Schwiebert - Initial API and implementation (2006)<br>
 * Mihail Atanassov - Refactoring (2015)
 * </p>
 */
public class Application {

	/**
	 * <p>
	 * Main-Methode des Programms. Hier können Sie einstellen, ob die
	 * GUI-Variante des Programms gestartet werden soll oder die Consolen-
	 * Variante.
	 * </p>
	 * 
	 * <p>
	 * Die zweite Variante ist zu Beginn zum Programmieren und Fehlersuchen
	 * besser geeignet.
	 * </p>
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		useGUIPhone();
		// useConsolePhone();
	}

	/**
	 * <p>
	 * Diese Methode startet die GUI-Variante des Telefons. In dieser Methode
	 * wird festgelegt, welcher T12Interpreter benutzt werden soll. Als Vorlage
	 * dient <code>T12InterpreterImpl.java</code>. Ändern Sie dies so, dass Ihre
	 * Implementation von T12Interpreter genutzt wird.
	 * </p>
	 * 
	 * @see SpinPhone
	 * @see T12Interpreter
	 */
	private static void useGUIPhone() {
		SpinPhone phone = new SpinPhone();
		// Folgende Zeile ändern ( = new MeinT12Interpreter() o.ä.)
		T12Interpreter interpreter = null;
		phone.connectToT12(interpreter);
	}

	/**
	 * <p>
	 * Diese Methode simuliert T12 mit Hilfe der Klasse
	 * {@code ConsolePhone.java}. Dabei werden alle Funktionen, die getestet
	 * werden sollen, für ein Objekt der Klasse ConsolePhone aufgerufen:
	 * </p>
	 * <code>{@link t12.phones.console.ConsolePhone#createLexicon}</code><br>
	 * <code>{@link t12.phones.console.ConsolePhone#typeAsNumbers}</code><br>
	 * <p>
	 * Sehen Sie sich die Methode im Quellcode an - hier können Sie schnell
	 * eigene Tests und Methodenaufrufe hinzufügen, und hier müssen Sie Ihre
	 * Klasse, die T12Interpreter implementiert, einbinden.
	 * </p>
	 * 
	 * @see ConsolePhone
	 * @see T12Interpreter
	 */
	public static void useConsolePhone() {
		ConsolePhone phone = new ConsolePhone();
		// Folgende Zeile ändern ( = new MeinT12Interpreter() o.ä.)
		T12Interpreter interpreter = null;
		phone.connectToT12(interpreter);

		phone.createLexicon("data", "SpinPhone.lex");

		// phone.loadLexicon("SpinPhone.lex");

		// phone.typeAsNumbers("h");
		// phone.typeAsNumbers("ha");
		// phone.typeAsNumbers("hal");
		// phone.typeAsNumbers("hall");
		// phone.typeAsNumbers("halla");

		// phone.typeAsNumbers("v");
		// phone.typeAsNumbers("vo");
		// phone.typeAsNumbers("vor");

		// phone.displayAlternative();
		// phone.learn("Informationsverarbeitung");
		// phone.typeAsNumbers("Informationsverarbeitung");
	}

}
