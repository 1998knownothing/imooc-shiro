package com.imooc.shiro.realm;

import com.imooc.dao.UserDao;
import com.imooc.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;

public class CustomerRealm extends AuthorizingRealm {

	@Resource
	private UserDao userDao;
/*	Map <String,String> userMap=new HashMap<String,String>();
	
	{
		
		//md5加密
		//userMap.put("Mark", "202cb962ac59075b964b07152d234b70");
		//md5加盐加密
		userMap.put("Mark", "0acf03f408f90ea0dcba786d300620db");
	}*/
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String username = (String) principalCollection.getPrimaryPrincipal();
//        从数据库或者缓存中获取角色信息
		Set<String> roles = getRolesByUsername(username);
		Set<String> permissions = getPermissionsByUsername(username);


		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setStringPermissions(permissions);
		simpleAuthorizationInfo.setRoles(roles);

		return simpleAuthorizationInfo;
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
		User user=userDao.getUserByUserName(userName);
		if(user!=null){
			return user.getPassword();
		}

		return null;
		//return userMap.get(userName);
	}

	private Set<String> getRolesByUsername(String username) {
		System.out.println("从数据库中获取授权信息");
		List<String> list = userDao.getRolesByUsername(username);
		Set<String> roles = new HashSet<String>(list);
		return roles;
	}


	private Set<String> getPermissionsByUsername(String username) {
		List<String> list = userDao.getPermissionsByUsername(username);
		Set<String> permissions = new HashSet<String>(list);
		return permissions;
	}

/*
	public static void main(String[] args) {
		Md5Hash md5=new Md5Hash("123","12");
		System.out.println(md5.toString());
	}
*/

}
