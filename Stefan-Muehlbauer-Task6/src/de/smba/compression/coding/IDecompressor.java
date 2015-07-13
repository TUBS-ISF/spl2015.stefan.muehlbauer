package de.smba.compression.coding;

import java.util.Map;

public interface IDecompressor {

	public String decompress(Map<String, String> anticoding, String toDecode);

}
