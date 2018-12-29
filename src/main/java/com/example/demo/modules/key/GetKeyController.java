package com.example.demo.modules.key;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.modules.util.AJAXResult;
import com.example.demo.util.GfAppWebConfig;

@Controller
public class GetKeyController {

	@RequestMapping("/getAesKey")
	@ResponseBody
	public AJAXResult getAesKey(){
		AJAXResult result = new AJAXResult();
		String key = GfAppWebConfig.getValue("app.aes.key");
		result.setKey(key);
		return result;
	}
}
