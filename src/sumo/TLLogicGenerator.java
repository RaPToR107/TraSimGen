package sumo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TLLogicGenerator {
	
	private PropertiesAccessor propertiesAccessor = null;
	private List<Junction> junctionList;

	public TLLogicGenerator(PropertiesAccessor propertiesAccessor) {
		this.propertiesAccessor = propertiesAccessor;
		junctionList = new ArrayList<Junction>();
	}
	
	public boolean readJunctionInfo() {
		
		String netFileName = propertiesAccessor.getString("netFileName");
		Document doc = null;
		
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
		
		NodeList junctionNodeList = doc.getElementsByTagName("junction");
		
		for(int i = 0; i < junctionNodeList.getLength(); i++) {
			Element curNode = (Element)junctionNodeList.item(i);					
			String nodeName = curNode.getAttribute("id");
			String nodeType = curNode.getAttribute("type");
			if(!nodeType.equals("internal")) {
				NodeList requestNodeList = curNode.getElementsByTagName("request");
				Junction junction = new Junction(nodeName, requestNodeList.getLength());
				junctionList.add(junction);
			}
		}
		
		return true;
	}
	
	public boolean generateTrafficLightInfo() {
		return true;
	}
	
	public boolean createTLLogic() {
		return true;
	}

}
