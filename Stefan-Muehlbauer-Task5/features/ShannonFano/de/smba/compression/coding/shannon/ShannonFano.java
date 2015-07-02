package de.smba.compression.coding.shannon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ShannonFano {

	public static Map<String, String> getCoding(Map freq) {
		
		Map<String, String> result = new HashMap<String, String>();
		
		List<String> symbolList = new ArrayList<String>();
		
		Iterator<Entry<String, String>> mapEntries = freq.entrySet().iterator();
		
		while(mapEntries.hasNext()) {
			Entry<String, String> entry = mapEntries.next();
			symbolList.add(entry.getKey());
		}
		
		addLayer(result, symbolList, true);
		
		return result;
	}
	
	private static void addLayer(Map<String, String> current, List<String> symbolList, boolean up) {
		
		String bit = "";
		
		if(!current.isEmpty()) {
			bit = (up) ? "0" : "1";
		}
		
		for(String c : symbolList) {
			
			String s = null;
			
			if (current.get(c).equals(null)) {
				s = "";
			} else {
				current.get(c);
			}
			
			current.put(c, s + bit);
		}
		
		if(symbolList.size() >= 2) {
			
			int separator = (int)Math.floor((float)symbolList.size()/2.0);
			
			List<String> upList = symbolList.subList(0, separator);
			addLayer(current, upList, true);
			
			List<String> downList = symbolList.subList(separator, symbolList.size());
			addLayer(current, downList, false);
		}
	}
}