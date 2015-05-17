package de.smba.compression.coding.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import de.smba.compression.analysis.Analyser;

public class HuffmanCode {
    public static HuffmanTree buildTree(Map<String, Integer> charFreqs) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        
        for (String key : charFreqs.keySet()) {
        	if (charFreqs.get(key) > 0) {
        		trees.offer(new HuffmanLeaf(charFreqs.get(key), key));
        	}
        }

         while (trees.size() > 1) {
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();
 
            trees.offer(new HuffmanVertex(a, b));
        }
        return trees.poll();
    }
 
    public static Map<String, String> exportCoding(HuffmanTree tree, StringBuffer prefix, Map<String, String> container) {
        
    	if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
            container.put(leaf.value+"", prefix.toString());
            return container;
        } else if (tree instanceof HuffmanVertex) {
        	HuffmanVertex node = (HuffmanVertex)tree;

            prefix.append('0');
            Map<String, String> container2 = exportCoding(node.left, prefix, container);
            prefix.deleteCharAt(prefix.length()-1);
 
            prefix.append('1');
            Map<String, String> container3 = exportCoding(node.right, prefix, container2);
            prefix.deleteCharAt(prefix.length()-1);
            
            return container3;
        }
        return null;
    }
    
    public static void main(String[] args) {
        String test = "((((((((())";
 
        Analyser a = Analyser.getInstance();
        Map<String, Integer> freqs = a.analyseFrequency(test);
        HuffmanTree tree = buildTree(freqs);
 
        System.err.println(exportCoding(tree, new StringBuffer(), new HashMap<String, String>()));
    }
}
