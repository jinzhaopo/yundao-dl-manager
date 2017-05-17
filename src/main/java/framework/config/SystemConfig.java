package framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: SystemConfig
 * @Description: 一些系统的配置
 * @author: jinzhaopo
 * @version: V1.0
 * @date: 2017年5月17日 下午10:43:55
 */
@ConfigurationProperties(prefix = "system")
@PropertySource("classpath:config.properties")
@Component
public class SystemConfig {

	//system
	/**
	 * 项目的名称
	 */
	private String projectName;
	
	/**
	 * 角色
	 */
	private String managerRole;
	
	/**
	 * 用户
	 */
	private String managerUser;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getManagerRole() {
		return managerRole;
	}

	public void setManagerRole(String managerRole) {
		this.managerRole = managerRole;
	}

	public String getManagerUser() {
		return managerUser;
	}

	public void setManagerUser(String managerUser) {
		this.managerUser = managerUser;
	}
	
	
	
	
}
