package de.smba.compression.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface IFileHandler {

	public void storeFile(String path, String text)
			throws FileNotFoundException, UnsupportedEncodingException;

	public void storeCompressedFile(String path, String compressed,
			Map<String, String> anticoding) throws IOException;

	public String loadCompressedFile(String path);

	public String getCompressed(String path);

	public String getAnticodingSerialised(String path);

	public String loadFile(String path) throws IOException;

}
