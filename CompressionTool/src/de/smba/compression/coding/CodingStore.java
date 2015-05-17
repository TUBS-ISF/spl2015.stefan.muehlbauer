package de.smba.compression.coding;

import java.util.HashMap;
import java.util.Map;

public class CodingStore {
	
	private static String current = null;
	private static Map<String, Map<String, String>> codings = new HashMap<String, Map<String, String>>();
	private static Map<String, Map<String, String>> anticodings = new HashMap<String, Map<String, String>>();
	
	public void addCoding(String identifier, Map<String, String> coding) {
		codings.put(identifier, coding);
		
		Map<String, String> anticoding = new HashMap<String, String>();
		for (String key : coding.keySet()) {
			anticoding.put(coding.get(key), key);
		}

		anticodings.put(identifier, anticoding);
		
		if (current == null) {
			current = identifier;
		}
	}
	
	public boolean contains(String identifier) {
		return codings.containsKey(identifier);
	}
	
	public String getCurrent() {
		return current;
	}
		
	public void setCurrent(String identifier) {
		assert codings.containsKey(identifier);
		
		current = identifier;
	}
	
	public Map<String, String> getCoding(String identifier) {
		if (codings.containsKey(identifier)) {
			return codings.get(identifier);
		}
		return null;
	}
	
	public Map<String, String> getAnticoding(String identifier) {
		if (anticodings.containsKey(identifier)) {
			return anticodings.get(identifier);
		}
		return null;
	}
	
	public String getPrintableCoding(String identifier) {
		assert codings.containsKey(identifier);
		
		System.err.println(codings.get(identifier));
		
		StringBuffer out = new StringBuffer();
		
		out.append("Character*\tSubstitution");
		
		for (String key : codings.get(identifier).keySet()) {
			out.append("\n" + key + "\t" + codings.get(identifier).get(key));
		}
		
		return out.toString();
	}
	
}
