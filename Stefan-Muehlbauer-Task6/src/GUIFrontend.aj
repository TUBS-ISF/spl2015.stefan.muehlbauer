import de.smba.compression.frontend.Frontend;
import de.smba.compression.frontend.GUI;

//fertig

public aspect GUIFrontend {
	
	public static void Frontend.main(String[] args) {
		GUI.main(new String[0]);
	}
	
}
