package de.smba.compression.coding;

import java.util.Map;

public interface ICodingFactory {
	public Map<String, String> buildCoding(String path);
}
