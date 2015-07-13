package de.smba.compression.file;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.smba.compression.coding.IDecompressor;
import de.smba.compression.coding.Decompressor;

public class FileHandler implements IFileHandler {

	private IDecompressor decompressor;

	public FileHandler() {
		this.decompressor = new Decompressor();
	}

	/**
	 * Writes text to a specified file.
	 * 
	 * @param path
	 * @param text
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void storeFile(String path, String text)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(path, "UTF-8");
		writer.write(text);
		writer.close();
	}

	/**
	 * Stores a compressed text including the corresponding anticoding to an
	 * XML-based file.
	 * 
	 * @param path
	 * @param compressed
	 * @param anticoding
	 * @throws IOException
	 */
	public void storeCompressedFile(String path, String compressed,
			Map<String, String> anticoding) throws IOException {

		String serialisedAnticoding = serialise((Serializable) anticoding);

		Document doc = XMLConstructor.constructFile(compressed,
				serialisedAnticoding);

		try {
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(new File(path));
			transformer.transform(source, result);
		} catch (Exception e) {

		}
	}

	public String loadCompressedFile(String path) {
		try {

			String anticodingSerialised = getAnticodingSerialised(path);
			String compressed = getCompressed(path);

			Object deserialised = deserialise(anticodingSerialised);
			Map<String, String> anticodingDeserialised = null;
			if (deserialised instanceof Map<?, ?>) {
				// unchecked
				anticodingDeserialised = (Map<String, String>) deserialised;
			} else {
				throw new IOException();
			}

			String decompressed = this.decompressor.decompress(
					anticodingDeserialised, compressed);

			return decompressed;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** Read the object from Base64 string. */
	private static Object deserialise(String s) throws IOException,
			ClassNotFoundException {
		byte[] data = Base64.decodeBase64(s);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(
				data));
		Object o = ois.readObject();
		ois.close();
		return o;
	}

	/** Write the object to a Base64 string. */
	private static String serialise(Serializable o) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		oos.close();
		return Base64.encodeBase64String(baos.toByteArray());
	}

	public String getCompressed(String path) {
		try {

			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("compressed");

			if (nList.getLength() == 1) {
				Node testsetpath = nList.item(0);

				if (testsetpath.getNodeType() == Node.ELEMENT_NODE) {
					String compressed = testsetpath.getTextContent();
					return compressed;
				}

			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getAnticodingSerialised(String path) {
		try {

			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("anticoding");

			if (nList.getLength() == 1) {
				Node testsetpath = nList.item(0);

				if (testsetpath.getNodeType() == Node.ELEMENT_NODE) {
					String anticoding = testsetpath.getTextContent();
					return anticoding;
				}

			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String loadFile(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		br.close();
		return sb.toString();
	}

}
