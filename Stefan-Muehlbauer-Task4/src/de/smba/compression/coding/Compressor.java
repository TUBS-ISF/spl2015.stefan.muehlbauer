package de.smba.compression.coding;

import java.util.Map;

/**
 * Utility class which is processing the compression/decompression for a given
 * coding.
 * 
 * @author Stefan Mühlbauer
 *
 */
public class Compressor implements ICompressor {

	public Compressor() {
		
	}
	
	/**
	 * Compresses a String based on the given coding.
	 * 
	 * @param Map
	 *            [String, String] coding
	 * @param String
	 *            toEncode
	 * @return String encoded
	 */
	public String compress(Map<String, String> coding, String toEncode) {
		StringBuffer encoded = new StringBuffer();

		for (char c : toEncode.toCharArray()) {
			encoded.append(coding.get(c + ""));
		}

		return encoded.toString();
	}
}
