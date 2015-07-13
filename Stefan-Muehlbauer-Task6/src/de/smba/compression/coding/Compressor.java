package de.smba.compression.coding;

import java.util.Map;

import de.smba.compression.coding.ICompressor;

/**
 * This compressor implementation indicates that the Compression feature was not
 * selected.
 * 
 * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 *
 */
public class Compressor implements ICompressor {

	public String compress(Map<String, String> coding, String toEncode) {
		return null;
	}

	public Compressor() {

	}

	public void identify() {

	}
}
