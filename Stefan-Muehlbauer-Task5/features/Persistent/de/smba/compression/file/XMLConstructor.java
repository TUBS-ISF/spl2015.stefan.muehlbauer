package de.smba.compression.file;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLConstructor  {

	public static Document constructFile(String compressed, String anticoding) {

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();

			Element rootElement = doc.createElement("root");
			doc.appendChild(rootElement);

			Element compressedNode = doc.createElement("compressed");
			compressedNode.appendChild(doc.createTextNode(compressed));
			rootElement.appendChild(compressedNode);

			Element anticodingNode = doc.createElement("anticoding");
			anticodingNode.appendChild(doc.createTextNode(anticoding));
			rootElement.appendChild(anticodingNode);

			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}