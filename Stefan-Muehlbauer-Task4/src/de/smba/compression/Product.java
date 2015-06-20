package de.smba.compression;

import de.smba.compression.analysis.Analyser;
import de.smba.compression.analysis.IAnalyser;
import de.smba.compression.coding.CodingStore;
import de.smba.compression.coding.Compressor;
import de.smba.compression.coding.Decompressor;
import de.smba.compression.coding.HuffmanCodingFactory;
import de.smba.compression.file.FileHandler;
import de.smba.compression.file.IFileHandler;
import de.smba.compression.frontend.Console;
import de.smba.compression.frontend.GUI;
import de.smba.compression.frontend.IFrontend;
import de.smba.compression.frontend.benchmarking.ConsoleBenchmarker;
import de.smba.compression.frontend.documentation.ConsoleDocumenter;
import de.smba.compression.frontend.documentation.GUIDocumenter;

/**
 * This product connects the different components via glue code in order to
 * derive a valid product.
 * 
 * Features which are to (de-)select comprise:
 * - Console (excludes GUI)
 * - GUI (excludes Console)
 * - ConsoleDocumentation (requires Console)
 * - GUIDocumentation (requires GUI)
 * - Compression
 * - Benchmarking (requires Compression))
 * 
 * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 */
public class Product {

	/** Frontend instance used */
	private IFrontend frontend;

	/**
	 * Constructor for the Compression Tool product variant.
	 * @param IFrontend instance
	 */
	public Product(IFrontend frontend) {
		this.frontend = frontend;
	}

	/**
	 * Every IFrontend instance implements a run() method since it 
	 * implements the Runnable interface
	 */
	public void start() {
		this.frontend.run();
	}

	public static void main(String[] args) {

		IAnalyser analyser = new Analyser();
		IFileHandler fileHandler = new FileHandler(new Decompressor());

		Product console = new Product(new Console(new CodingStore(),
				fileHandler, new Compressor(), new HuffmanCodingFactory(
						analyser, fileHandler), new ConsoleDocumenter(),
				new ConsoleBenchmarker()));

		//console.start();
		
		Product gui = new Product(new GUI(new GUIDocumenter(), 
				new HuffmanCodingFactory(new Analyser(), new FileHandler(new Decompressor())),
				new Compressor(),
				new Decompressor(),
				new FileHandler(new Decompressor())));
		
		gui.start();
	}
}
