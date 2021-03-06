package t12.tests;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import t12.phones.console.ConsolePhone;
import t12.spinphone.T12Interpreter;
import t12.spinphone.TMT12Interpreter;

/**
 * <p>
 * Eine Klasse mit JUni-Tests. Wählen Sie den Test, den Sie ausführen möchten,
 * im Package Explorer oder im Outline View von Eclipse aus (z.B.
 * testCreateLexicon()), und wählen Sie im Kontextmenu den Punkt <br>
 * "Run As... &gt; JUnit Test". Daraufhin wird die setUp()-Methode dieser Klasse
 * und anschließend der gewählte Test ausgeführt. So können Sie schnell prüfen,
 * ob Ihre Implementation arbeitet, wie gewünscht. Wenn Sie statt einer der
 * test...-Methoden die Klasse selbst auswählen, werden alle Tests der Klasse
 * ausgeführt.
 * </p>
 * 
 * <p>
 * Eigene Tests können Sie ebenfalls hinzufügen, diese müssen lediglich mit
 * "test" beginnen, um von JUnit als Tests erkannt zu werden.
 * </p>
 * 
 * <p>
 * Als Telefon wird die Klasse ConsolePhone benutzt, als T12Interpreter ist in
 * der Vorlage der ExampleT12Interpreter eingestellt - dies können Sie in der
 * Methode "setUp" ändern und Ihre Implementation testen lassen.
 * </p>
 * 
 * <p>
 * Contributors: <br>
 * Stephan Schwiebert - Initial API and implementation <br>
 * Mihail Atanassov - Refactoring
 * </p>
 * 
 * @see t12.phones.console.ConsolePhone
 * 
 */
public class PhoneTests extends TestCase {

	private ConsolePhone phone;
	private TMT12Interpreter interpreter;

	/**
	 * In der Setup-Methode wird ein neues ConsolePhone erzeugt und mit einem
	 * ExampleT12Interpreter verbunden. Damit Sie Ihre Implementation von
	 * T12Interpreter testen können, sollten Sie hier eine Instanz Ihrer eigenen
	 * Klasse erzeugen lassen.
	 */
	@Before
	public void setUp() {
		phone = new ConsolePhone();
		// Hier den eigenen T12Interpreter zuweisen:
		interpreter = new TMT12Interpreter();
		phone.connectToT12(interpreter);
		System.out.println("ConsolePhone und Interpreter verbunden.");
		phone.createLexicon("data", "SpinPhone.lex");
		phone.loadLexicon("SpinPhone.lex");
	}

	/**
	 * Testmethode für 't12.ConsolePhone.createLexicon(String, String)' Zunächst
	 * wird die Lexikon-Datei "SpinPhone.lex" gelöscht, falls sie existiert.
	 * Anschließend wird die Methode createLexikon von ConsolePhone aufgerufen.
	 * Der Test schlägt fehl, wenn nach dem Aufruf keine Lexikondatei namens
	 * "SpinPhone.lex" existiert, oder wenn die erzeugte Datei 0 Bytes groß ist.
	 */
	
	@Test
	public void testCreateLexicon() {
		File lexFile = new File("SpinPhone.lex");
		if (lexFile.exists()) {
			lexFile.delete();
		}
		phone.createLexicon("data", "SpinPhone.lex");
		super.assertTrue(lexFile.exists());
		super.assertTrue(lexFile.length() > 0);
	}
	
	

	/**
	 * Test der Methode ConsolePhone.loadLexicon(). Es wird versucht, ein
	 * Lexikon aus der Datei "SpinPhone.lex" zu laden.
	 */
	
	@Test
	public void testLoadLexicon() {
		phone.loadLexicon("SpinPhone.lex");
		super.assertTrue(new File("SpinPhone.lex").exists());
	}
	

	/**
	 * Test Methode für 't12.ConsolePhone.displayAlternative()' Zunächst wird
	 * das Wort "uns" eingegeben und geprüft, ob der zurückgegebene String null
	 * ist oder nicht. Anschließend wird nach einer Alternative gefragt, und es
	 * werden folgende Bedingungen überprüft: 1.) Die Alternative darf nicht
	 * null sein 2.) Die Alternative muss sich von der ersten Eingabe
	 * unterscheiden.
	 */
	
	@Test
	public void testDisplayAlternative() {
		String word = phone.typeAsNumbers("uns");
		System.out.println("word: "+word);
		super.assertNotNull(word);
		String word2 = phone.displayAlternative();
		
		System.out.println("word2: "+word2);

		super.assertNotNull(word2); //Assertion-Fehler hier!
		super.assertNotSame(word, word2);
		super.assertFalse(word.equals(word2));
	}
	

	/**
	 * Test Methode for 't12.ConsolePhone.learn(String)' Es wird geprüft, ob das
	 * Telefon in der Lage ist, ein neues Wort zu lernen. Zunächst wird
	 * "informationsverarbeitung" eingegeben und es wird geprüft, dass das Wort
	 * noch nicht vorhanden ist. Anschließend wird es mit ConsolePhone.learn()
	 * gelernt, dann wird geprüft, ob auf erneute Eingabe von
	 * "informationsverarbeitung" ein String zurückgegeben wird, der 1.) nicht
	 * null ist und 2.) "informationsverarbeitung" lautet.
	 */
	
	@Test
	public void testLearn() {
		String word = phone.typeAsNumbers("informationsverarbeitung ");//BEGRÜNDUNG LIEFERN
		super.assertNull(word); 
		phone.learn("informationsverarbeitung");
		word = phone.typeAsNumbers("informationsverarbeitung");
		super.assertNotNull(word);
		super.assertEquals(word, "informationsverarbeitung");
	}
	/**
	 * Test für die Methode t12.spinPhone.TMT12Interpreter.typerNumbers() und 
	 * t12.spinPhone.TMT12Interpreter.asteriskButtonPressed(). Zunächst wird die *-Taste
	 * ausgeführt, danach die Nummern "2333" mit den Wort "affe" simuliert. Zum Abschluss
	 * wird überprüft, ob die korrekten Zahlen auf dem Display erscheinen.
	 * 
	 * @author Mark Eschweiler
	 */
	@Test
	public void testNumberModus() {
		interpreter.asteriskButtonPressed();
		String numbers = phone.typeAsNumbers("affe");
		super.assertEquals(numbers, "2333");
		
	}
	

}
