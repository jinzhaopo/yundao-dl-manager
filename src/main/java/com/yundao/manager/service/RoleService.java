package com.yundao.manager.service;

import java.util.List;

import com.yundao.manager.entity.rbac.Operate;
import com.yundao.manager.entity.rbac.Role;

import framework.service.BaseService;

/**
 * 
 * @ClassName: RoleService
 * @Description: 角色service
 * @author: zhaopo
 * @date: 2016年11月11日 下午3:16:07
 */
public interface RoleService extends BaseService<Role> {

	/**
	 * 
	 * @Title: editRoleMenu
	 * @Description: 修改角色菜单
	 * @param role
	 * @param menuIds
	 * @param operateIds
	 * @return: void
	 */
	public void editRoleMenu(Role role, Long[] menuIds, Long[] operateIds);

	/**
	 * 
	 * @Title: getAutOperate
	 * @Description: 根据资源id获取所有授权的操作
	 * @param object
	 * @return
	 * @return: List<Operate>
	 */
	public List<Operate> getAutOperate(Long menuId);

	/**
	 * 
	 * @Title: remove
	 * @Description: 批量删除 这里本来应该要重写的 都是角色数据不多就直接循环了
	 * @param ids
	 * @return: void
	 */
	public void remove(Long[] ids);

}
