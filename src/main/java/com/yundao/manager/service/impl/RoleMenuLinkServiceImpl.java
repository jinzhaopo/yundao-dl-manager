package com.yundao.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yundao.tour.mapper.RoleMenuLinkMapper;
import com.yundao.tour.model.link.RoleMenuLink;
import com.yundao.tour.service.RoleMenuLinkService;

import framework.service.impl.BaseServiceImpl;

/**
 * 
 * @ClassName: RoleMenuLinkServiceImpl
 * @Description: TODO
 * @author: zhaopo
 * @date: 2016年11月14日 上午9:11:10
 */
@Service
public class RoleMenuLinkServiceImpl extends BaseServiceImpl<RoleMenuLink> implements RoleMenuLinkService {

	@Autowired
	private RoleMenuLinkMapper roleMenuLinkMapper;

	@Autowired
	public void setMapper(RoleMenuLinkMapper mapper) {
		super.setMapper(mapper);
	}
}
