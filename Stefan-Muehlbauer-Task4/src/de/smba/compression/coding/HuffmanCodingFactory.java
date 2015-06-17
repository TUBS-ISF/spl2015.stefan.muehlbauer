package de.smba.compression.coding;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.smba.compression.analysis.IAnalyser;
import de.smba.compression.file.IFileHandler;

import de.smba.compression.coding.huffman.HuffmanCode;
import de.smba.compression.coding.huffman.HuffmanTree;


/**
 * Factory class for different codings.
 * @author Stefan Mühlbauer
 *
 */
public class HuffmanCodingFactory implements ICodingFactory {
	
	private IAnalyser analyser;
	private IFileHandler fileHandler;

	public HuffmanCodingFactory(IAnalyser analyser, IFileHandler fileHandler) {
		this.analyser = analyser;
		this.fileHandler = fileHandler;
	}
	/**
	 * Builds a Huffman coding.
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

		Map<String, Integer> frequency = this.analyser.analyseFrequency(text);
		HuffmanTree tree = HuffmanCode.buildTree(frequency);
		return HuffmanCode.exportCoding(tree, new StringBuffer(),
				new HashMap<String, String>());
	} 

}
