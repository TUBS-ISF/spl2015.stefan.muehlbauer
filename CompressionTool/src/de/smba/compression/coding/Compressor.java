package de.smba.compression.coding;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class Compressor {
	public static String compress(Map<String, String> coding, String toEncode) {
		StringBuffer encoded = new StringBuffer();
		
		for (char c : toEncode.toCharArray()) {
			encoded.append(coding.get(c+""));
		}
		
		return encoded.toString();
	}
	
	public static String decompress(Map<String, String> anticoding, String toDecode) {
		StringBuffer decoded = new StringBuffer();
		
		String symbol = "";
		for (int i = 0; i < toDecode.length(); i++) {
			
			symbol += toDecode.charAt(i)+"";
			
			
			if (anticoding.containsKey(symbol)) {
				decoded.append(anticoding.get(symbol));
				symbol = "";
			} 
		}
		
		return decoded.toString();
	}
}
