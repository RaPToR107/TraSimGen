package sumo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class TrafficFlow {
	
	private Point startingPoint;
	private Point endPoint;
	private Direction direction;
	private Integer probability;
	private Integer startingTime;
	private List<Point> route;
	
	public TrafficFlow() {
		route = new ArrayList<Point>();
	}

	public Point getStartingPoint() {
		return startingPoint;
	}
	
	public void setStartingPoint(Point startingPoint) {
		this.startingPoint = startingPoint;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Integer getProbability() {
		return probability;
	}
	
	public void setProbability(Integer probability) {
		this.probability = probability;
	}
	
	public Integer getStartingTime() {
		return startingTime;
	}

	public void setStartingTime(Integer startingTime) {
		this.startingTime = startingTime;
	}
	
	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public List<Point> getRoute() {
		return route;
	}

	public void generateRoute() {
		
		if(direction == Direction.UP) {
			for(int i = startingPoint.y; i <= endPoint.y; i ++) {
				route.add(new Point(startingPoint.x, i));
			}	
		}
		else if(direction == Direction.DOWN) {
			for(int i = startingPoint.y; i >= endPoint.y; i --) {
				route.add(new Point(startingPoint.x, i));
			}	
		}
		else if(direction == Direction.RIGHT) {
			for(int i = startingPoint.x; i <= endPoint.x; i ++) {
				route.add(new Point(i, startingPoint.y));
			}
		}
		else if(direction == Direction.LEFT) {
			for(int i = startingPoint.x; i >= endPoint.x; i --) {
				route.add(new Point(i, startingPoint.y));
			}
		}
	}
}
