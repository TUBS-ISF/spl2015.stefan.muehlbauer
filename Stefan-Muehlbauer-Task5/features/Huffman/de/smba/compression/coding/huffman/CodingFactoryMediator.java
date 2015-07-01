package de.smba.compression.coding.huffman;

import de.smba.compression.coding.HuffmanCodingFactory;
import de.smba.compression.coding.ICodingFactory;
import de.smba.compression.analysis.Analyser;

import de.smba.compression.file.FileHandler;
import de.smba.compression.coding.Decompressor;
import de.smba.compression.analysis.IAnalyser;

public class CodingFactoryMediator {
	public static ICodingFactory getCodingFactory() {
		return new HuffmanCodingFactory((IAnalyser) new Analyser(), new FileHandler(new Decompressor()));
	}
}