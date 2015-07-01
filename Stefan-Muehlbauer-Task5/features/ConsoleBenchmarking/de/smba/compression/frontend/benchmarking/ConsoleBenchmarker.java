package de.smba.compression.frontend.benchmarking;

import de.smba.compression.frontend.benchmarking.AbstractConsoleBenchmarker;

public class ConsoleBenchmarker extends AbstractConsoleBenchmarker {

	public double benchmark(String before, String after) {
		
		double ratio = -1* (1 - (after.length()/8.0 * 1.0 / before.length()) * 100); 
		System.out.println("	Compression reduced size by " + ratio + "%.");	
		
		return ratio;
		
	}

}