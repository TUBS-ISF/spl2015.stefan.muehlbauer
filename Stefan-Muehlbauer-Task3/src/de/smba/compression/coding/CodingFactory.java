package de.smba.compression.coding;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.smba.compression.analysis.Analyser;
import de.smba.compression.coding.huffman.HuffmanCode;
import de.smba.compression.coding.huffman.HuffmanTree;

public class CodingFactory {
	private static final Analyser analyser = Analyser.getInstance();
	
	public static Map<String, String> buildHuffmanCoding(String path) {
		String text = null;
		try {
			text = analyser.loadFile(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String, Integer> frequency = analyser.analyseFrequency(text);
		HuffmanTree tree = HuffmanCode.buildTree(frequency);
		return HuffmanCode.exportCoding(tree, new StringBuffer(), new HashMap<String, String>());
	}
	
}
