package de.smba.compression.coding;

import de.smba.compression.coding.ICodingFactory;
import de.smba.compression.analysis.Analyser;

import de.smba.compression.file.FileHandler;
import de.smba.compression.coding.Decompressor;
import de.smba.compression.analysis.IAnalyser;

public class CodingFactoryMediator {
	public static ICodingFactory getCodingFactory() {
		return new ShannonFanoCodingFactory((IAnalyser) new Analyser(), new FileHandler(new Decompressor()));
	}
}