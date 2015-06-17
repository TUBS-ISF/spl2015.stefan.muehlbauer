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
import de.smba.compression.frontend.IFrontend;
import de.smba.compression.frontend.documentation.ConsoleDocumenter;

/**
 * This product connects the different components via glue code
 * in order to derive a valid product.
 * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 
 */
public class Product {
	
	private IFrontend frontend;
	
	public Product(IFrontend frontend) {
		this.frontend = frontend;
	}
	
	public void start() {
		this.frontend.run();
	}
	
	public static void main(String[] args) {
		
		IAnalyser analyser = new Analyser();
		IFileHandler fileHandler = new FileHandler(new Decompressor());
		
		Product console = new Product(new Console(
				analyser,
				new CodingStore(),
				fileHandler,
				new Compressor(),
				new HuffmanCodingFactory(analyser, fileHandler),
				new ConsoleDocumenter()
				));
		
		console.start();
	}
}
