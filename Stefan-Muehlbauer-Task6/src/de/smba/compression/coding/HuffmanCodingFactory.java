package de.smba.compression.coding; 

import java.io.IOException; 
import java.util.HashMap; 
import java.util.Map; 

import de.smba.compression.analysis.Analyser;
import de.smba.compression.analysis.IAnalyser; 
import de.smba.compression.file.FileHandler;
import de.smba.compression.file.IFileHandler; 
import de.smba.compression.coding.huffman.HuffmanCode; 
import de.smba.compression.coding.huffman.HuffmanTree; 


/**
 * Factory class for Huffman codings.
 * 
 * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 * 
 */
public  class  HuffmanCodingFactory  implements ICodingFactory {
	
	
	private Analyser analyser;

	
	private IFileHandler fileHandler;

	

	public HuffmanCodingFactory() {
		this.analyser = new Analyser();
		this.fileHandler = new FileHandler();
	}

	
	/**
	 * Builds a Huffman coding.
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

	
	
	public Map<String, String> buildCodingFromText(String text) {
		Map<String, Integer> frequency = this.analyser.analyseFrequency(text);
		HuffmanTree tree = HuffmanCode.buildTree(frequency);
		return HuffmanCode.exportCoding(tree, new StringBuffer(),
				new HashMap<String, String>());
	}


}
