import javax.swing.JOptionPane;
import de.smba.compression.frontend.benchmarking.GUIBenchmarker;

public aspect GUIBenchmarking {
	
	@Override
	public void GUIBenchmarker.compressBenchmarkNotification(String old, String news) {
		double ratio = -1* (1 - (old.length()/8.0 * 1.0 / news.length()) * 100); 
		
		JOptionPane.showMessageDialog(null, "	Compression reduced size by " + ratio + " %.");
	}
}