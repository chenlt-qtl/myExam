package com.shiro;

import com.exam.shiro.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestCache {

    @Test
    public void test(){
        //1. 创建securityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        //2. 使用SecurityUtils绑定
        SecurityUtils.setSecurityManager(securityManager);


        //3.设置缓存
        CacheManager cacheManager = new EhCacheManager();
        securityManager.setCacheManager(cacheManager);

        //4. 设置realm
        Realm realm = new MyRealm();
        securityManager.setRealm(realm);

        //5. 获取主体对象
        Subject subject = SecurityUtils.getSubject();

        //6. 创建凭证
        UsernamePasswordToken token = new UsernamePasswordToken("admin","123456");

        //7. 登录
        subject.login(token);
        System.out.println(subject.hasRole("admin"));
        System.out.println(subject.hasRole("super"));
        System.out.println(subject.isPermitted("user:view"));
        System.out.println(subject.isPermitted("user:delete"));

        subject.logout();
    }
}
