package sumo;

public class RoadGenerator {
	
	private PropertiesAccessor propertiesAccessor = null;

	public RoadGenerator(PropertiesAccessor propertiesAccessor) {
		this.propertiesAccessor = propertiesAccessor;
	}
	
	public boolean generateRoadTopology() {
		
		Integer xNumber = propertiesAccessor.getInt("xNumber");
		Integer yNumber = propertiesAccessor.getInt("yNumber");
		Integer gridLength = propertiesAccessor.getInt("gridLength");
		String netFileName = propertiesAccessor.getString("netFileName");
		
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec(new String[]{"netgenerate", 
					"-g", "true", 
					"--grid.x-number", xNumber.toString(), 
					"--grid.y-number", yNumber.toString(), 
					"--grid.length", gridLength.toString(),
					"-o", netFileName,
					"--no-turnarounds", "true",
					"--no-left-connections", "true",
					"-j","traffic_light"});
			process.waitFor();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
