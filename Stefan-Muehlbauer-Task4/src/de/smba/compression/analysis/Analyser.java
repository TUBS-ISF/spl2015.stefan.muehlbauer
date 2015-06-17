package de.smba.compression.analysis;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 * 
 */
public class Analyser implements IAnalyser {

	/** Private constructor */
	public Analyser() {
		super();
	}

	public Map<String, Integer> analyseFrequency(String text) {
		Map<String, Integer> frequency = new HashMap<String, Integer>();

		for (char c : text.toCharArray()) {
			if (frequency.keySet().contains(c + "")) {
				frequency.put(c + "", frequency.get(c + "") + 1);
			} else {
				frequency.put(c + "", 1);
			}
		}
		return frequency;
	}
}

