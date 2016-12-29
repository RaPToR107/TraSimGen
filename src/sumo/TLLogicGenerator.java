package sumo;

import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TLLogicGenerator {
	
	private PropertiesAccessor propertiesAccessor = null;
	private Map<Point, Element> tlList;
	private Document doc = null;

	public TLLogicGenerator(PropertiesAccessor propertiesAccessor) {
		this.propertiesAccessor = propertiesAccessor;
		tlList = new HashMap<Point, Element>();
	}
	
	public boolean readJunctionInfo() {
		
		String netFileName = propertiesAccessor.getString("netFileName");
		
		try {
			File xmlFile = new File(netFileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		NodeList tlNodeList = doc.getElementsByTagName("tlLogic");
		
		for(int i = 0; i < tlNodeList.getLength(); i++) {
			Element curNode = (Element)tlNodeList.item(i);					
			String nodeName = curNode.getAttribute("id");
			Point junctionId = getJunctionPoint(nodeName);
			tlList.put(junctionId, curNode);
		}
		
		return true;
	}
	
	public boolean updateTLLogic() {
		
		Integer yellowDuration = propertiesAccessor.getInt("yellowDuration");
		Integer greenDuration = propertiesAccessor.getInt("greenDuration");
		String netFileName = propertiesAccessor.getString("netFileName");
		
		for(Element cur: tlList.values()) {
			NodeList phaseList = cur.getElementsByTagName("phase");
			for(int i = 0; i < phaseList.getLength(); i++) {
				Element curNode = (Element)phaseList.item(i);
				if(i%2 == 0) {
					curNode.setAttribute("duration", greenDuration.toString());
				}
				else {
					curNode.setAttribute("duration", yellowDuration.toString());
				}
			}
		}
		
		try{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(netFileName));
			transformer.transform(source, result);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private Point getJunctionPoint(String junctionId) {

		StringTokenizer tokenizer = new StringTokenizer(junctionId, "/ ");
		return new Point(Integer.valueOf(tokenizer.nextToken()), Integer.valueOf(tokenizer.nextToken()));
	}

}
