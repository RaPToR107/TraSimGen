package sumo;

import java.awt.Point;
import java.util.List;

public class Vehicle {
	
	private Integer vehicledId;
	private Integer startTime;
	private List<Point> nodeList;
	
	
	public Integer getVehicledId() {
		return vehicledId;
	}
	
	public void setVehicledId(Integer vehicledId) {
		this.vehicledId = vehicledId;
	}
	
	public Integer getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}
	
	public List<Point> getNodeList() {
		return nodeList;
	}
	
	public void setNodeList(List<Point> nodeList) {
		this.nodeList = nodeList;
	}
	
	public String getNodeListInString() {
				
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < nodeList.size()-1; i++ ) {
			Point first = nodeList.get(i);
			Point second = nodeList.get(i+1);
			builder.append(first.x + "/" + first.y);
			builder.append("to");
			builder.append(second.x + "/" + second.y);
			if(i+2 != nodeList.size()) {
				builder.append(" ");
			}
		}
		
		return builder.toString();
	}

}
