import de.smba.compression.frontend.Frontend;
import de.smba.compression.frontend.Console;

//fertig

public aspect Console {
	
	public static void Frontend.main(String[] args) {
		Console.main(new String[0]);
	}
	
}
