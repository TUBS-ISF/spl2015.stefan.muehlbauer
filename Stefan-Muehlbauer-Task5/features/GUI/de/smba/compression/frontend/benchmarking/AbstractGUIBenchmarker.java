package de.smba.compression.frontend.benchmarking;

import de.smba.compression.frontend.benchmarking.IBenchmarker;

public abstract class AbstractGUIBenchmarker implements IBenchmarker {
	public abstract void compressBenchmarkNotification(String old, String news);
}