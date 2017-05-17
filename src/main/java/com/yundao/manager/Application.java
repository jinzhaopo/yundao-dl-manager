package com.yundao.manager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @ClassName: Application
 * @Description: 启动类
 * @author: jinzhaopo
 * @version: V1.0
 * @date: 2017年5月14日 下午5:49:11
 */
@SpringBootApplication
@MapperScan(basePackages = "com.yundao.manager.mapper")
@ComponentScan("framework")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}