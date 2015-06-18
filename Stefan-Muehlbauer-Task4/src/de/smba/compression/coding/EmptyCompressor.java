package de.smba.compression.coding;

import java.util.Map;

/**
 * This compressor implementation indicates that the Compression feature 
 * was not selected.
 * 
 * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 *
 */
public class EmptyCompressor implements ICompressor {

	public String compress(Map<String, String> coding, String toEncode) {
		return null;
	}

}
