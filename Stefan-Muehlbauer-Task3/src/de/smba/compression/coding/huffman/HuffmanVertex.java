package de.smba.compression.coding.huffman;

class HuffmanVertex extends HuffmanTree {
    public final HuffmanTree left, right;
 
    public HuffmanVertex(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}
