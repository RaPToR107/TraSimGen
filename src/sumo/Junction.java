package sumo;

import java.awt.Point;
import java.util.StringTokenizer;

public class Junction extends Point {
	
	private Integer noOfRequests;
	
	public Junction(String id, Integer noOfRequests) {
		this.noOfRequests = noOfRequests;
		
		StringTokenizer tokenizer = new StringTokenizer(id, "/ ");
		this.x = Integer.valueOf(tokenizer.nextToken());
		this.y = Integer.valueOf(tokenizer.nextToken());
	}

	public Integer getNoOfRequests() {
		return noOfRequests;
	}	

}
