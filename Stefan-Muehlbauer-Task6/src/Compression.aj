import java.util.Map;
import de.smba.compression.coding.Compressor;

//fertig

/**
 * This aspect implements the full compression functionality by extending the 
 * Compressor class.
 * 
 * @author Stefan Mühlbauer <s.muehlbauer@tu-bs.de>
 *
 */
public aspect Compression {
	
	pointcut CompressionCompress(Map<String, String> coding, String toEncode):
		execution(String Compressor.compress(Map<String, String>, String)) && args(coding, toEncode);
	
	String around(Map<String, String> coding, String toEncode): CompressionCompress(coding, toEncode) {
		StringBuffer encoded = new StringBuffer();

		for (char c : toEncode.toCharArray()) {
			encoded.append(coding.get(c + ""));
		}

		return encoded.toString();
	}
}
