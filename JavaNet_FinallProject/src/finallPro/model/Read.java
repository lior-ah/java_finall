package finallPro.model;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Read {

	public static void main(String[] args) throws ParserConfigurationException, IOException, Exception {
		// TODO Auto-generated method stub
		File xmlFile = new File("src/RestConf.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xmlFile);
		
		doc.getDocumentElement().normalize();
		
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		
		NodeList restt = doc.getElementsByTagName("Resturant");
		Node rest = restt.item(0);
		Element el = (Element) rest;
		String s = el.getAttribute("name");
		System.out.println(s);

	}

}
