package de.smba.compression.coding;

import java.util.Map;

/**
 * Utility class which is processing the compression/decompression for a given
 * coding.
 * 
 * @author Stefan Mühlbauer
 *
 */
public class Compressor {

	// #ifdef Compression
	/**
	 * Compresses a String based on the given coding.
	 * 
	 * @param Map
	 *            [String, String] coding
	 * @param String
	 *            toEncode
	 * @return String encoded
	 */
	public static String compress(Map<String, String> coding, String toEncode) {
		StringBuffer encoded = new StringBuffer();

		for (char c : toEncode.toCharArray()) {
			encoded.append(coding.get(c + ""));
		}

		return encoded.toString();
	}

	// #endif
	/**
	 * Decompresses a String based on a given coding.
	 * 
	 * @param anticoding
	 * @param toDecode
	 * @return decoded
	 */
	public static String decompress(Map<String, String> anticoding,
			String toDecode) {
		StringBuffer decoded = new StringBuffer();

		String symbol = "";
		for (int i = 0; i < toDecode.length(); i++) {

			symbol += toDecode.charAt(i) + "";

			if (anticoding.containsKey(symbol)) {
				decoded.append(anticoding.get(symbol));
				symbol = "";
			}
		}

		return decoded.toString();
	}
}
