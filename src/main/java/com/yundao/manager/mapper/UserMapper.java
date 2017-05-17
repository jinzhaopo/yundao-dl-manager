package com.yundao.manager.mapper;

import java.util.List;
import java.util.Map;

import com.yundao.manager.bean.UserBean;
import com.yundao.manager.entity.rbac.User;

import framework.mapper.MyMapper;

/**
 * 
 * @ClassName: UserDao
 * @Description: 用户
 * @author: zhaopo
 * @date: 2016年11月3日 下午2:09:43
 */
public interface UserMapper extends MyMapper<User> {
	/**
	 * 
	 * @Title: find
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: List<UserBean>
	 */
	public List<UserBean> find(Map<String, Object> map);

}
