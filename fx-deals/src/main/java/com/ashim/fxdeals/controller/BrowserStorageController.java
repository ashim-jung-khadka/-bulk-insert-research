package com.ashim.fxdeals.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ashimjk on 12/17/2018
 */
@Controller
public class BrowserStorageController {

	@GetMapping("/input")
	public String testPage() {
		return "input";
	}

	@GetMapping("/output")
	public String outputPage() {
		return "output";
	}
}
