package com.yundao.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yundao.manager.entity.TestDomain;
import com.yundao.manager.mapper.TestMapper;
import com.yundao.manager.service.TestService;

import framework.mapper.MyMapper;
import framework.service.impl.BaseServiceImpl;

/**
 * 
 * @ClassName: TestServiceImpl
 * @Description: 测试实现类
 * @author: jinzhaopo
 * @version: V1.0
 * @date: 2017年5月14日 下午5:46:28
 */
@Service
public class TestServiceImpl extends BaseServiceImpl<TestDomain> implements TestService {

	@Autowired
	public void setMapper(TestMapper mapper) {
		super.setMapper(mapper);
	}
}
