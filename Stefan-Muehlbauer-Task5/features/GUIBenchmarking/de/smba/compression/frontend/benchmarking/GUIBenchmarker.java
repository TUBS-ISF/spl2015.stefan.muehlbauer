package de.smba.compression.frontend.benchmarking;

import javax.swing.JOptionPane;

import de.smba.compression.frontend.benchmarking.AbstractGUIBenchmarker;

public class GUIBenchmarker extends AbstractGUIBenchmarker {

	public double benchmark(String before, String after) {

		return 0;
	}

	@Override
	public void compressBenchmarkNotification(String old, String news) {
		double ratio = -1* (1 - (old.length()/8.0 * 1.0 / news.length()) * 100); 
		
		JOptionPane.showMessageDialog(null, "	Compression reduced size by " + ratio + " %.");
	}

}