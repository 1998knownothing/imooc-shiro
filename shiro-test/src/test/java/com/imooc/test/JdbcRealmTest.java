package com.imooc.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;

import org.apache.shiro.subject.Subject;

import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;

public class JdbcRealmTest {

	
	DruidDataSource dataSource=new DruidDataSource();
	{

		
		dataSource.setUrl("jdbc:mysql://localhost:3306/shiro?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone = GMT");
		dataSource.setUsername("root");
		dataSource.setPassword("1158982120fire");
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	}
	
	@Test
	public void testAuthentication() {
		JdbcRealm jdbcRealm=new JdbcRealm();
		jdbcRealm.setDataSource(dataSource);
		//.构建securityManager环境
		DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
		defaultSecurityManager.setRealm(jdbcRealm);
		//主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject=SecurityUtils.getSubject();
		
		UsernamePasswordToken token=new UsernamePasswordToken("Mark","123");
		
		subject.login(token);
		
		System.out.println("is Authentication "+subject.isAuthenticated());
		subject.checkRoles("admin");
		subject.checkPermission("user:delete");
		subject.logout();
		
		System.out.println("is Authentication "+subject.isAuthenticated());
		
	}
}
