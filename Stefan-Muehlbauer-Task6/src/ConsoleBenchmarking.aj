import de.smba.compression.frontend.benchmarking.ConsoleBenchmarker;

public aspect ConsoleBenchmarking {
	public double ConsoleBenchmarker.benchmark(String before, String after) {
		
		double ratio = -1* (1 - (after.length()/8.0 * 1.0 / before.length()) * 100); 
		System.out.println("	Compression reduced size by " + ratio + "%.");	
		
		return ratio;
		
	}
}