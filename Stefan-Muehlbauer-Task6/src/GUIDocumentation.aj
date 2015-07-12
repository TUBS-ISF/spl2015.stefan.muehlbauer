import javax.swing.JOptionPane;
import de.smba.compression.frontend.documentation.GUIDocumenter;

//fertig

public aspect GUIDocumentation {
	
	pointcut documentationGUI():
		execution(void GUIDocumenter.documentAbout());
	
	void around(): documentationGUI() {
		javax.swing.JOptionPane.OptionPane.showMessageDialog(null, "This is a Java Swing based frontend.");
	}
}
