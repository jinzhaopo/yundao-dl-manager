package com.yundao.manager.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yundao.manager.entity.TestDomain;
import com.yundao.manager.service.TestService;

@RestController
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@RequestMapping("/test2")
	public List<TestDomain> controller(){
		List<TestDomain> queryAll = testService.queryAll();
		return queryAll;
		
	}
}