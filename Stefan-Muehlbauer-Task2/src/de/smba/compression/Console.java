package de.smba.compression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import de.smba.compression.analysis.Analyser;
import de.smba.compression.coding.CodingStore;
import de.smba.compression.coding.Compressor;
import de.smba.compression.coding.huffman.HuffmanCode;
import de.smba.compression.coding.huffman.HuffmanTree;

public class Console {
	
	private static boolean feature_compression = false;
	private static boolean feature_consoleDocumentation = false;
	
	
	private static final Analyser analyser = Analyser.getInstance();
	private static final Scanner in = new Scanner(System.in);
	private static final CodingStore store = new CodingStore();
	
	public static void delegateBuildHuffmanCoding(String command) {
		
		if (command.length() != 0) {

			String identifier 	= command.split(" ")[1];
			String path			= command.split(" ")[2];
			
			String text;
			try {
				text = analyser.loadFile(path);
			} catch (IOException e) {
				System.out.println("	Path '" + path + "' seems to be invalid! Try again!");
				return;
			}
			
			Map<String, Integer> frequency = analyser.analyseFrequency(text);
			HuffmanTree tree = HuffmanCode.buildTree(frequency);
			store.addCoding(identifier, HuffmanCode.exportCoding(tree, new StringBuffer(), new HashMap<String, String>()));
			
			System.out.println("	Stored Huffman coding '" + identifier + "'.");
		} else {
			System.out.println("	No path specified!");
		}
	}
	
	public static void delegateLoadCoding(String command) {
		if (command.length() != 0) {
			if (store.contains(command)) {
				store.setCurrent(command);
				System.out.println("	Loaded coding '" + command + "'.");
			} else {
				System.out.println("	No coding named'" + command + "' found. Please try again or generate coding!");
			}
		} else {
			System.out.println("	No coding specified!");
		}
	}
	
	public static void delegateShow(String command) {
		if (command.length() != 0) {
			if (store.contains(command)) {
				System.out.println(store.getPrintableCoding(command));
			} else {
				System.out.println("	No coding named'" + command + "' found. Please try again or generate coding!");
			}
		} else {
			System.out.println("	No coding specified!");
		}
	}
	
	public static void delegateHelp(String command) {
		if (command.length() != 0) {
			if (command.startsWith("help")) {
				
			} else if (command.startsWith("load")) {
				System.out.println("	Loads a generated coding.");
				System.out.println("	Command 'load' usage: load <CodingIdentifier>");
			} else if (command.startsWith("huffman")) {
				System.out.println("	Generates a Huffman coding based on a specific file.");
				System.out.println("	Command 'huffman' usage: huffman <CodingIdentifier> <PathToFileToCompress>");
			} else if (command.startsWith("exit")) {
				System.out.println("	Terminates the tool.");
				System.out.println("	Command 'exit' usage: exit");
			} else if (command.startsWith("show")) {
				System.out.println("	Shows a coding.");
				System.out.println("	Command 'show' usage: show <CodingIdentifier>");
			} else if (feature_compression && command.startsWith("compress")) {
				System.out.println("	Compresses a file.");
				System.out.println("	Command 'compress' usage: compress <PathToFileToCompress> <PathToTargetFile>");
			} else if (feature_compression && command.startsWith("decompress")) {
				System.out.println("	Decompresses a file.");
				System.out.println("	Command 'decompress' usage: decompress <PathToFileToDeompress> <PathToTargetFile>");
			} else {
				System.out.println("	'" + command + "' is not a command.");
			}
		} else {
			System.out.println("Help available for load, huffman, show and exit.");
		}
	}
	
	
	public static void main(String args[]) {
		
		/** Runtine feature selection processing */
		List<String> argList = new ArrayList<String>(Arrays.asList(args));
		
		if (argList.contains("compression")) {
			feature_compression = true;
		}
		if (argList.contains("consoleDocumentation")) {
			feature_consoleDocumentation = true;
		}
		
		System.out.println("### Compression Console ###");
		while (true) {
			System.out.print("coco >> ");
			String line = in.nextLine();
			
			if (line.startsWith("exit")) {
				System.out.println("	Terminating.");
				System.exit(0);
			} else if (feature_consoleDocumentation && line.startsWith("help")) {
				delegateHelp(line.substring(4).trim());
			} else if (line.startsWith("load")) {
				delegateLoadCoding(line.substring(4).trim());
			} else if (line.startsWith("huffman")) {
				delegateBuildHuffmanCoding(line.substring(6).trim());
			} else if (line.startsWith("show")) {
				delegateShow(line.substring(4).trim());
			} else if (line.startsWith("easter egg")) {
				System.out.println("	This is not an Easter Egg!");
			} else if (feature_compression && line.startsWith("compress")) {
				delegateCompress(line.substring(8).trim());
			} else if (feature_compression && line.startsWith("decompress")) {
				delegateDecompress(line.substring(10).trim());
			} else {
				System.out.println("	Command '" + line.split(" ")[0] + "' not found.");
			}
		}
	}
	
	public static void delegateCompress(String command) {
		if (command.length() != 0) {
			Map<String, String> coding = store.getCoding(store.getCurrent());
			
			String source 	= command.split(" ")[0];
			String target	= command.split(" ")[1];
			
			String toEncode;
			try {
				toEncode = analyser.loadFile(source);
			} catch (IOException e) {
				System.out.println("	Path '" + source + "' seems to be invalid! Try again!");
				return;
			}
			
			String compressed = Compressor.compress(coding, toEncode);
			
			try {
				Compressor.storeFile(target, compressed);
			} catch (Exception e) {
				System.out.println("	Path '" + target + "' seems to be invalid or something did not work! Try again!");
				return;
			}
		
		} else {
			System.out.println("	No path specified!");
		}
	}
	
	public static void delegateDecompress(String command) {
		if (command.length() != 0) {
			Map<String, String> anticoding = store.getAnticoding(store.getCurrent());
			
			String source 	= command.split(" ")[0];
			String target	= command.split(" ")[1];
			
			String toDecode;
			try {
				toDecode = analyser.loadFile(source);
			} catch (IOException e) {
				System.out.println("	Path '" + source + "' seems to be invalid! Try again!");
				return;
			}
			
			String compressed = Compressor.decompress(anticoding, toDecode);
			
			try {
				Compressor.storeFile(target, compressed);
			} catch (Exception e) {
				System.out.println("	Path '" + target + "' seems to be invalid or something did not work! Try again!");
				return;
			}
		
		} else {
			System.out.println("	No path specified!");
		}
	}
}
