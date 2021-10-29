package jdbc.util;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
private static final Properties properties = new Properties();

	private PropertiesUtil() {
		
	}
	
	static {
		loadProperties();	
	}
	
	public static String get(String key) {
		return properties.getProperty(key);
	}
	
	private static void loadProperties() {
		try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")){	
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

}
