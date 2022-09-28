package com.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

public class TestUtils {

    public static Subject getSubject(){
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

        return subject;
    }
}
