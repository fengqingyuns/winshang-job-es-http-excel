package com.example.demo.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


public class PropertiesUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);
	public static String getProperty(String property, String key) {
		Properties properties = new Properties();
		InputStream resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(property);
		try {
			properties.load(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(resourceAsStream!=null)
				resourceAsStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String property2 = properties.getProperty(key);
		LOGGER.info("properties:{}", property2);
		return property2;
	}
	
	
}
