package com.yundao.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yundao.manager.entity.rbac.User;
import com.yundao.manager.mapper.UserMapper;
import com.yundao.manager.service.UserService;

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
