package t12.util;

public class SimpleConverter {
	
	public String doConvert(String word) {
		word=word.toLowerCase();
		word=word.replace("a", "2");
		word=word.replace("b", "2");
		word=word.replace("c", "2");
		word=word.replace("d", "3");
		word=word.replace("e", "3");
		word=word.replace("f", "3");
		word=word.replace("g", "4");
		word=word.replace("h", "4");
		word=word.replace("i", "4");
		word=word.replace("j", "5");
		word=word.replace("k", "5");
		word=word.replace("l", "5");
		word=word.replace("m", "6");
		word=word.replace("n", "6");
		word=word.replace("o", "6");
		word=word.replace("p", "7");
		word=word.replace("q", "7");
		word=word.replace("r", "7");
		word=word.replace("s", "7");
		word=word.replace("t", "8");
		word=word.replace("u", "8");
		word=word.replace("v", "8");
		word=word.replace("w", "9");
		word=word.replace("x", "9");
		word=word.replace("y", "9");
		word=word.replace("z", "9");
		return word;
	
	}
}