package file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.smba.compression.coding.Compressor;

public class FileHandler {

	/**
	 * Writes text to a specified file.
	 * 
	 * @param path
	 * @param text
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void storeFile(String path, String text)
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
	public static void storeCompressedFile(String path, String compressed,
			Map<String, String> anticoding) throws IOException {
		
		String serialisedAnticoding = serialise((Serializable) anticoding);
		
		Document doc = XMLConstructor.constructFile(compressed, serialisedAnticoding); 

		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(new File(path));
			transformer.transform(source, result);
		} catch (Exception e) {
			
			//e.printStackTrace();
		}
	}
	
	public static String loadCompressedFile(String path) {
		try {
						
			String anticodingSerialised = getAnticodingSerialised(path);
			String compressed = getCompressed(path);

			Map<String, String> anticodingDeserialised = (Map<String, String>) deserialise(anticodingSerialised);
			
			String decompressed = Compressor.decompress(anticodingDeserialised, compressed);
			
			return decompressed;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		//storeCompressedFile("benerXML.xml", "12345", new HashMap<String, String>());
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "10");
		map.put("b", "130");
		
		String serialised;
		try {
			serialised = serialise((Serializable) map);
			System.out.println(deserialise(serialised));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//System.err.println(fromString(serialised));
	}
	

	 /** Read the object from Base64 string. */
	   private static Object deserialise( String s ) throws IOException ,
	                                                       ClassNotFoundException {
	        byte [] data = Base64.getDecoder().decode( s );
	        ObjectInputStream ois = new ObjectInputStream( 
	                                        new ByteArrayInputStream(  data ) );
	        Object o  = ois.readObject();
	        ois.close();
	        return o;
	   }

	    /** Write the object to a Base64 string. */
	    private static String serialise( Serializable o ) throws IOException {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ObjectOutputStream oos = new ObjectOutputStream( baos );
	        oos.writeObject( o );
	        oos.close();
	        return Base64.getEncoder().encodeToString(baos.toByteArray()); 
	    
	    }
	    
	    public static String getCompressed(String path) {
			try {

				File fXmlFile = new File(path);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
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
	    
	    public static String getAnticodingSerialised(String path) {
			try {

				File fXmlFile = new File(path);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
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
}
