/**
 * 
 */
package com.cogmento.automation.web.tool;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Harini
 *
 */
public class ConfigUtil {
	private static Properties configs = new Properties();
	/*
	 * Loads the config file config.properties under <project_root>/config
	 * 
	 */
	static {
		String filePath = System.getProperty("user.dir") + "/config/config.properties";
		try {
			FileInputStream ip = new FileInputStream(filePath);
			configs.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return configs.getProperty(key);
	}
}
