package de.smba.compression.frontend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

import de.smba.compression.analysis.IAnalyser;
import de.smba.compression.coding.EmptyCompressor;
import de.smba.compression.coding.ICodingFactory;
import de.smba.compression.coding.ICodingStore;
import de.smba.compression.coding.ICompressor;
import de.smba.compression.config.CompressionConfig;
import de.smba.compression.file.IFileHandler;
import de.smba.compression.frontend.documentation.EmptyDocumenter;
import de.smba.compression.frontend.documentation.IConsoleDocumenter;


public class Console implements IFrontend {

	private IAnalyser analyser;
	private IFileHandler fileHandler;
	private ICompressor compressor;
	private ICodingStore store;
	private IConsoleDocumenter consoleDocumenter;
	
	private Collection<ICodingFactory> codingFactories;
	private ICodingFactory currentCodingFactory;
	
	private Scanner in;
		
	public Console(IAnalyser analyser, ICodingStore store, IFileHandler fileHandler, ICompressor compressor, ICodingFactory initialCodingFactory, IConsoleDocumenter consoleDocumenter) {
		this.analyser = analyser;
		this.in =  new Scanner(System.in);
		this.store = store;
		this.fileHandler = fileHandler;
		this.compressor = compressor;
		this.consoleDocumenter = consoleDocumenter;
		
		this.codingFactories = new HashSet<ICodingFactory>();
		this.codingFactories.add(initialCodingFactory);
		this.currentCodingFactory = initialCodingFactory;
	}
	
	public void delegateLoadCoding(String command) {
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
	
	public void delegateShow(String command) {
		if (command.length() != 0) {
			if (this.store.contains(command)) {
				System.out.println(this.store.getPrintableCoding(command));
			} else {
				System.out.println("	No coding named'" + command + "' found. Please try again or generate coding!");
			}
		} else {
			System.out.println("	No coding specified!");
		}
	}
	
	public void delegateHelp(String command) {
		
	}
	

	public double delegateCompress(String command) {

		if (this.compressor instanceof EmptyCompressor) {
			System.out.println("	Help on command compress is not available since the feature Compression is not selected");
			return 1.0;
		}
		
		if (command.length() != 0) {
					
			String source 	= command.split(" ")[0];
			String target	= command.split(" ")[1];
			
			Map<String, String> coding = this.currentCodingFactory.buildCoding(source);
			store.addCoding(target, coding); 
			
			String toEncode = null;
			
			try {
				toEncode = this.fileHandler.loadFile(source);
			} catch (IOException e) {
				System.out.println("	Path '" + source + "' seems to be invalid! Try again!");
			}
			
			String compressed = this.compressor.compress(coding, toEncode);
			
			//#ifdef Report
			double ratio = -1* (1 - (compressed.length()/8.0 * 1.0 / toEncode.length()) * 100); 
			System.out.println("	Compression reduced size by " + ratio + "%.");			
			//#endif
			
			try {
				
				this.fileHandler.storeCompressedFile(target, compressed, store.getAnticoding(store.getCurrent()));
				
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
	//#endif
	
	public void delegateDecompress(String command) {
		if (command.length() != 0) {

			String source 	= command.split(" ")[0];
			String target	= command.split(" ")[1];
					
			String compressed = this.fileHandler.loadCompressedFile(source);
			
			try {
				this.fileHandler.storeFile(target, compressed);
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
	public void delegateTest() {
		
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
			ratio += delegateCompress(file.getAbsolutePath() + " " + TestSetPath + "/compressed/" + file.getName());
		}
		
		System.out.println("\n	Test run on " + files.size() + " elements achieved an average Compression rate of " + ratio/files.size() + " %.");
	}
	//#endif


	public void run() {
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
				if (consoleDocumenter instanceof EmptyDocumenter) {
					System.out.println("	Command '" + line.split(" ")[0] + "' not available since the feature ConsoleDocumentation is not selected.");
				} else {
					String r = consoleDocumenter.documentHelp(line.substring(4).trim());
					System.out.println(r);
				}
			} else if (line.startsWith("load")) {
				delegateLoadCoding(line.substring(4).trim());
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
	}	
}
