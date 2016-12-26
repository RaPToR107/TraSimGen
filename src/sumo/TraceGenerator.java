package sumo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TraceGenerator extends FileOperations {
	
	private PropertiesAccessor propertiesAccessor = null;
	private Map<Integer, Vehicle> vehicles;
	private List<TrafficFlow> trafficFlows;

	public TraceGenerator(PropertiesAccessor propertiesAccessor) {
		this.propertiesAccessor = propertiesAccessor;
		vehicles = new HashMap<Integer, Vehicle>();
		trafficFlows = new ArrayList<TrafficFlow>();
	}
	
	private void initializeFlows() {
		Integer maxStepBetweenVehicles = propertiesAccessor.getInt("maxStepBetweenVehicles");
		Integer maxStartingTimeOfFlows = propertiesAccessor.getInt("maxStartingTimeOfFlows");
		Integer xNumber = propertiesAccessor.getInt("xNumber");
		Integer yNumber = propertiesAccessor.getInt("yNumber");
		Integer seed = propertiesAccessor.getInt("seed");
		
		RandomNumberGenerator random = RandomNumberGenerator.getInstance(seed);
		
		for(int i = 1; i < (xNumber-1); i++) {
			TrafficFlow trafficFlow = new TrafficFlow();
			trafficFlow.setStartingPoint(new Point(i, 0));
			trafficFlow.setEndPoint(new Point(i, yNumber - 1));
			trafficFlow.setDirection(Direction.UP);
			trafficFlow.setProbability(random.getNextInt(maxStepBetweenVehicles)+1);
			trafficFlow.setStartingTime(random.getNextInt(maxStartingTimeOfFlows));
			trafficFlow.generateRoute();
			trafficFlows.add(trafficFlow);
		}

		for(int i = 1; i < (xNumber-1); i++) {
			TrafficFlow trafficFlow = new TrafficFlow();
			trafficFlow.setStartingPoint(new Point(i, yNumber - 1));
			trafficFlow.setEndPoint(new Point(i, 0));
			trafficFlow.setDirection(Direction.DOWN);
			trafficFlow.setProbability(random.getNextInt(maxStepBetweenVehicles)+1);
			trafficFlow.setStartingTime(random.getNextInt(maxStartingTimeOfFlows));
			trafficFlow.generateRoute();
			trafficFlows.add(trafficFlow);
		}
		
		for(int i = 1; i < (yNumber-1); i++) {
			TrafficFlow trafficFlow = new TrafficFlow();
			trafficFlow.setStartingPoint(new Point(0, i));
			trafficFlow.setEndPoint(new Point(xNumber-1, i));
			trafficFlow.setDirection(Direction.RIGHT);
			trafficFlow.setProbability(random.getNextInt(maxStepBetweenVehicles)+1);
			trafficFlow.setStartingTime(random.getNextInt(maxStartingTimeOfFlows));
			trafficFlow.generateRoute();
			trafficFlows.add(trafficFlow);
		}
		for(int i = 1; i < (yNumber-1); i++) {
			TrafficFlow trafficFlow = new TrafficFlow();
			trafficFlow.setStartingPoint(new Point(xNumber - 1, i));
			trafficFlow.setEndPoint(new Point(0, i));
			trafficFlow.setDirection(Direction.LEFT);
			trafficFlow.setProbability(random.getNextInt(maxStepBetweenVehicles)+1);
			trafficFlow.setStartingTime(random.getNextInt(maxStartingTimeOfFlows));
			trafficFlow.generateRoute();
			trafficFlows.add(trafficFlow);
		}
		
	}
	
	public boolean generateTraceModel() {
		
		initializeFlows();
		
		Integer simulationTime = propertiesAccessor.getInt("simulationTime");
		int vehicleId = 1;
		
		for(int i = 1; i <= simulationTime; i++) {
			for(TrafficFlow cur : trafficFlows) {
				int relativeTime = i - cur.getStartingTime();
				if(relativeTime >= 0 && (relativeTime % cur.getProbability() == 0)) {
					generateVehicle(vehicleId, i, cur);
					vehicleId++;
				}
			}		
		}
		
		return true;
	}
	
	private void generateVehicle(int vehicleId, int startingTime, TrafficFlow flow) {	
		
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicledId(vehicleId);
		vehicle.setStartTime(startingTime);
		vehicle.setNodeList(flow.getRoute());
		vehicles.put(vehicleId, vehicle);	
	}
	
	public boolean createTraceModelFile() {
		
		String traceFileName = propertiesAccessor.getString("traceFileName");
		
		boolean result = openOutputFile(traceFileName);
		if(result == false) {
			return false;
		}
		
		fileWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		fileWriter.println("<routes xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://www.sumo.dlr.de/xsd/routes_file.xsd\">");
		for(Vehicle cur : vehicles.values()) {
			fileWriter.println("<vehicle id=\"" + cur.getVehicledId() + "\" depart=\""+ cur.getStartTime() +".00\">");
			fileWriter.println("<route edges=\"" + cur.getNodeListInString() + "\"/>");
			fileWriter.println("</vehicle>");		
		}
		fileWriter.println("</routes>");
		closeOutputFile();
		
		return true;
	}

}
