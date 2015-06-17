package de.smba.compression.analysis;

import java.util.Map;

/**
 * Interface for the analysers.
 * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 *
 */
public interface IAnalyser {

	public Map<String, Integer> analyseFrequency(String text);
	
}
