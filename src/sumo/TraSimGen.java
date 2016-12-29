package sumo;

public class TraSimGen {

	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("TraSimGen <configFileName>");
			return;
		}
		
		PropertiesAccessor propertiesAccessor = new PropertiesAccessor();
		boolean result = propertiesAccessor.readPropertiesFile(args[0]);
		
		if(result == false) {
			System.out.println("Property file could not be opened.");
			return;
		}
		
		RoadGenerator roadGenerator = new RoadGenerator(propertiesAccessor);
		result = roadGenerator.generateRoadTopology();
		
		if(result == false) {
			System.out.println("Road topology could not be generated.");
			return;
		}
		
		TraceGenerator traceGenerator = new TraceGenerator(propertiesAccessor);
		result = traceGenerator.generateTraceModel();
		
		if(result == false) {
			System.out.println("Trace model could not be generated.");
			return;
		}
		
		result = traceGenerator.createTraceModelFile();
		
		if(result == false) {
			System.out.println("Trace model file could not be generated.");
			return;
		}
		
		TLLogicGenerator tlLogicGenerator = new TLLogicGenerator(propertiesAccessor);
		result = tlLogicGenerator.readJunctionInfo();
		
		if(result == false) {
			System.out.println("Junction info cannot be read from the net file.");
			return;
		}
		
		result = tlLogicGenerator.updateTLLogic();
		
		if(result == false) {
			System.out.println("TlLogic info cannot be updated.");
			return;
		}
		
	}

}
