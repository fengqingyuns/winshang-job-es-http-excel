package com.example.demo.modules.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainAppController {

	@RequestMapping("/main")
	public String toMain() {
		return "main/main";
	}
}
