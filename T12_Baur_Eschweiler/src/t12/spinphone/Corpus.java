package t12.spinphone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.BreakIterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Corpus {
	
	/**
	 * Die Klasse repräsentiert den Wortkorpus, der aus den vorhandenen Textdateien
	 * zusammengebaut wird. Enthält alle nötigen Methoden, um Dateien zu durchcrawlen
	 * und deren Texte herauszufiltern und anschließend alle Wörter als ganzes zu tokanisieren.
	 * @author Thomas Baur
	 */
	
	/**
	 * List<File> dateien
	 * ArrayList mit allen Dateien, die Text enhalten
	 */
	public List<File> dateien = new ArrayList<>();
	
	private final static String ENCODING = "UTF-8";
	
    private final static Integer MAX_WORD_LENGTH = 20;
	
	/**
	 * Gibt rekursiv alle Dateien die sich innerhalb pathToTexts befinden. Dabei sollen nur ".txt"-Dateien
	 * in die zu rueckgegebene Liste aufgenommen werden
	 * 
	 * @param   String pathToTexts  
	 * 			Verzeichnis zu den Texten
	 * @return  List<String> dateien  
	 * 			Alle txt-Dateien aus dem Verzeichnis pathToTexts
	 * @author  Thomas Baur
	 */
	public List<File> crawlFilesFromPath(String pathToTexts) {
		
		File file = new File(pathToTexts);
		
		
		//pruefen,ob das uebergebene Verzeichnis nicht leer oder kein Verzeichnis ist...
		if (pathToTexts == null || !file.isDirectory())
            return null;
		//Alle Dateien auflisten
        File[] fileArray = file.listFiles();
       
		for (File f : fileArray) {
						
            if (f.isDirectory()) {
                crawlFilesFromPath(f.getPath());                
            } 
            
            // Nur .txt-Dateien der Liste hinzufuegen
            if (f.getName().endsWith(".txt")) {
            	this.dateien.add(f);
            }
            
        }		
				
		return this.dateien;
		
	}
	
	
	/**
	 * Durchsucht eine gegebene Datei (File file) mit dem BufferedReader zeilenweise
	 * und gibt den Text als Ganzes zurueck in einem stringBuffer.toString()
	 * 
	 * 
	 * @param File file
	 * @param String encoding
	 * @return String
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author Thomas Baur
	 */
	public static String getText(File file, String encoding)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		
		//mit dem BufferedReader die Datei durchlaufen
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), encoding)
				);
		StringBuffer stringBuffer = new StringBuffer();
		String line = "";
		while ((line = br.readLine()) != null) {
			
			stringBuffer.append(line).append(System.lineSeparator());
		}
		br.close();
		return stringBuffer.toString();
	}
	
	
	/**
	 * Ruft die Methode @see t12.spinphone.Corpus.getText() auf und zerlegt den Text in einzelne Tokens.
	 * Dabei kann nach bestimmten Kriterien gefiltert werden, sodass einzelne Zeichen eliminiert
	 * werden und nicht in die Rueckgabe beruecksichtigt werden.
	 * 
	 * @author Thomas Baur
	 * @param  List<File> files
	 * 		   Eine Liste von Dateien
	 * @return List<String> words
	 * 		   Alle tokanisierten Woerter
	 * @throws IOException
	 */
	public List<String> getTokensFromFiles(List<File> files, Boolean filter) throws IOException {
		List<String> words = new ArrayList<String>();
				
		for (int i=0; i < files.size(); i++) {
			
			String text = getText(files.get(i), Corpus.ENCODING); //System.out.println(text);	
			if (filter == true) {
				String regexMultipleChars = "(.)(\\1{1,1})\\1*";
				
				//Alles aus dem Text entfernen - entfernt u.a Satzzeichen und Zahlen
				text = text.replaceAll("[0-9]", "").replaceAll("\\p{Punct}", "");
					
				
			}

			// Wir holen eine Instanz eines BreakIterators, der auf Wortebene den
			// Eingabetext "text" tokenisiert und setzen den sprachlichen Kontext.
			BreakIterator iterator = BreakIterator.getWordInstance(Locale.GERMAN);
			// Wir setzen den zu scannenden Text
			iterator.setText(text); 
			int start = iterator.first();
			int end = iterator.next();
			
			while (end != BreakIterator.DONE) {
				//Text in einzelne Wörter zerteilen und in Kleinbuchstaben umwandeln
				String subStringFromText = text.substring(start, end).toLowerCase(Locale.GERMAN);
				
				//Alle Wörter, die drei aufeinander folgende, gleiche Zeichen enthalten, sollen hier vorbehandelt werden
				Pattern p = Pattern.compile("(.)(\\1{1,2})\\1*");
				Matcher m = p.matcher(subStringFromText);
				if (m.find() == true) {
					subStringFromText = m.replaceAll("$1$2");
				}
				
				/**
				 *  Keine Leerzeichen oder White-Space-Character in die Liste aufnehmen und keine Wörter mit
				 * aufnehmen, die größer sind als die maximale zulässige Zahl Zeichen <code>MAX_WORD_LENGTH</code>
				 * @see: https://stackoverflow.com/questions/15625629/regex-expressions-in-java-s-vs-s
				 */
				if (! subStringFromText.matches("\\s+") && 
						subStringFromText.length() <= Corpus.MAX_WORD_LENGTH) {
					
					words.add(subStringFromText);
					
				} else {
					//nix tun hier
				}					
			
	
				start = end;
				end = iterator.next();
		
			}
		    
		}
		
		return words;
		
	}

}
