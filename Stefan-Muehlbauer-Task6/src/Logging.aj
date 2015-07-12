import java.util.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import de.smba.compression.frontend.Console;
import java.awt.event.ActionEvent;
import de.smba.compression.frontend.documentation.ConsoleDocumenter;
import de.smba.compression.frontend.documentation.GUIDocumenter;;

/**
 * This aspect implements the logging functionality described by the 'Logging' feature. 
 * Currently this aspect yields a printed line for every action performed.
 * 
 * @author Stefan Mühlbauer <s.muehlbauer@tu-bs.de>
 *
 */
public aspect Logging {

	/* 
	 * Compression pointcuts
	 */
	pointcut consoleCompression(): execution(void Console.delegateCompress(String));
	pointcut guiCompression(): execution(void GUI.actionPerformed(ActionEvent));
	
	/*
	 * Documentation pointcuts
	 */
	pointcut helpConsole(): execution(String ConsoleDocumenter.documentHelp(String));
	pointcut helpGUI(): execution(String GUIDocumenter.documentHelp(String));
	
	
	/*
	 * Frontend invocation pointcuts
	 * TODO refactor/test
	 */
	pointcut mainConsole(): execution(public static void Console.main(String[]));
	pointcut mainGUI(): execution(public static void GUI.main(String[]));
	pointcut mainCallConsole(): call(public static void Console.main(String[]));
	pointcut mainCallGUI(): call(public static void GUI.main(String[]));
	
	/*
	 * ----------------------
	 */
	
	/*
	 * Advice definitions
	 */
	after(): consoleCompression() || guiCompression() {
		Calendar calendar = Calendar.getInstance();
		Date now  = calendar.getTime();
		Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		System.err.println("[Logging] [" + currentTimestamp.toString() + "] Compression performed!");
	}
	
	after(): helpConsole() || helpGUI() {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		System.err.println("[Logging] [" + currentTimestamp.toString() + "] Documentation performed!");
	}
	
	after(): mainConsole() || mainGUI() || mainCallConsole() || mainCallGUI() {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		System.err.println("[Logging] [" + currentTimestamp.toString() + "] Frontend started!");
	}
}