package de.smba.compression.frontend.documentation;

//TODO compress feature

public class ConsoleDocumenter implements IConsoleDocumenter {

	public void documentAbout() {
		// TODO Auto-generated method stub
		
	}

	public ConsoleDocumenter() {
		
	}
	
	public String documentHelp(String command) {
		
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
				//#ifdef Compression
				returnString.append("	Compresses a file.");
				returnString.append("	Command 'compress' usage: compress <PathToFileToCompress> <PathToTargetFile>");
				//#else
//@				
				//#endif
			} else if (command.startsWith("decompress")) {
				returnString.append("	Decompresses a file.");
				returnString.append("	Command 'decompress' usage: decompress <PathToFileToDeompress> <PathToTargetFile>");
			} else {
				returnString.append("	'" + command + "' is not a command.");
			}
		} else {
			returnString.append("Help available for load, huffman, show and exit.");
		}
		
		return returnString.toString();
	}

}
