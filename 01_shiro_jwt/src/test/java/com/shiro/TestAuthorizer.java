package com.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * 授权
 */
public class TestAuthorizer {


    @Test
    public void test(){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("wang","123");
        subject.login(token);
        Assert.assertTrue(subject.isPermitted("user:view"));
        Assert.assertFalse(subject.isPermitted("user:update"));
        Assert.assertFalse(subject.hasRoles(Arrays.asList("role1","role2"))[0]);
        subject.checkRole("role1");
    }
}
