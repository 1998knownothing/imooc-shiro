package com.imooc.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {


    //自带的Realm
    SimpleAccountRealm simpleAccountRealm=new SimpleAccountRealm();

    @Before
    public void addUer() {
        simpleAccountRealm.addAccount("Mark", "123","admin","user");
    }



    @Test
    public void testAuthentication() {
        IniRealm iniRealm=new IniRealm("classpath:user.ini");
        //.构建securityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject=SecurityUtils.getSubject();

        UsernamePasswordToken token=new UsernamePasswordToken("Mark","123");

        subject.login(token);

        System.out.println("is Authentication "+subject.isAuthenticated());
        //subject.checkRoles("admin1");

        subject.logout();

        System.out.println("is Authentication "+subject.isAuthenticated());

    }

}
