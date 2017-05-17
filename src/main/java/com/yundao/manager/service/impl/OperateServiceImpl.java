package com.yundao.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.baisha.util.RegexpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yundao.tour.Constant;
import com.yundao.tour.mapper.OperateMapper;
import com.yundao.tour.model.link.RoleMenuLink;
import com.yundao.tour.model.link.RoleOperateLink;
import com.yundao.tour.model.rbac.Operate;
import com.yundao.tour.model.rbac.Role;
import com.yundao.tour.service.MenuService;
import com.yundao.tour.service.OperateService;
import com.yundao.tour.service.RoleOperateLinkService;
import com.yundao.tour.service.RoleService;

import framework.page.SearchFilter;
import framework.page.SearchFilter.Operator;
import framework.service.impl.BaseServiceImpl;
import framework.util.AppContext;
import framework.util.PropertiesHelper;
import net.sf.json.JSONObject;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 
 * @ClassName: OperateServiceImpl
 * @Description: 操作模块
 * @author: jinzhaopo
 * @version: V1.0
 * @date: 2016年11月6日 下午12:35:17
 */
@Service
public class OperateServiceImpl extends BaseServiceImpl<Operate> implements OperateService {

	private final String APPLICATION_OPERATES = "application_operates";
	@Autowired
	private OperateMapper operateMapper;
	@Autowired
	private RoleOperateLinkService roleOperateLinkService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

	@Autowired
	public void setMapper(OperateMapper mapper) {
		super.setMapper(mapper);
	}

	/**
	 * 
	 * @Title: getOperatesWidthCondition
	 * @Description: 根据菜单id和操作类型获取操作
	 * @param menuId
	 * @param operateType
	 * @return
	 * @see com.yundao.tour.service.OperateService#getOperatesWidthCondition(java.lang.Long,
	 *      boolean)
	 */
	public List<Operate> getOperatesWidthCondition(Long menuId, Boolean operateType) {
		Example example = new Example(Operate.class);
		Criteria createCriteria = example.createCriteria();
		if (operateType != null) {
			createCriteria.andEqualTo("isAuthorizationType", operateType);
		}
		if (menuId != null) {
			createCriteria.andEqualTo("menuId", menuId);
		}
		example.orderBy("priority").asc().orderBy("createDate").asc();
		return selectByExample(example);
	}

	/**
	 * 
	 * @Title: getAllUrlRole
	 * @Description: TODO
	 * @return
	 * @see com.yundao.tour.service.OperateService#getAllUrlRole()
	 */
	public Map<String, String> getAllUrlRole() {
		// 获取所有的操作
		List<Operate> operates = getOperatesWidthCondition(null, null);
		Map<String, String> urls = new HashMap<String, String>();

		if (operates != null && operates.size() > 0) {
			for (Iterator<Operate> iterator = operates.iterator(); iterator.hasNext();) {
				Operate operate = iterator.next();
				if (!operate.getIsAuthorizationType()) {
					urls.put(operate.getPattern(), getRoleSetString(operate.getId()));
				} else {
					urls.put(operate.getPattern(), menuService.getRoleSetString(operate.getMenuId()));
				}
			}
		}
		return urls;
	}

	/**
	 * 
	 * @Title: getRoleSetString
	 * @Description: TODO
	 * @return
	 * @see com.yundao.tour.service.OperateService#getRoleSetString()
	 */
	public String getRoleSetString(Long operateId) {
		StringBuffer stringBuffer = new StringBuffer();
		// 获取操作绑定的权限
		List<RoleOperateLink> list = roleOperateLinkService.getList("operateId", operateId);
		Set<Long> idSet = new LinkedHashSet<Long>();
		for (RoleOperateLink rol : list) {
			idSet.add(rol.getRoleId());
		}
		List<Role> roleSet = roleService.queryByIds(new ArrayList<Long>(idSet));
		if (roleSet != null) {
			for (Role role : roleSet) {
				stringBuffer.append(Constant.SEPARATOR + role.getValue());
			}
		}
		// 增加系统管理员的权限
		String system_manager_role = PropertiesHelper.getPropertiesValue("config", "system_manager_role");
		stringBuffer.append(Constant.SEPARATOR + system_manager_role);

		if (stringBuffer.length() > 0) {
			stringBuffer.deleteCharAt(0);
		}
		return stringBuffer.toString();
	}

	/**
	 * 
	 * @Title: getBestFitOperate
	 * @Description: 根据url获取最合适的action(操作)
	 * @param url
	 * @return
	 * @see com.yundao.tour.service.OperateService#getBestFitOperate(java.lang.String)
	 */
	public Operate getBestFitOperate(String url) {
		// 如果url中包含了工程路径
		String contextPath = AppContext.getContextPath();
		if (!"".equals(contextPath) && url.indexOf(contextPath) != -1) {
			url = url.substring(contextPath.length());
		}

		// 如果url中包含"/"，则表明是整路径
		if (url.indexOf("/") == -1) {
			String path = AppContext.getRequest().getRequestURI().substring(contextPath.length() + 1);
			path = StringUtils.substringBeforeLast(path, "/");
			url = path + "/" + url;
		}

		// 获取系统所有动作
		List<Operate> allOperateList = getAllOperates();
		Operate curOperate = null;
		for (Iterator<Operate> iter = allOperateList.iterator(); iter.hasNext();) {
			Operate paraOperate = iter.next();
			String pattern = paraOperate.getPattern();
			if (StringUtils.isEmpty(pattern)) {
				pattern = paraOperate.getUrl();
			}
			if (pattern != null && StringUtils.isNotEmpty(pattern) && RegexpHelper.isGlobmatches(url, pattern)) {
				curOperate = paraOperate;
				break;
			}
		}
		return curOperate;
	}

