import de.smba.compression.coding.CodingFactoryMediator;
import de.smba.compression.coding.ShannonFanoCodingFactory;

//fertig

public aspect ShannonFano {
	
	public static ICodingFactory CodingFactoryMediator.getCodingFactory() {
		return new ShannonFanoCodingFactory();
	}
	
}
