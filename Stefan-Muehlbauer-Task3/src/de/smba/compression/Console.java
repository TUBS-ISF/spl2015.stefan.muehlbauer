package de.smba.compression;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import de.smba.compression.analysis.Analyser;
import de.smba.compression.coding.CodingFactory;
import de.smba.compression.coding.CodingStore;
import de.smba.compression.coding.Compressor;
import de.smba.compression.coding.huffman.HuffmanCode;
import de.smba.compression.coding.huffman.HuffmanTree;
import de.smba.compression.config.CompressionConfig;
import de.smba.compression.file.FileHandler;

public class Console {
	
	/*
	 * Report	: Any Compression run provides the ratio of compressed and original file
	 * ConsoleDocumentation	: Provides brief information on each command
	 * Compression	: Provides the Compression functionality/command
	 * TestSet		: Provides a test run and returns the average ratio of compressed and original file
	 */
	
	/*
	 * VALIDITY CONSTRAINTS (implemented in main())
	 * (TestSet => Report) && (Report => Compression)
	 */
	
	private static final Analyser analyser = Analyser.getInstance();
	private static final Scanner in = new Scanner(System.in);
	private static final CodingStore store = new CodingStore();
		
	/*
	 * Method is not used anymore, since the coding generation is no handled by the
	 * Compression method directly.
	 */
	@Deprecated
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
			} else if (command.startsWith("compress")) {
				//#ifdef Compression
				System.out.println("	Compresses a file.");
				System.out.println("	Command 'compress' usage: compress <PathToFileToCompress> <PathToTargetFile>");
				//#else
//@				System.out.println("	Help on command compress is not available since the feature Compression is not selected");
				//#endif
			} else if (command.startsWith("decompress")) {
				System.out.println("	Decompresses a file.");
				System.out.println("	Command 'decompress' usage: decompress <PathToFileToDeompress> <PathToTargetFile>");
			} else {
				System.out.println("	'" + command + "' is not a command.");
			}
		} else {
			System.out.println("Help available for load, huffman, show and exit.");
		}
	}
	
	//#ifdef Report
	public static double delegateCompress(String command) {
	//#else
//@	public static void delegateCompress(String command) {	
	//#endif
		if (command.length() != 0) {
			
			
			String source 	= command.split(" ")[0];
			String target	= command.split(" ")[1];
			
			Map<String, String> coding = CodingFactory.buildHuffmanCoding(source);
			store.addCoding(target, coding); 
			
			String toEncode = null;
			
			try {
				toEncode = analyser.loadFile(source);
			} catch (IOException e) {
				System.out.println("	Path '" + source + "' seems to be invalid! Try again!");
			}
			
			String compressed = Compressor.compress(coding, toEncode);
			
			//#ifdef Report
			double ratio = -1* (1 - (compressed.length()/8.0 * 1.0 / toEncode.length()) * 100); 
			System.out.println("	Compression reduced size by " + ratio + "%.");			
			//#endif
			
			try {
				
				/*
				 * Old version
				 * FileHandler.storeFile(target, compressed);
				 */
				FileHandler.storeCompressedFile(target, compressed, store.getAnticoding(store.getCurrent()));
				
				//#ifdef Report
				return ratio;
				//#endif
				
			} catch (Exception e) {
				System.out.println("	Path '" + target + "' seems to be invalid or something did not work! Try again!");
			}
		
		} else {
			System.out.println("	No path specified!");
		}
		//#ifdef Report
		return 0.0;
		//#endif
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
			
			String compressed = FileHandler.loadCompressedFile(source);//Compressor.decompress(anticoding, toDecode);
			
			try {
				FileHandler.storeFile(target, compressed);
			} catch (Exception e) {
				System.out.println("	Path '" + target + "' seems to be invalid or something did not work! Try again!");
				return;
			}
		
		} else {
			System.out.println("	No path specified!");
		}
	}
	
	/**
	 * This method encapsulates the test run functionality. 
	 */
	//#if TestSet && Report
	public static void delegateTest() {
		
		/** Retrieves the test set path*/
		String TestSetPath = CompressionConfig.getTestSetPath();
		String p = System.getProperty("user.dir") + "/" + TestSetPath;
		p = p.replace("\n", "");
		
		File f = new File(p);
		
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		
		double ratio = 0.0;
		
		for (File file : files) {
			
			System.out.println("");
			System.out.println("	Compressing " + file.getAbsolutePath());
			
			/* Compress all test files */ 
			ratio += Console.delegateCompress(file.getAbsolutePath() + " " + TestSetPath + "/compressed/" + file.getName());
		}
		
		System.out.println("\n	Test run on " + files.size() + " elements achieved an average Compression rate of " + ratio/files.size() + " %.");
	}
	//#endif

public static void main(String args[]) {
	
		/*
		 * Validation of the feature constraints (TestSet => Report) & (Report => Compression)
		 */
		//#if (!TestSet || Report) && (!Report || Compression)
	
		System.out.println("### Compression Console ###");
		while (true) {
			System.out.print("coco >> ");
			String line = in.nextLine();
			
			if (line.startsWith("exit")) {
				System.out.println("	Terminating.");
				System.exit(0);
			} else if (line.startsWith("help")) {
				//#ifdef ConsoleDocumentation
				delegateHelp(line.substring(4).trim());
				//#else
//@				System.out.println("	Command '" + line.split(" ")[0] + "' not available since the feature ConsoleDocumentation is not selected.");
				//#endif
			} else if (line.startsWith("load")) {
				delegateLoadCoding(line.substring(4).trim());
			} else if (line.startsWith("huffman")) {
				delegateBuildHuffmanCoding(line.substring(6).trim());
			} else if (line.startsWith("show")) {
				delegateShow(line.substring(4).trim());
			} else if (line.startsWith("easter egg")) {
				System.out.println("	This is not an Easter Egg!");
			} else if (line.startsWith("TestSet")) {
				//#ifdef TestSet
				delegateTest();
				//#else
//@				System.out.println("	Command '" + line.split(" ")[0] + "' not available since the feature TestSet is not selected.");
				//#endif
			}else if (line.startsWith("compress")) {
				//#ifdef Compression
				delegateCompress(line.substring(8).trim());
				//#else
//@				System.out.println("	Command '" + line.split(" ")[0] + "' not available since the feature Compression is not selected.");
				//#endif
				
			} else if (line.startsWith("decompress")) {
				delegateDecompress(line.substring(10).trim());
			} else {
				System.out.println("	Command '" + line.split(" ")[0] + "' not found.");
			}
		}
		
		//#else
//@		System.err.println("	Invalid feature selection!");
//@		System.exit(1);
		//#endif
	}
	
}
