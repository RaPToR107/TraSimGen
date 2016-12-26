package sumo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesAccessor {
	
	private Properties properties = null;
	
	public boolean readPropertiesFile(String fileName) {
		
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		

		try {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			properties = null;
			return false;
		}
		
		return true;
	}
	
	public String getString(String property) {
		
		if(properties == null) {
			return null;
		}
		
		return properties.getProperty(property);
		
	}
	
	public Integer getInt(String property) {
		
		if(properties == null) {
			return null;
		}
		
		String prop = properties.getProperty(property);
		
		if(prop == null) {
			return null;
		}
		
		try {
			return Integer.valueOf(prop);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public Double getDouble(String property) {
		
		if(properties == null) {
			return null;
		}
		
		String prop = properties.getProperty(property);
		
		if(prop == null) {
			return null;
		}
		
		try {
			return Double.valueOf(prop);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}	
		
	}
}
