package com.example.demo.util;

import java.io.InputStreamReader;
import java.util.Properties;
/**
*  <p>Title：GfAppWebConfig.java</p>
*  <p>Description：获取AppWeb配置文件</p>
*  @author
*/
public class GfAppWebConfig {

	//private static InputStreamReader inputStream;
	private static Properties properties;
	// properties.load(new
	// InputStreamReader(gf.getClass().getClassLoader().getResourceAsStream("properties/gfyun_app_web.properties"),
	// "UTF-8"));
	static {
		GfAppWebConfig gf = new GfAppWebConfig();
		// inputStream = new
		// InputStreamReader(gf.getClass().getClassLoader().getResourceAsStream("properties/gfyun_app_web.properties"));
		properties = new Properties();
		try {
			properties.load(new InputStreamReader(
					gf.getClass().getClassLoader().getResourceAsStream("properties/key.properties"),
					"UTF-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 获取属性值
	 * 
	 * @param name 入参key
	 * @return value 获得对应的value值
	 */
	public static String getValue(String name) {
		String value = properties.getProperty(name);
		return value;
	}

}
