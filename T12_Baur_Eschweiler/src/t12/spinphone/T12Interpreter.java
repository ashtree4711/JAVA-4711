package t12.spinphone;

/**
 * <p>
 * Dieser Interface müssen Sie implementieren, damit Ihr Programm die Befehle
 * der "Telefon-GUI" annehmen kann. Eine "Referenz-Implementation" ist die
 * Klasse ExampleT12Interpreter, in der zwar keine Programmlogik implementiert
 * ist, Sie jedoch sehen können, welche Methode wann aufgerufen wird, indem Sie
 * die "Tasten" des Telefons betätigen.
 * </p>
 * 
 * Contributors: <br>
 * Stephan Schwiebert - Initial API and implementation <br>
 * Mihail Atanassov - Refactoring (2015)
 */
public interface T12Interpreter {

	/**
	 * Diese Methode wird aufgerufen, wenn eine der Zifferntasten 1 bis 9 des
	 * Tastenblocks gedrückt wird. Die wahrscheinlichste Zeichenkette muss von
	 * der Methode zurückgegeben werden.
	 * 
	 * @param number
	 *            Die gedrückte Zifferntaste
	 * 
	 * @return Die wahrscheinlichste Zeichenkette zur bisher eingegebenen
	 *         Ziffernfolge, oder null, wenn keine Zeichenkette der Ziffernfolge
	 *         entspricht.
	 */
	public String buttonPressed(int number);

	/**
	 * Diese Methode wird aufgerufen, wenn das Menu "Lexikon generieren"
	 * ausgewählt wird. Ihre Aufgabe ist es, aus den Texten, die im Verzeichnis
	 * pathToTexts liegen, ein T9-Lexikon zu erzeugen und in der Datei
	 * lexFileDestination zu speichern.
	 * 
	 * @param pathToTexts
	 *            Verzeichnis zu den Texten
	 * @param lexFileDestination
	 *            Lexikondatei
	 */
	public void generateLexicon(String pathToTexts, String lexFileDestination);

	/**
	 * Diese Methode wird aufgerufen, wenn der Menupunkt "Lexikon laden"
	 * ausgewählt wird, oder wenn Sie in den Einstellungen den Punkt "Lexikon
	 * automatisch laden" aktiviert haben. In beiden Fällen müssen Sie dafür
	 * sorgen, dass das Lexikon lexFilePath geladen wird.
	 * 
	 * @param lexFilePath
	 *            Pfad zur Lexikondatei
	 */

	public void loadLexicon(String lexFilePath);

	/**
	 * Diese Methode wird aufgerufen, wenn die "alternative"-Taste des Telefons
	 * gedrückt wird. Es soll die nächstbeste Alternative zum aktuellen Wort
	 * gefunden werden, diese muss dann an die Telefon-GUI zurückgegeben werden.
	 * 
	 * @return Die nächste Alternative zu einem Wort, oder null, falls keine
	 *         weitere vorhanden ist.
	 */
	public String getAlternative();

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer des Programms den
	 * Menupunkt "Neues Wort lernen" aufruft, um dem System ein neues Wort
	 * beizubringen.
	 * 
	 * @param newWord
	 *            Das neue Wort
	 */
	public void learn(String newWord);

	/**
	 * Diese Methode müssen Sie implementieren, in ihr muss jedoch nicht
	 * zwingend Code ausgeführt werden. Sie wird aufgerufen, wenn der
	 * Asterisk-Button (*) gedrückt wird, und Sie können Sie dazu nutzen,
	 * zusätzliche Funktionen in Ihr Telefon einzubauen.
	 */
	public void asteriskButtonPressed();

	/**
	 * Diese Methode müssen Sie implementieren, in ihr muss jedoch nicht
	 * zwingend Code ausgeführt werden. Sie wird aufgerufen, wenn der #-Button
	 * gedrückt wird, und Sie können Sie dazu nutzen, zusätzliche Funktionen in
	 * Ihr Telefon einzubauen.
	 */
	public void numberSignButtonPressed();

	/**
	 * Diese Methode wird aufgerufen, wenn der "löschen"-Button gedrückt wird.
	 * Das zuletzt eingegebene Zeichen soll gelöscht werden, und es soll die
	 * wahrscheinlichste Zeichenfolge, die der (reduzierten) Eingabe entspricht,
	 * zurückgegeben werden.
	 * 
	 * @return die wahrscheinlichste Zeichenfolge nach der Reuduzierung, oder
	 *         null, falls das letzte/erste Zeichen gelöscht wurde
	 */
	public String delButtonPressed();

	/**
	 * Wird aufgerufen, wenn ein Wort eingegeben wurde und die Leertaste (bzw.
	 * die Taste "0" am Handy) gedrückt wurde. Ihr Programm muss dafür sorgen,
	 * dass der "Zwischenspeicher", der die bisher eingegebene Ziffernfolge
	 * enthält, zurückgesetzt wird.
	 */
	public void wordCompleted();

	/**
	 * Implementieren Sie diese Methode, indem Sie einfach Ihren Namen
	 * zurückgeben. Danach erscheinen Sie als Autor des Programms im
	 * "Über dieses Programm"-Dialog.
	 * 
	 * @return Ihr Name
	 */
	public String getAuthorName();

}
