package de.smba.compression.analysis;

import java.util.Map;

/**
 * Interface for analysers.
 * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 *
 */
public interface IAnalyser {

	/**
	 * This method analyses the word frequency for a given String text.
	 * @param text Text
	 * @return Map<String, Integer> frequencies
	 */
	public Map<String, Integer> analyseFrequency(String text);
	
}