package com.yundao.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yundao.manager.entity.link.RoleOperateLink;
import com.yundao.manager.mapper.RoleOperateLinkMapper;
import com.yundao.manager.service.RoleOperateLinkService;

import framework.service.impl.BaseServiceImpl;

/**
 * 
 * @ClassName: RoleOperateServiceImpl
 * @Description: TODO
 * @author: zhaopo
 * @date: 2016年11月14日 上午9:05:18
 */
@Service
public class RoleOperateLinkServiceImpl extends BaseServiceImpl<RoleOperateLink> implements RoleOperateLinkService {

	
	@Autowired
	private RoleOperateLinkMapper roleOperateLinkMapper;
	
	@Autowired
	public void setMapper(RoleOperateLinkMapper mapper) {
		// TODO Auto-generated method stub
		super.setMapper(mapper);
	}
}
