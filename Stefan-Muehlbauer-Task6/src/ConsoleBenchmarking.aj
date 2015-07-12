import de.smba.compression.frontend.benchmarking.ConsoleBenchmarker;

//fertig

public aspect ConsoleBenchmarking {
	
	pointcut benchmarkingConsole(String before, String after) : 
		execution(double ConsoleBenchmarker.benchmark(String, String)) && args(before, after);
	
	void around(String before, String after): benchmarkingConsole(before, after) {
		double ratio = -1* (1 - (after.length()/8.0 * 1.0 / before.length()) * 100); 
		System.out.println("	Compression reduced size by " + ratio + "%.");
	}
}
