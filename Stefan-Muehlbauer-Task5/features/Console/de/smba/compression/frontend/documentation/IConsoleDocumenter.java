package de.smba.compression.frontend.documentation;

import de.smba.compression.frontend.documentation.IDocumenter;

public interface IConsoleDocumenter extends IDocumenter {
	public String documentHelp(String command);
}