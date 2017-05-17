package com.yundao.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yundao.manager.Application;
import com.yundao.manager.entity.TestDomain;
import com.yundao.manager.service.TestService;

import framework.page.Order;
import framework.page.Pager;
import framework.page.SearchFilter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {

	@Autowired
	private TestService testService;

	@Test
	@Transactional
	public void contextLoads() {
		// System.out.println(testService.queryAll().get(0).getName());
		// System.out.println(testService.queryAll().size());
		// Pager pager = new Pager();
		// List<SearchFilter> sfList = new ArrayList<>();
		// pager.addOrder(Order.asc("id"));
		// Pager find = testService.find(pager, sfList);
		// for(TestDomain test : (List<TestDomain>)find.getList()){
		// System.out.println(test.getName());
		// }

		TestDomain test = new TestDomain();
		test.setName("11111112222222");

		TestDomain test1 = new TestDomain();
		test1.setName("11111112222222");

		testService.save(test);
		int i = 0;
		int j = 4 / i;
		testService.save(test1);
	}

}