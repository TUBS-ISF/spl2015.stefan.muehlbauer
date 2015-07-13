package de.smba.compression.coding; 

import java.util.Map; 
/**
 * Interface for all coding factories.
 * 
 * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 *
 */
public interface ICodingFactory {
	
	/**
	 * Factory method to be implemented.
	 * @param path
	 * @return coding
	 */
	public Map<String, String> buildCoding(String path);
	
	public Map<String, String> buildCodingFromText(String text);


}