	/**
	 * 
	 * @Title: getAllOperates
	 * @Description: 获取所有的操作，先从内存中获取，第一次为空的时候，先放入内存
	 *               (根据当前的url获取匹配当前的操作、将url与操作的角色存入缓存中)
	 * @return
	 * @return: List<Operate>
	 */
	@SuppressWarnings("unchecked")
	private List<Operate> getAllOperates() {
		ServletContext servletContext = AppContext.getServletContext();
		List<Operate> operates = (List<Operate>) servletContext.getAttribute(APPLICATION_OPERATES);
		if (operates == null || operates.size() == 0) {
			operates = getOperatesWidthCondition(null, null);
			servletContext.setAttribute(APPLICATION_OPERATES, operates);
		}
		return operates;
	}

	/**
	 * 
	 * @Title: getPrivOperates
	 * @Description: 获取授权的操作
	 * @param roleId
	 * @param operate
	 * @param isDiaplayInToolBar
	 * @return
	 * @see com.yundao.tour.service.OperateService#getPrivOperates(java.lang.Long,
	 *      com.yundao.tour.model.rbac.Operate, boolean)
	 */
	public List<Operate> getPrivOperates(Long roleId, Long menuId, boolean isDiaplayInToolBar) {
		Assert.notNull(menuId, "菜单id不能为空");
		List<Long> operateIds = null;
		List<RoleOperateLink> rols = roleOperateLinkService.getList("roleId", roleId);
		if (rols != null && rols.size() > 0) {
			operateIds = new ArrayList<Long>();
			for (RoleOperateLink rol : rols) {
				operateIds.add(rol.getOperateId());
			}
		}

		List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
		searchFilters.add(new SearchFilter("menuId", Operator.EQ, menuId));
		searchFilters.add(new SearchFilter("isAuthorizationType", Operator.EQ, true));
		searchFilters.add(new SearchFilter("isDiaplayInToolBar", Operator.EQ, isDiaplayInToolBar));
		if (operateIds != null) {
			searchFilters.add(new SearchFilter("id", Operator.IN, operateIds));
		}

		return getList(searchFilters);
	}

	/**
	 * 
	 * @Title: deleteById
	 * @Description: 重写删除的方法 删除的时候要把中间表的数据删除
	 * @param id
	 * @return
	 * @see framework.service.impl.BaseServiceImpl#deleteById(java.lang.Long)
	 */
	@Override
	public Integer deleteById(Long id) {
		int i = 0;
		// 先删除中间表的数据
		RoleOperateLink rol = new RoleOperateLink();
		rol.setOperateId(id);
		roleOperateLinkService.deleteByWhere(rol);

		// 删除操作的
		return super.deleteById(id);
	}

	/**
	 * 
	 * @Title: updatePriority
	 * @Description: 更新排序
	 * @param ids
	 * @param priority
	 * @see com.yundao.tour.service.OperateService#updatePriority(java.lang.Long[],
	 *      java.lang.Integer[])
	 */
	public void updatePriority(Long[] ids, Integer[] priority) {
		for (int i = 0, len = ids.length; i < len; i++) {
			Operate operate = this.queryById(ids[i]);
			operate.setPriority(priority[i].longValue());
			updateSelective(operate);
		}

	}

	public JSONObject getJson(Operate operate) {
		JSONObject obj = new JSONObject();
		obj.put("idKey", "o" + operate.getId());
		obj.put("pIdKey", "m" + operate.getMenuId());
		obj.put("id", operate.getId());
		obj.put("name", operate.getName());
		obj.put("url", operate.getUrl());
		obj.put("html", operate.getHtml());
		obj.put("btnIcon", operate.getBtnIcon());
		obj.put("type", "operate");
		obj.put("pattern", operate.getPattern());
		// obj.put("parentId", operate.getMenuId());
		return obj;
	}

	/**
	 * 
	 * @Title: getOperates
	 * @Description: 根据角色获取所有的操作
	 * @param roleId
	 * @return
	 * @see com.yundao.tour.service.OperateService#getOperates(java.lang.Long)
	 */
	public List<Operate> getOperates(Long roleId) {
		List<RoleOperateLink> list = roleOperateLinkService.getList("roleId", roleId);
		List<Long> idsList = new ArrayList<Long>();
		for (RoleOperateLink rol : list) {
			idsList.add(rol.getOperateId());
		}
		return queryByIds(idsList);
	}
}
