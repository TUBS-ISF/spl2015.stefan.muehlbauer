package de.smba.compression.frontend.documentation;

public class EmptyDocumenter implements IDocumenter, IGUIDocumenter, IConsoleDocumenter {

	public EmptyDocumenter() {
		
	}
	
	public void documentAbout() {
		
	}

	public String documentHelp(String command) {
		return null;
	}

}
