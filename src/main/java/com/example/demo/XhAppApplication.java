package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
@SpringBootApplication()
@MapperScan({"com.example.demo.modules.login.dao",
			"com.example.demo.modules.reg.dao",
			"com.example.demo.modules.xiaohua.dao",
			"com.example.demo.modules.job.dao"
	})
public class XhAppApplication extends SpringBootServletInitializer{
	 @Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	              return application.sources(XhAppApplication.class);
	          }
	public static void main(String[] args) {
		SpringApplication.run(XhAppApplication.class, args);
	}

}

