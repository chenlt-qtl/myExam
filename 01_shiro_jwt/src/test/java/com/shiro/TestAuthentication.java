package com.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

/**
 * 身份验证
 */
public class TestAuthentication {

    @Test
    public void test(){
        //1. 创建securityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        //2. 使用SecurityUtils绑定
        SecurityUtils.setSecurityManager(securityManager);

        //3. 设置realm
        Realm realm = new IniRealm("classpath:shiro.ini");
        securityManager.setRealm(realm);

        //4. 获取主体对象
        Subject subject = SecurityUtils.getSubject();

        //5. 创建凭证
        UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");

        //6. 登录
        subject.login(token);

        //7. 验证
        Assert.assertTrue(subject.isAuthenticated());

    }
}
