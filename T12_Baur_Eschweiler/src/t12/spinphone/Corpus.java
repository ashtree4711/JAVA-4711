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

public class Corpus {
	
	public List<File> dateien = new ArrayList<>();
	
	private final static String ENCODING = "UTF-8";
	
	//private String[] regexes = {"\\p{Punct}", "\\s", "@", "[0-9]"};
	
	/**
	 * Gibt rekursiv alle Dateien die sich innerhalb pathToTexts befinden. Dabei sollen nur ".txt"-Dateien
	 * in die zu rueckgegebene Liste aufgenommen werden
	 * 
	 * @author thomas
	 * @param   String pathToTexts  
	 * 			Verzeichnis zu den Texten
	 * @return  List<String> dateien  
	 * 			Alle txt-Dateien aus dem Verzeichnis pathToTexts
	 */
	public List<File> crawlFilesFromPath(String pathToTexts) {
		
		File file = new File(pathToTexts);
		
		
		//pruefen,ob das uebergebene Verzeichnis nicht leer oder kein Verzeichnis ist...
		if (pathToTexts == null || !file.isDirectory())
            return null;
		//Alle Dateien auflisten
        File[] fileArr = file.listFiles();
       
		for (File f : fileArr) {
						
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
	 * und gibt den Text als Ganzes zurueck in einem stringBuffer
	 * 
	 * @author thomas
	 * @param File file
	 * @param String encoding
	 * @return String
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
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
	 * Ruft die Methode getText auf und zerlegt den Text in einzelne Tokens.
	 * Dabei kann nach bestimmten Kriterien gefiltert werden, sodass einzelne Zeichen eliminiert
	 * werden und nicht in die Rueckgabe beruecksichtigt werden.
	 * 
	 * @author thomas
	 * @param  List<File> files
	 * 		   Eine Liste von Dateien
	 * @return List<String> words
	 * 		   Alle tokanisierten Woerter
	 * @throws IOException
	 */
	public List<String> getTokensFromFiles(List<File> files, Boolean filter) throws IOException {
		List<String> words = new ArrayList<String>();
		
		for (int i=0; i < files.size(); i++) {
			
			String text = getText(files.get(i), this.ENCODING); //System.out.println(text);	
			if (filter == true) {
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
				String subStringFromText = text.substring(start, end).toLowerCase(Locale.GERMAN);
				
				if (! subStringFromText.matches("\\s+")) {
					
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
