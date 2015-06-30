package de.smba.compression.coding.shannon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * TODO description
 */
public class ShannonFano {

	public static Map<String, String> getCoding(Map freq) {
		
		Map<String, String> result = new HashMap<String, String>();
		List<String> charList = new ArrayList<String>();
		
		@SuppressWarnings("unchecked")
		Iterator<Entry<String, String>> entries = freq.entrySet().iterator();
		while( entries.hasNext() ) {
			Entry<String, String> entry = entries.next();
			charList.add(entry.getKey());
		}
		
		addLayer(result, charList, true);
		
		return result;
	}
	
	private static void addLayer(Map<String, String> result, List<String> charList, boolean up) {
		String bit = "";
		if( !result.isEmpty() ) {
			bit = (up) ? "0" : "1";
		}
		
		for( String c : charList ) {
			String s = null;
			if (result.get(c).equals(null)) {
				s = "";
			} else {
				result.get(c);
			}
			result.put(c, s + bit);
		}
		
		if( charList.size() >= 2 ) {
			
			int separator = (int)Math.floor((float)charList.size()/2.0);
			
			List<String> upList = charList.subList(0, separator);
			addLayer(result, upList, true);
			List<String> downList = charList.subList(separator, charList.size());
			addLayer(result, downList, false);
		}
	}
}