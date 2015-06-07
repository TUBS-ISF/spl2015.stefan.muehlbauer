package de.smba.compression.config;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * 
 * @author Stefan Mühlbauer
 *
 */
public class CompressionConfig {
	
	public static String getTestSetPath() {
		try {

			File fXmlFile = new File("bin/de/smba/compression/config/config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("testsetpath");

			if (nList.getLength() == 1) {
				Node testsetpath = nList.item(0);
				
				if (testsetpath.getNodeType() == Node.ELEMENT_NODE) {
					String path = testsetpath.getTextContent();
					return path;
				}
				
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String args[]) {
		System.err.println(CompressionConfig.getTestSetPath());
	}
}