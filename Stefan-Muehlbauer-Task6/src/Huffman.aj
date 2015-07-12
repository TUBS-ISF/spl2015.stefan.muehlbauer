import de.smba.compression.coding.CodingFactoryMediator;
import de.smba.compression.coding.HuffmanCodingFactory;
import de.smba.compression.coding.ICodingFactory;

//fertig

public aspect Huffman {
	public static ICodingFactory CodingFactoryMediator.getCodingFactory() {
		return new HuffmanCodingFactory();
	}
}
