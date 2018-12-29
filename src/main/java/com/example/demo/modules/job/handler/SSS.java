package com.example.demo.modules.job.handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.demo.util.DateCalcUtil;
import com.example.demo.util.DateFormatUtil;

public class SSS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long time2 = new Date().getTime();
		Calendar curr = Calendar.getInstance();

				curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)-4);
curr.setTime(new Date(2014, 01, 01));
				Date date=curr.getTime();
				long time = date.getTime();
		/*System.out.println(new Date());
		System.out.println(time2);
		String time = String.valueOf(time2);
			time = time.substring(0, 10);
		System.out.println(time);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
*/		System.out.println(time);
	
	}
	
}
