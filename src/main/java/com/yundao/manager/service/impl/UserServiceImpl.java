package com.yundao.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yundao.tour.mapper.UserMapper;
import com.yundao.tour.model.manager.User;
import com.yundao.tour.service.UserService;

import framework.page.Pager;
import framework.service.impl.BaseServiceImpl;

/**
 * 
 * @ClassName: UserServiceImpl
 * @Description: 用户
 * @author: zhaopo
 * @date: 2016年11月14日 下午4:06:40
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	public void setMapper(UserMapper mapper) {
		super.setMapper(mapper);
	}


}
