package de.smba.compression.frontend;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import de.smba.compression.coding.CodingFactoryMediator;
import de.smba.compression.coding.CodingStore;
import de.smba.compression.coding.Compressor;
import de.smba.compression.coding.Decompressor;
import de.smba.compression.coding.ICodingFactory;
import de.smba.compression.coding.ICodingStore;
import de.smba.compression.coding.ICompressor;
import de.smba.compression.file.FileHandler;
import de.smba.compression.file.IFileHandler;
import de.smba.compression.frontend.benchmarking.AbstractConsoleBenchmarker;
import de.smba.compression.frontend.benchmarking.ConsoleBenchmarker;
import de.smba.compression.frontend.documentation.ConsoleDocumenter;
import de.smba.compression.frontend.documentation.IConsoleDocumenter;

/**
 * This class defines the console frontend variant of the compression tool.
 * 
 * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 *
 */
public  class  Console  implements IFrontend {
	

	private static IFileHandler fileHandler = new FileHandler();

	
	private static ICompressor compressor = new Compressor();

	
	private static  ICodingStore store = new CodingStore();

	
	private static IConsoleDocumenter consoleDocumenter = new ConsoleDocumenter();

	
	private static AbstractConsoleBenchmarker benchmarker = new ConsoleBenchmarker();

	

	private static List<ICodingFactory> codingFactories = new LinkedList<ICodingFactory>();

	private static ICodingFactory currentCodingFactory = getInitialCodingFactory();

	private static Scanner in = new Scanner(System.in);

	
	private static ICodingFactory getInitialCodingFactory() {
		return CodingFactoryMediator.getCodingFactory();
	}

	private static void delegateLoadCoding(String command) {
		if (command.length() != 0) {

			if (store.contains(command)) {
				store.setCurrent(command);
				System.out.println("	Loaded coding '" + command + "'.");
			} else {
				System.out.println("	No coding named'" + command
						+ "' found. Please try again or generate coding!");
			}

		} else {
			System.out.println("	No coding specified!");
		}
	}

	

	private static void delegateShow(String command) {
		if (command.length() != 0) {
			if (store.contains(command)) {
				System.out.println(store.getPrintableCoding(command));
			} else {
				System.out.println("	No coding named'" + command
						+ "' found. Please try again or generate coding!");
			}
		} else {
			System.out.println("	No coding specified!");
		}
	}

	

	private static void delegateCompress(String command) {

		//TODO refactor
		/*
		if (compressor instanceof EmptyCompressor) {
			System.out
					.println("	Help on command compress is not available since the feature 'Compression' is not selected");
			return;
		}
		*/

		if (command.length() != 0) {

			String source = command.split(" ")[0];
			String target = command.split(" ")[1];

			Map<String, String> coding = currentCodingFactory
					.buildCoding(source);
			store.addCoding(target, coding);

			String toEncode = null;

			try {
				toEncode = fileHandler.loadFile(source);
			} catch (IOException e) {
				System.out.println("	Path '" + source
						+ "' seems to be invalid! Try again!");
			}

			String compressed = compressor.compress(coding, toEncode);

			/**
			 * Benchmarking implementation optional
			 */
			benchmarker.benchmark(toEncode, compressed);

			try {

				fileHandler.storeCompressedFile(target, compressed,
						store.getAnticoding(store.getCurrent()));
				return;

			} catch (Exception e) {
				System.out
						.println("	Path '"
								+ target
								+ "' seems to be invalid or something did not work! Try again!");
			}

		} else {
			System.out.println("	No path specified!");
		}
	}

	

	private static void delegateDecompress(String command) {
		if (command.length() != 0) {

			String source = command.split(" ")[0];
			String target = command.split(" ")[1];

			String compressed = fileHandler.loadCompressedFile(source);

			try {
				fileHandler.storeFile(target, compressed);
			} catch (Exception e) {
				System.out
						.println("	Path '"
								+ target
								+ "' seems to be invalid or something did not work! Try again!");
				return;
			}

		} else {
			System.out.println("	No path specified!");
		}
	}

	

	public static void main(String args[]) {
		
		System.out.println("### Compression Console ###");
		while (true) {
			System.out.print("coco >> ");
			String line = in.nextLine();

			if (line.startsWith("exit")) {
				System.out.println("	Terminating.");
				System.exit(0);
			} else if (line.startsWith("help")) {
				
				//TODO Refactor
				/*
				if (consoleDocumenter instanceof EmptyDocumenter) {
					System.out
							.println("	Command '"
									+ line.split(" ")[0]
									+ "' not available since the feature 'ConsoleDocumentation' is not selected.");
				} else {
				*/
					String r = consoleDocumenter.documentHelp(line.substring(4)
							.trim());
					System.out.println(r);
				/*
				}
				*/
			} else if (line.startsWith("load")) {
				delegateLoadCoding(line.substring(4).trim());
			} else if (line.startsWith("show")) {
				delegateShow(line.substring(4).trim());
			} else if (line.startsWith("easter egg")) {
				System.out.println("	This is not an Easter Egg!");
			} else if (line.startsWith("compress")) {
				delegateCompress(line.substring(8).trim());
			} else if (line.startsWith("decompress")) {
				delegateDecompress(line.substring(10).trim());
			} else {
				System.out.println("	Command '" + line.split(" ")[0]
						+ "' not found.");
			}
		}
	}
}