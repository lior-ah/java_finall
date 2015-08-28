package finallPro.model;

import java.io.File;
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
		
		NodeList restt = doc.getElementsByTagName("Resturant");
		Node rest = restt.item(0);
		Element el = (Element) rest;
		String s = el.getAttribute("name");
		int s1 = Integer.parseInt(el.getAttribute("numOfSeats"));
		int s2 = Integer.parseInt(el.getAttribute("maxCustomersPerDay"));
		
		Resturant resta = new Resturant(s, s1, s2, 2, 2, 2);
		Waiter[] ws = new Waiter[4];
		for(int i = 0; i < ws.length; i++){
			ws[i] = new Waiter("Waiter_" + i, resta);
			resta.addWaiter(ws[i]);
		}
		Customer[] custs = new Customer[5];
		for(int i = 0; i < custs.length; i++){
			custs[i] = new Customer("cust_" + i, "eating");
			resta.addCustomer(custs[i]);
		}
		//Customer cust = new Customer("abdc", "eating");
		//cust.doSomething();
		//Customer cust1 = new Customer("abcd", "playing");
		//cust1.doSomething();

	}
}
