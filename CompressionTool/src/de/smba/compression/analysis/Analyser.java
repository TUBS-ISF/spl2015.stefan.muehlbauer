package de.smba.compression.analysis;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 * 
 */
public class Analyser {
	
	/** Singleton instance */
	private static Analyser instance = null;
	
	/** Private constructor */
	private Analyser() {
		super();
	}
	
	/** 
	 * Static method which returns the singleton instance
	 * @return instance Analyser instance
	 */
	public static Analyser getInstance() {
		if (instance == null) {
			instance = new Analyser();
		}
		return instance;
	}
	
	public Map<String, Integer> analyseFrequency(String text) {
		Map<String, Integer> frequency = new HashMap<String, Integer>(); 

		for (char c : text.toCharArray()) {
			if (frequency.keySet().contains(c+"")) {
				frequency.put(c+"", frequency.get(c+"") + 1);
			} else {
				frequency.put(c+"", 1);
			}
		}
		return frequency;
	}
	
	public String loadFile(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();	
		
		while (line != null) {
			sb.append(line);
	        sb.append(System.lineSeparator());
	        line = br.readLine();
		}
		br.close();
	    return sb.toString();
	}
	
	/**
	 * 
	 * @param args []
	 */
	public static void main(String args[]) {
		if (args.length == 1) {
			Analyser a = Analyser.getInstance();
			System.out.println(a.analyseFrequency(args[0]));
		} else if (args.length == 2) {
			if (args[0].equals("-p")) {
				//TODO path
			} else {
				//TODO raise help
			}
		}
	}
}
