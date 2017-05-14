package com.yundao.manager.entity;

import javax.persistence.Table;

import framework.entity.BaseEntity;
/**
 * 
 * @ClassName: Test
 * @Description: 测试实体类
 * @author: jinzhaopo
 * @version: V1.0 
 * @date: 2017年5月14日 下午3:29:34
 */
@Table(name="test")
public class TestDomain extends BaseEntity {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
