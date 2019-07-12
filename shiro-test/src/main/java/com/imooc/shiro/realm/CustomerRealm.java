package com.imooc.shiro.realm;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class CustomerRealm extends AuthorizingRealm {

	Map <String,String> userMap=new HashMap<String,String>();
	
	{
		
		//md5加密
		//userMap.put("Mark", "202cb962ac59075b964b07152d234b70");
		//md5加盐加密
		userMap.put("Mark", "0acf03f408f90ea0dcba786d300620db");
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName=(String)token.getPrincipal();
		
		String password=getPasswordByUserName(userName);
		if(password==null) {
			return null;
		}
		SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(userName,password,"customerRealm");
		//设置盐值12
		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("12"));
		return authenticationInfo;
	}
//模拟数据库
	private String getPasswordByUserName(String userName) {
		// TODO Auto-generated method stub
		return userMap.get(userName);
	}
/*
	public static void main(String[] args) {
		Md5Hash md5=new Md5Hash("123","12");
		System.out.println(md5.toString());
	}
*/

}
