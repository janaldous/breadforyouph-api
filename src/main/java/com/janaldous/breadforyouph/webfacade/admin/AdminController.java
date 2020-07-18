package com.janaldous.breadforyouph.webfacade.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/login")
	public ResponseEntity<String> authCheck() {
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
