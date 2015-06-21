package de.smba.compression.frontend.documentation;

import javax.swing.JOptionPane;

public class GUIDocumenter implements IGUIDocumenter {

	public void documentAbout() {
		
		JOptionPane.showMessageDialog(null, "This is a Java Swing based frontend.");

	}

}
