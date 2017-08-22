package t12.spinphone;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import t12.phones.LexiconSerializationException;



public class Lexicon extends ArrayList<WordObject> implements Serializable{

	
	private static final long serialVersionUID = -3477170431168113034L;
	
	
	/**
	 * saveLexicon(Lexicon lexicon) speichert eine .lex-Datei mit den Daten des Objekts vom
	 * Typ Lexicon. Die Daten werden serialisiert abgespeichert.
	 * 
	 * @author thomas
	 * @param lexicon
	 * @throws LexiconSerializationException
	 */
	public static void saveLexicon(Lexicon lexicon, String lexFileDest) throws LexiconSerializationException {
		try {
			FileOutputStream fos = new FileOutputStream(lexFileDest);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(lexicon);
			oos.close();
		} catch (FileNotFoundException e) {
			throw new LexiconSerializationException("Failed to store file", e);
		} catch (IOException e) {
			throw new LexiconSerializationException(e);
		}
	}
	
	/**
	 * loadLexicon() - lädt eine vorhandene .lex-Datei mithilfe des FileInputStreams
	 * Die Funktion gibt eine Objekt-Repräsentation vom Typ Lexicon zurück.
	 * 
	 * @author thomas
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Lexicon loadLexicon() throws IOException, ClassNotFoundException {
		
		FileInputStream fis = new FileInputStream("SpinPhone.lex");
		BufferedInputStream bis = new BufferedInputStream(fis);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Lexicon lex = (Lexicon) ois.readObject();
		ois.close();
				
		return lex;
		
	}
	
	
}
