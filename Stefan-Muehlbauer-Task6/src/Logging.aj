import java.util.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import de.smba.compression.frontend.Console;
import java.awt.event.ActionEvent;
import de.smba.compression.frontend.documentation.ConsoleDocumenter;
import de.smba.compression.frontend.documentation.GUIDocumenter;;

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
	 * Point cut / advise for the benchmarking method
	 */
	after(): execution(double GUIBenchmarker.benchmark(String, String)) {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		System.err.println("[Logging] [" + currentTimestamp.toString() + "] Benchmarking performed!");
	}
	
	/**
	 * Point cut / advice for the documentation method
	 */
	after(): execution(void GUIDocumenter.documentAbout()) {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		System.err.println("[Logging] [" + currentTimestamp.toString() + "] Documentation performed!");
	}
	
	/**
	 * Point cut / advice for the compression method
	 */
	after(): execution(String Compressor.compress(Map<String, String>, String)) {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		System.err.println("[Logging] [" + currentTimestamp.toString() + "] Compression performed!");
	}

}
