package de.smba.compression.analysis;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Analyser {

	/**
	 * This method analyses the word frequency for a given String text.
	 * @param text Text
	 * @return Map<String, Integer> frequencies
	 */
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