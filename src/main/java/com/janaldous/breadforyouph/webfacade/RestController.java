package com.janaldous.breadforyouph.webfacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.janaldous.breadforyouph.service.RestService;

@Controller
@RequestMapping(path = "/book")
@ResponseBody
public class RestController {
	private final RestService restService;

	@Autowired
	public RestController(RestService restService) {
		this.restService = restService;
	}

	@RequestMapping("/")
	public String home() {
		return "Hello Docker World";
	}

	@RequestMapping(value = "data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity onResRequest(@RequestParam(value = "id") String id) {
		Long Id = Long.parseLong(id);
		return ResponseEntity.ok(restService.getBookStats(Id));
	}
}