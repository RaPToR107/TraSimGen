package sumo;

import java.util.Random;

public class RandomNumberGenerator {
	
	private static RandomNumberGenerator instance = null;
	private Random random = null;
	
	public static RandomNumberGenerator getInstance(int seed) {
		if(instance == null) {
			instance = new RandomNumberGenerator(seed);
		}
		return instance;
	}
	
	private RandomNumberGenerator(int seed) {
		random = new Random(seed);
	}
	
	public int getNextInt(int bound) {
		return random.nextInt(bound);
	}

}
