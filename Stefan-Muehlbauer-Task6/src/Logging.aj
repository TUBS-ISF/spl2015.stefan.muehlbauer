import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import de.smba.compression.coding.Compressor;
import de.smba.compression.frontend.GUI;
import de.smba.compression.frontend.benchmarking.GUIBenchmarker;
import de.smba.compression.frontend.documentation.GUIDocumenter;

//fertig

/**
 * This aspect implements the logging functionality described by the 'Logging' feature. 
 * Currently this aspect yields a printed line for every action performed.
 * 
 * @author Stefan Mühlbauer <s.muehlbauer@tu-bs.de>
 *
 */
public aspect Logging {

	/**
	 * Point cut / advice for the compression method
	 */
	before(): execution(void GUI.main(String[])) {
		final Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		System.err.println("[Logging] [" + currentTimestamp.toString() + "] Starting GUI!");
	}
	
}
