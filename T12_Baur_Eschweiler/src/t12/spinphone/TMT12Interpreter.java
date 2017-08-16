package t12.spinphone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TMT12Interpreter implements T12Interpreter {
	
	public Corpus Lex = new Corpus();
	
	public List<String> index = new ArrayList<String>();

	@Override
	public String buttonPressed(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateLexicon(String pathToTexts, String lexFileDestination) {
		// TODO Auto-generated method stub
		List<File> files = Lex.crawlFilesFromPath(pathToTexts);
		try {
			this.index = Lex.getTokensFromFiles(files, true);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		for(int i=0; i < index.size(); i++) {
		System.out.print(index.get(i) + "\r\n");
		}
	}

	@Override
	public void loadLexicon(String lexFilePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAlternative() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void learn(String newWord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void asteriskButtonPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void numberSignButtonPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String delButtonPressed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void wordCompleted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAuthorName() {
		
		String authors = "Mark Eschweiler und Thomas Baur";
		return authors;
	}

}
