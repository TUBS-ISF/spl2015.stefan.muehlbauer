import javax.swing.JOptionPane;
import  de.smba.compression.frontend.documentation.GUIDocumenter;

public aspect GUIDocumentation {
	public void GUIDocumenter.documentAbout  () {
		OptionPane.showMessageDialog(null, "This is a Java Swing based frontend.");	
	}
}