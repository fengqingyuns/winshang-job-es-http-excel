package com.example.demo.modules.xiaohua.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.demo.config.UrlConfig;
import com.example.demo.http.HttpClient;
import com.example.demo.http.HttpClientResponse;
import com.example.demo.modules.util.AJAXResult;
import com.example.demo.modules.xiaohua.entity.Joke;

import net.sf.json.JSONObject;


@Service
public class JokeService {
	public static int num = 1;
	public static int getNum() {
		return num;
	}
	public static void setNum(int num) {
		JokeService.num = num;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(JokeService.class);
	/** 连接超时时间，毫秒 */
	protected static final int HTTP_CONN_TIME_OUT = 10000;
	/** 读取超时时间，毫秒 */
	protected static final int HTTP_SOCKET_TIME_OUT = 20000;
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");// 设置日期格式
	public AJAXResult getJokes(String page,String pagesize,String time,String sort ) {
		AJAXResult result = new AJAXResult();
		String url = UrlConfig.TIAN_QI_URL;
		 time = String.valueOf(System.currentTimeMillis());
		time = time.substring(0, 10);
		NameValuePair[] data = { //
				new BasicNameValuePair("sort", sort),
				new BasicNameValuePair("page", page),
				new BasicNameValuePair("pagesize", pagesize) ,
				new BasicNameValuePair("time", time),
				new  BasicNameValuePair("key", "0527dacdc8ec5781816934fded5d7446")
		};
		try {
			HttpClientResponse response = doRequest(url,data);
			LOGGER.info("response",response);
			String body = response.getBody();
			List<Joke> list = new ArrayList<Joke>();
			 JSONObject object = JSONObject.fromObject(body);
			 LOGGER.info("JSONObject ", object);
			 JSONObject jo = object.getJSONObject("result");
			 LOGGER.info(" JSONObject---", jo);
			net.sf.json.JSONArray jsonArray = jo.getJSONArray("data"); 
			LOGGER.info("net.sf.json.JSONArray", jsonArray);
			for(int i = 0;i<jsonArray.size();i++) {
				JSONObject obj = (JSONObject) jsonArray.get(i);
				String content = (String) obj.get("content");
				String updatetime = obj.getString("updatetime");
				Joke joke = new Joke();joke.setTitle(df.format(new Date())+".笑话");;joke.setContent(content);joke.setTitle(updatetime);joke.setTime(updatetime);
				list.add(joke);
			}
			for(int i=0;i<list.size();i++) {
				System.out.println(list.get(i));
			}
			result.setSuccess(true);
			result.setData(list);
			result.setMsg("请求成功");
		} catch (Exception e) {
			// TODO: handle exception
		    
			LOGGER.error("获取笑话失败",e);
		}
		
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
