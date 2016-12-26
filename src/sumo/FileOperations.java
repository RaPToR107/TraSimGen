package sumo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public abstract class FileOperations {
	
	protected PrintWriter fileWriter = null;
	protected Scanner fileReader = null;
	
	protected boolean openOutputFile(String fileName) {

		try {
			fileWriter = new PrintWriter(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	protected void closeOutputFile() {
		fileWriter.close();
	}
	
	protected boolean openInputFile(String fileName) {

		try {
			fileReader = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	protected void closeInputFile() {
		fileReader.close();
	}

}
