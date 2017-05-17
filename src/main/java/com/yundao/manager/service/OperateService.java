package com.yundao.manager.service;

import java.util.List;
import java.util.Map;

import com.yundao.manager.entity.rbac.Operate;

import framework.service.BaseService;
import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: OperateService
 * @Description: 操作管理模块
 * @author: jinzhaopo
 * @version: V1.0
 * @date: 2016年11月6日 下午12:34:18
 */
public interface OperateService extends BaseService<Operate> {

	/**
	 * 
	 * @Title: getOperatesWidthCondition
	 * @Description: 根据菜单id和操作类型获取操作
	 * @param menuId
	 * @param operateType
	 * @return
	 * @return: List<Operate>
	 */
	public abstract List<Operate> getOperatesWidthCondition(Long menuId, Boolean operateType);

	/**
	 * 
	 * @Title: getAllUrlRole
	 * @Description: TODO
	 * @return
	 * @return: Map<String,String>
	 */
	public Map<String, String> getAllUrlRole();

	/**
	 * 
	 * @Title: getRoleSetString
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	public String getRoleSetString(Long operateId);

	/**
	 * 
	 * @Title: getBestFitOperate
	 * @Description: 根据url获取最合适的action(操作)
	 * @param url
	 * @return
	 * @return: Operate
	 */
	public abstract Operate getBestFitOperate(String url);

	/**
	 * 
	 * @Title: getPrivOperates
	 * @Description: 获取授权的操作
	 * @param roleIds
	 * @param menu
	 * @param isDiaplayInToolBar
	 * @return
	 * @return: List<Operate>
	 */
	public abstract List<Operate> getPrivOperates(Long roleId, Long menuId, boolean isDiaplayInToolBar);

	/**
	 * 
	 * @Title: updatePriority
	 * @Description: 更新排序
	 * @param ids
	 * @param priority
	 * @return: void
	 */
	public void updatePriority(Long[] ids, Integer[] priority);

	/**
	 * 
	 * @Title: getJson
	 * @Description: TODO
	 * @param operate
	 * @return
	 * @return: JSONObject
	 */
	public JSONObject getJson(Operate operate);

	/**
	 * 
	 * @Title: getOperates
	 * @Description: 根据角色获取所有的操作
	 * @param roleId
	 * @return
	 * @return: List<Operate>
	 */
	public List<Operate> getOperates(Long roleId);

}
