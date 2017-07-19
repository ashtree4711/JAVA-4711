package t12.phones;

/**
 * Eine RuntimeException, die von den Klassen ConsolePhone und SpinPhone geworfen wird,
 * falls diese nicht mit einem T12Interpreter verbunden sind.
 *  
 * <p>
 * Contributors: <br>
 * Stephan Schwiebert - Initial API and implementation (2006)<br>
 * Mihail Atanassov - Refactoring (2015)
 * </p>
 *
 */
public class NoInterpreterException extends RuntimeException {
	
	private static final long serialVersionUID = 8902229494376891139L;

	public NoInterpreterException() {
		super("Kein T12Interpreter verfügbar! Prüfen Sie, ob Sie setT12Interpreter() richtig aufgerufen haben.");
	}

}
