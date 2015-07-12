package de.smba.compression.coding.huffman; 

import de.smba.compression.coding.huffman.HuffmanTree; 

 

class  HuffmanLeaf  extends HuffmanTree {
	
	public final String value;

	

	public HuffmanLeaf(int freq, String val) {
		super(freq);
		value = val;
	}


}
