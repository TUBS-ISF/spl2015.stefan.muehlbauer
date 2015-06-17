package de.smba.compression.coding;

import java.util.Map;

public interface ICompressor {
	public String compress(Map<String, String> coding, String toEncode);
}
