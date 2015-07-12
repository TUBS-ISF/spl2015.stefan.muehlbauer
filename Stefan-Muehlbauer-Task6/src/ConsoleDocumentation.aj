import de.smba.compression.frontend.documentation.ConsoleDocumenter;

//fertig

public aspect ConsoleDocumentation {
		
	pointcut consoleDocumentationAbout():
		execution(void ConsoleDocumenter.documentAbout());
	
	pointcut consoleDocumentationHelp(String command):
		execution(String ConsoleDocumenter.documentHelp(String)) && args(command);
	
	void around(): consoleDocumentationAbout() {
		System.out.println("	This is a console based frontend.");
	}
	
	String around(String command): consoleDocumentationHelp(command) {
		StringBuffer returnString = new StringBuffer();
		
		if (command.length() != 0) {
			if (command.startsWith("help")) {
				
			} else if (command.startsWith("load")) {
				returnString.append("	Loads a generated coding.");
				returnString.append("	Command 'load' usage: load <CodingIdentifier>");
			} else if (command.startsWith("exit")) {
				returnString.append("	Terminates the tool.");
				returnString.append("	Command 'exit' usage: exit");
			} else if (command.startsWith("show")) {
				returnString.append("	Shows a coding.");
				returnString.append("	Command 'show' usage: show <CodingIdentifier>");
			} else if (command.startsWith("compress")) {
				returnString.append("	Compresses a file.");
				returnString.append("	Command 'compress' usage: compress <PathToFileToCompress> <PathToTargetFile>");
			} else if (command.startsWith("decompress")) {
				returnString.append("	Decompresses a file.");
				returnString.append("	Command 'decompress' usage: decompress <PathToFileToDeompress> <PathToTargetFile>");
			} else {
				returnString.append("	'" + command + "' is not a command.");
			}
		} else {
			returnString.append("Help available for load, huffman, show and exit.");
		}
		
		System.out.println(returnString);
	}
}
