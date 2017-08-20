package t12.phones;

/*
 * LexiconSerializationException-Class
 * Für die Serialisation hinzugefügt. 
 */
public class LexiconSerializationException extends Exception {
	

	private static final long serialVersionUID = -7283445119038727533L;

	public LexiconSerializationException(String message, Exception cause) {
		super(message, cause);
	}

	public LexiconSerializationException(Exception cause) {
		super(cause);
	}

}
