package com.dante.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/angular")
public class AngularController {

	@RequestMapping("/expression")
	public String getAngularExpression() {
		return "angular/expression";
	}
	
	@RequestMapping("/module")
	public String getAngularModule() {
		return "angular/module";
	}

}
