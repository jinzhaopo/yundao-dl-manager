package framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.yundao.manager.interceptor.AdminDetailsInterceptor;
import com.yundao.manager.interceptor.ErrorInterceptor;
import com.yundao.manager.interceptor.ValidationInterceptor;

/**
 * 
 * @ClassName: InterceptorConfig
 * @Description: 拦截器的配置
 * @author: jinzhaopo
 * @version: V1.0
 * @date: 2017年5月18日 下午11:11:32
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private AdminDetailsInterceptor adminDetailsInterceptor;

	@Autowired
	private ErrorInterceptor errorInterceptor;

	@Autowired
	private ValidationInterceptor validationInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(adminDetailsInterceptor).addPathPatterns("/**").excludePathPatterns("/admin/common/**")
				.excludePathPatterns("/admin/login**").excludePathPatterns("/admin/test**");

		registry.addInterceptor(errorInterceptor).addPathPatterns("/**").excludePathPatterns("/admin/common/**")
				.excludePathPatterns("/admin/test**");

		registry.addInterceptor(validationInterceptor).addPathPatterns("/**").excludePathPatterns("/admin/common/**")
				.excludePathPatterns("/admin/test**");

	}
}
