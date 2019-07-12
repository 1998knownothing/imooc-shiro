package com.imooc.test;

import com.imooc.shiro.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;

import org.apache.shiro.subject.Subject;

import org.junit.Test;



public class CustomerRealmTest {


	
	@Test
	public void testAuthentication() {
		CustomerRealm customerRealm=new CustomerRealm();
		//.构建securityManager环境
		DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
		defaultSecurityManager.setRealm(customerRealm);
		//设置加密
		HashedCredentialsMatcher mather=new HashedCredentialsMatcher();
		mather.setHashAlgorithmName("md5");//设置加密算法
		mather.setHashIterations(1);//设置加密次数
		
		customerRealm.setCredentialsMatcher(mather);//给Realm设置加密
		
		
		
		
		//主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject=SecurityUtils.getSubject();
		
		UsernamePasswordToken token=new UsernamePasswordToken("Mark","123");
		
		subject.login(token);
		
		System.out.println("is Authentication "+subject.isAuthenticated());
		//subject.checkRoles("admin");
		//subject.checkPermission("user:delete");
		subject.logout();
		
		System.out.println("is Authentication "+subject.isAuthenticated());
		
	}
}
