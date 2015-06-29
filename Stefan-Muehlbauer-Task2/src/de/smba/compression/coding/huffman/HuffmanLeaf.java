package de.smba.compression.coding.huffman;

class HuffmanLeaf extends HuffmanTree {
    public final String value;
 
    public HuffmanLeaf(int freq, String val) {
        super(freq);
        value = val;
    }
}
