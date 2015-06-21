package de.smba.compression; 

import de.smba.compression.analysis.Analyser;
import de.smba.compression.analysis.IAnalyser;
import de.smba.compression.coding.CodingStore;
import de.smba.compression.coding.Compressor;
import de.smba.compression.coding.Decompressor;
import de.smba.compression.coding.EmptyCompressor;
import de.smba.compression.coding.HuffmanCodingFactory;
import de.smba.compression.coding.ICodingFactory;
import de.smba.compression.coding.ICodingStore;
import de.smba.compression.coding.ICompressor;
import de.smba.compression.coding.IDecompressor;
import de.smba.compression.file.FileHandler;
import de.smba.compression.file.IFileHandler;
import de.smba.compression.frontend.Console;
import de.smba.compression.frontend.GUI;
import de.smba.compression.frontend.IFrontend;
import de.smba.compression.frontend.benchmarking.AbstractConsoleBenchmarker;
import de.smba.compression.frontend.benchmarking.AbstractGUIBenchmarker;
import de.smba.compression.frontend.benchmarking.ConsoleBenchmarker;
import de.smba.compression.frontend.benchmarking.EmptyConsoleBenchmarker;
import de.smba.compression.frontend.benchmarking.EmptyGUIBenchmarker;
import de.smba.compression.frontend.benchmarking.GUIBenchmarker;
import de.smba.compression.frontend.documentation.ConsoleDocumenter;
import de.smba.compression.frontend.documentation.EmptyDocumenter;
import de.smba.compression.frontend.documentation.GUIDocumenter;
import de.smba.compression.frontend.documentation.IConsoleDocumenter;
import de.smba.compression.frontend.documentation.IDocumenter;
import de.smba.compression.frontend.documentation.IGUIDocumenter;

/**
 * This factory class contains several factory
 * methods for different IFrontend instances. 
 * We refer to the features as follows:
 * C: 	Console (excludes GUI)
 * G: 	GUI (excludes Console)
 * CD: 	ConsoleDocumentation (requires Console)
 * GD:	GUIDocumentation (requires GUI)
 * Co:	Compression
 * B:	Benchmarking (requires Compression))
 * 
 * @author Stefan Mühlbauer <s.muehlbauer@student.ucc.ie>
 *
 */

public class ProductFactory {
	
	/**
	 * Static fields
	 */
	private static final IAnalyser analyser = new Analyser();
	private static final ICompressor compressor = new Compressor();
	private static final ICompressor emptyCompressor = new EmptyCompressor();
	private static final IDecompressor decompressor = new Decompressor();
	private static final ICodingStore store = new CodingStore();
	private static final IFileHandler fileHandler = new FileHandler(decompressor);
	private static final IGUIDocumenter emptyDocumenter = new EmptyDocumenter();
	private static final IDocumenter guiDocumenter = new GUIDocumenter();
	private static final IConsoleDocumenter consoleDocumenter = new ConsoleDocumenter();
	private static final AbstractGUIBenchmarker emptyGuiBenchmarker = new EmptyGUIBenchmarker();
	private static final AbstractGUIBenchmarker guiBenchmarker = new GUIBenchmarker();
	private static final AbstractConsoleBenchmarker emptyConsoleBenchmarker = new EmptyConsoleBenchmarker();
	private static final AbstractConsoleBenchmarker consoleBenchmarker = new ConsoleBenchmarker();
	private static final ICodingFactory codingFactory = new HuffmanCodingFactory(analyser, fileHandler);
	
	/* Console based frontends */
	
	public static IFrontend createFrontend_C() {
		return new Console(store, fileHandler, emptyCompressor, codingFactory,(IConsoleDocumenter) emptyDocumenter, emptyConsoleBenchmarker);
	}
	
	public static IFrontend createFrontend_C_B() {
		return new Console(store, fileHandler, emptyCompressor, codingFactory,(IConsoleDocumenter) emptyDocumenter, consoleBenchmarker);
	}
	
	public static IFrontend createFrontend_C_CD() {
		return new Console(store, fileHandler, emptyCompressor, codingFactory, consoleDocumenter, emptyConsoleBenchmarker);
	}
	
	public static IFrontend createFrontend_C_B_CD() {
		return new Console(store, fileHandler, emptyCompressor, codingFactory,consoleDocumenter, consoleBenchmarker);
	}
	
	public static IFrontend createFrontend_C_Com() {
		return new Console(store, fileHandler, compressor, codingFactory,(IConsoleDocumenter) emptyDocumenter, emptyConsoleBenchmarker);
	}
	
	public static IFrontend createFrontend_C_B_Com() {
		return new Console(store, fileHandler, compressor, codingFactory,(IConsoleDocumenter) emptyDocumenter, consoleBenchmarker);
	}
	
	public static IFrontend createFrontend_C_CD_Com() {
		return new Console(store, fileHandler, compressor, codingFactory, consoleDocumenter, emptyConsoleBenchmarker);
	}
	
	public static IFrontend createFrontend_C_B_CD_Com() {
		return new Console(store, fileHandler, compressor, codingFactory,consoleDocumenter, consoleBenchmarker);
	}
	
	/* GUI based frontends */
	
	public static IFrontend createFrontend_G() {
		return new GUI(emptyDocumenter, codingFactory, emptyCompressor, fileHandler, emptyGuiBenchmarker);
	}
	
	public static IFrontend createFrontend_G_B() {
		return new GUI(emptyDocumenter, codingFactory, emptyCompressor, fileHandler, guiBenchmarker);
	}
	
	public static IFrontend createFrontend_G_GD() {
		return new GUI((IGUIDocumenter) guiDocumenter, codingFactory, emptyCompressor, fileHandler, emptyGuiBenchmarker);
	}
	
	public static IFrontend createFrontend_G_B_GD() {
		return new GUI((IGUIDocumenter) guiDocumenter, codingFactory, emptyCompressor, fileHandler, guiBenchmarker);
	}
	
	public static IFrontend createFrontend_G_Com() {
		return new GUI(emptyDocumenter, codingFactory, compressor, fileHandler, emptyGuiBenchmarker);
	}
	
	public static IFrontend createFrontend_G_B_Com() {
		return new GUI(emptyDocumenter, codingFactory, compressor, fileHandler, guiBenchmarker);
	}
	
	public static IFrontend createFrontend_G_GD_Com() {
		return new GUI((IGUIDocumenter) guiDocumenter, codingFactory, compressor, fileHandler, emptyGuiBenchmarker);
	}
	
	public static IFrontend createFrontend_G_B_GD_Com() {
		return new GUI((IGUIDocumenter) guiDocumenter, codingFactory, compressor, fileHandler, guiBenchmarker);
	}
}
