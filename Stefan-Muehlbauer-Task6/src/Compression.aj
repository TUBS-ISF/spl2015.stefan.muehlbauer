import java.util.Map;
import de.smba.compression.coding.Compressor;
/**
 * This aspect implements the full compression functionality by extending the 
 * Compressor class.
 * 
 * @author Stefan Mühlbauer <s.muehlbauer@tu-bs.de>
 *
 */
public aspect Compression {
	
	/*
	 * Overrides the existing compress method.
	 */
	public String Compressor.compress(Map<String, String> coding, String toEncode) {
		StringBuffer encoded = new StringBuffer();

		for (char c : toEncode.toCharArray()) {
			encoded.append(coding.get(c + ""));
		}

		return encoded.toString();
	}
	
	public Compressor.Compressor() {
		
	}
	
	public void Compressor.identify() {
		
	}
}