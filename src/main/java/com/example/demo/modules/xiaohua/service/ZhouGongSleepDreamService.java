package com.example.demo.modules.xiaohua.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.KeyConfig;
import com.example.demo.config.UrlConfig;
import com.example.demo.http.HttpClient;
import com.example.demo.http.HttpClientResponse;
import com.example.demo.modules.util.AJAXResult;
import com.example.demo.modules.xiaohua.entity.Dream;
import com.example.demo.modules.xiaohua.entity.Joke;

//import net.sf.json.JSONObject;

@Service
public class ZhouGongSleepDreamService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZhouGongSleepDreamService.class);
	/** 连接超时时间，毫秒 */
	protected static final int HTTP_CONN_TIME_OUT = 10000;
	/** 读取超时时间，毫秒 */
	protected static final int HTTP_SOCKET_TIME_OUT = 20000;
	public AJAXResult getSleepDream(String q) {
		AJAXResult result = new AJAXResult();
		String url = UrlConfig.ZHOUGONG_SLEEP_DREAM;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH");//设置日期格式
		
		NameValuePair[] data = { 
				new BasicNameValuePair("q",q),
			
				new  BasicNameValuePair("key", KeyConfig.ZHOU_GONG_DREAM_KEY)
		};
		HttpClientResponse response = doRequest(url,data);
		
		String body = response.getBody();
		LOGGER.info("body:{}",body);
		List<Dream> list = new ArrayList<Dream>();
	//	 JSONObject object = JSONObject.fromObject(body);
		 com.alibaba.fastjson.JSONObject object = JSON.parseObject(body);
	//	net.sf.json.JSONArray jsonArray = object.getJSONArray("result"); 
		 JSONArray jsonArray = object.getJSONArray("result");
		 
		for(int i = 0;i<jsonArray.size();i++) {
			JSONObject obj = (JSONObject) jsonArray.get(i);
			Dream dream = obj.toJavaObject(Dream.class);
			//String title = (String) obj.get("title");
		//	String des = obj.getString("des");
			//Dream dream = new Dream();
			String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		//	dream.setTitle(title);dream.setDes(des);
			dream.setTime(date);
			list.add(dream);
		}
		result.setData(list);
		return result;
	}
	
	protected HttpClientResponse doRequest(String url, NameValuePair[] data) {
		HttpClientResponse hcres = null;
		try {
			LOGGER.info("url:{}", url);

			HttpClient httpClient = HttpClient.get(url);
			httpClient.timeout(HTTP_CONN_TIME_OUT, HTTP_SOCKET_TIME_OUT);
			httpClient.addParameters(data);
			hcres = httpClient.action();
		} catch (Exception e) {
			LOGGER.error("error", e);
		}
		return hcres;
	}
}
