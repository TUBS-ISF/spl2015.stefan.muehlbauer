import javax.swing.JOptionPane;
import de.smba.compression.frontend.benchmarking.GUIBenchmarker;

//fertig

public aspect GUIBenchmarking {
	
	pointcut compressionBenchmark(String old, String news):
		execution(void GUIBenchmarker.compressBenchmarkNotification(String, String)) && args(old, news);
	
	void around(String old, String news): compressionBenchmark(old, news) {
		double ratio = -1* (1 - (old.length()/8.0 * 1.0 / news.length()) * 100); 
		JOptionPane.showMessageDialog(null, "	Compression reduced size by " + ratio + " %.");
	}
}