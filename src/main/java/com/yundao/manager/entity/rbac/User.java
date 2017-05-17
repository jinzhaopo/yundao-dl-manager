package com.yundao.manager.entity.rbac;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import framework.entity.PriorityEntity;
import framework.util.PropertiesHelper;

/**
 * 
 * @ClassName: User
 * @Description: 用户
 * @author: zhaopo
 * @date: 2016年11月3日 下午1:52:56
 */
@Table(name = "s_manager_user")
public class User extends PriorityEntity {

	private static final long serialVersionUID = -3224845335788125657L;
	/**
	 * 登陆账号
	 */
	@Column(name = "user_name")
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 真实姓名
	 */
	@Column(name = "real_name")
	private String realName;
	/**
	 * 是否禁止账号登陆
	 */
	@Column(name = "is_account_enabled")
	private Boolean isAccountEnabled;
	/**
	 * 上次登录的sessionId
	 */
	@Column(name = "login_id")
	private String loginId;
	/**
	 * 登录的次数
	 */
	@Column(name = "login_count")
	private Integer loginCount;
	/**
	 * 上次登录时间
	 */
	@Column(name = "last_login_time")
	private Date lastLoginTime; // 上次登录时间
	/**
	 * 上次登录的IP
	 */
	@Column(name = "last_login_ip")
	private String lastLoginIp;
	/**
	 * 账号锁定日期
	 */
	@Column(name = "locked_date")
	private Date lockedDate;
	/**
	 * 登录失败次数
	 */
	@Column(name = "login_failure_count")
	private Integer loginFailureCount;
	/**
	 * 是否锁定
	 */
	@Column(name = "is_account_locked")
	private Boolean isAccountLocked;// 账号是否锁定

	/*** 关联关系 ****/
	/**
	 * 用户所属角色
	 */
	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "dept_id")
	private Long deptId;

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Boolean getIsAccountEnabled() {
		return isAccountEnabled;
	}

	public void setIsAccountEnabled(Boolean isAccountEnabled) {
		this.isAccountEnabled = isAccountEnabled;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public Boolean getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(Boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	/**
	 * 是否系统管理员
	 * 
	 * @return
	 */
	@Transient
	@JsonIgnore
	public boolean isSystemManager() {
		// 增加系统管理员的权限
		String system_manager_user = PropertiesHelper.getPropertiesValue("config", "system_manager_user");
		List<String> system_manager_users = Arrays.asList(system_manager_user.split(","));
		return system_manager_users.contains(getUserName());
	}

}
