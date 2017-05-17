package com.yundao.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yundao.tour.mapper.RoleMapper;
import com.yundao.tour.model.link.RoleMenuLink;
import com.yundao.tour.model.link.RoleOperateLink;
import com.yundao.tour.model.manager.User;
import com.yundao.tour.model.rbac.Operate;
import com.yundao.tour.model.rbac.Role;
import com.yundao.tour.service.OperateService;
import com.yundao.tour.service.RoleMenuLinkService;
import com.yundao.tour.service.RoleOperateLinkService;
import com.yundao.tour.service.RoleService;
import com.yundao.tour.service.UserService;

import framework.page.SearchFilter;
import framework.page.SearchFilter.Operator;
import framework.service.impl.BaseServiceImpl;
import framework.util.PropertiesHelper;

/**
 * 
 * @ClassName: RoleServiceImpl
 * @Description: 角色
 * @author: zhaopo
 * @date: 2016年11月11日 下午3:16:57
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private OperateService operateService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleOperateLinkService roleOperateLinkService;

	@Autowired
	private RoleMenuLinkService roleMenuLinkService;

	@Autowired
	public void setMapper(RoleMapper mapper) {
		super.setMapper(mapper);
	}

	/**
	 * 
	 * @Title: editRoleMenu
	 * @Description: 修改角色
	 * @param role
	 * @param menuIds
	 * @param operateIds
	 * @see com.yundao.tour.service.RoleService#editRoleMenu(com.yundao.tour.model.rbac.Role,
	 *      java.lang.Long[], java.lang.Long[])
	 */
	public void editRoleMenu(Role role, Long[] menuIds, Long[] operateIds) {
		int i = 0;
		// 先删除中间表的所有数据
		RoleOperateLink rol = new RoleOperateLink();
		rol.setRoleId(role.getId());
		roleOperateLinkService.deleteByWhere(rol);

		RoleMenuLink rml = new RoleMenuLink();
		rml.setRoleId(rol.getId());
		roleMenuLinkService.deleteByWhere(rml);

		for (Long id : operateIds) {
			RoleOperateLink rol_ = new RoleOperateLink();
			rol_.setRoleId(role.getId());
			rol_.setOperateId(id);

			roleOperateLinkService.save(rol_);
		}

		for (Long id : menuIds) {
			RoleMenuLink rml_ = new RoleMenuLink();
			rml_.setRoleId(role.getId());
			rml_.setMenuId(id);
			
			roleMenuLinkService.save(rml_);
		}
	}

	/**
	 * 
	 * @Title: getAutOperate
	 * @Description: 根据资源id获取授权动作
	 * @param menuId
	 * @return
	 * @see com.yundao.tour.service.RoleService#getAutOperate(java.lang.Long)
	 */
	public List<Operate> getAutOperate(Long menuId) {
		List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
		searchFilters.add(new SearchFilter("isAuthorizationType", Operator.EQ, true));
		if (menuId != null) {
			searchFilters.add(new SearchFilter("menuId", Operator.EQ, menuId));
		}

		return operateService.getList(searchFilters);
	}

	/**
	 * 
	 * @Title: deleteById
	 * @Description: 删除的时候注意级联删除
	 * @param id
	 * @return
	 * @see framework.service.impl.BaseServiceImpl#deleteById(java.lang.Long)
	 */
	@Override
	public Integer deleteById(Long id) {
		int i = 0;
		// TODO 删除用户
		User user = new User();
		user.setRoleId(id);
		userService.deleteByWhere(user);

		// 删除角色的中间表 2张
		RoleOperateLink rol = new RoleOperateLink();
		rol.setRoleId(id);
		roleOperateLinkService.deleteByWhere(rol);

		RoleMenuLink rml = new RoleMenuLink();
		rml.setRoleId(id);
		roleMenuLinkService.deleteByWhere(rml);

		return super.deleteById(id);
	}

	/**
	 * 
	 * @Title: remove
	 * @Description: 这里其实不应该这么些的 都是role数据不多就循环了
	 * @param ids
	 * @see com.yundao.tour.service.RoleService#remove(java.lang.Long[])
	 */
	public void remove(Long[] ids) {
		for (Long id : ids) {
			deleteById(id);
		}

	}

	/**
	 * 
	 * @Title: save
	 * @Description: 重写save方法
	 * @param record
	 * @return
	 * @see framework.service.impl.BaseServiceImpl#save(framework.model.BaseEntity)
	 */
	@Override
	public Integer save(Role role) {
		int i = 0;
		// 角色标识是唯一的
		super.save(role);
		Role e_role = get("value", role.getValue());
		// 保存2张中间表的数据
		RoleOperateLink rol = new RoleOperateLink();
		rol.setRoleId(e_role.getId());
		roleOperateLinkService.deleteByWhere(rol);

		RoleMenuLink rml = new RoleMenuLink();
		rml.setRoleId(e_role.getId());
		roleMenuLinkService.deleteByWhere(rml);
		return 0;
	}
}
