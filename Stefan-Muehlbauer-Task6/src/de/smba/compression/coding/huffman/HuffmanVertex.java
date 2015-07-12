package de.smba.compression.coding.huffman; 

import de.smba.compression.coding.huffman.HuffmanTree; 

 

class  HuffmanVertex  extends HuffmanTree {
	
	public final HuffmanTree left, right;

	

	public HuffmanVertex(HuffmanTree l, HuffmanTree r) {
		super(l.frequency + r.frequency);
		left = l;
		right = r;
	}


}
