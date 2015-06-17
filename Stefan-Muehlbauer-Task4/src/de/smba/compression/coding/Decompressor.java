package de.smba.compression.coding;

import java.util.Map;

public class Decompressor implements IDecompressor {
	
	public Decompressor() {
		
	}
	
	/**
	 * Decompresses a String based on a given coding.
	 * 
	 * @param anticoding
	 * @param toDecode
	 * @return decoded
	 */
	public String decompress(Map<String, String> anticoding,
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
