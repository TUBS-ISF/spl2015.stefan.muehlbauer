package de.smba.compression.coding;

import java.io.IOException;
import java.util.Map;

import de.smba.compression.analysis.IAnalyser;
import de.smba.compression.coding.shannon.ShannonFano;
import de.smba.compression.file.IFileHandler;

public class ShannonFanoCodingFactory implements ICodingFactory  {
	private IAnalyser analyser;
	private IFileHandler fileHandler;

	public ShannonFanoCodingFactory(IAnalyser analyser, IFileHandler fileHandler) {
		this.analyser = analyser;
		this.fileHandler = fileHandler;
	}

	/**
	 * Builds a ShannonFano coding.
	 * @param path
	 * @return
	 */
	public Map<String, String> buildCoding(String path) {
		String text = null;
		try {
			text = this.fileHandler.loadFile(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buildCodingFromText(text);
	}
	
	public Map<String, String> buildCodingFromText(String text) {
		Map<String, Integer> frequency = this.analyser.analyseFrequency(text);
		return ShannonFano.getCoding(frequency);
	}
}
