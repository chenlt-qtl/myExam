package com.shiro;

import com.exam.shiro.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 自定义realm
 * 作用：
 * 1.登陆认证 doGetAuthenticationInfo
 * 2.授权 doGetAuthorizationInfo
 */
public class TestRealm {

    private static Subject subject;

    @BeforeClass
    public static void before() {
        //1. 创建securityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        //2. 使用SecurityUtils绑定
        SecurityUtils.setSecurityManager(securityManager);

        //3. 设置realm
        MyRealm myRealm = new MyRealm();
        securityManager.setRealm(myRealm);

        //4. 获取主体对象
        subject = SecurityUtils.getSubject();
    }


    @Test
    public void test() {

        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            Assert.assertEquals("用户不存在", e.getMessage());
        }

        token = new UsernamePasswordToken("admin1", "123");

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            Assert.assertEquals("密码错误", e.getMessage());
        }

        token = new UsernamePasswordToken("admin1", "123456");

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            Assert.assertEquals("用户已停用", e.getMessage());
        }

        token = new UsernamePasswordToken("admin2", "123456");

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            Assert.assertEquals("用户已锁定", e.getMessage());
        }

        token = new UsernamePasswordToken("admin", "123456");
        subject.login(token);

        Assert.assertFalse(subject.hasRole("admin"));
        Assert.assertTrue(subject.hasRole("super"));
        Assert.assertTrue(subject.isPermitted("user:view"));
        Assert.assertFalse(subject.isPermitted("user:delete"));
    }

}

