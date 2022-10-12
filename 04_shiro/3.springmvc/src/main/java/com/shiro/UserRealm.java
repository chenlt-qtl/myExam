package com.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.debug("doGetAuthorizationInfo 授权");

        JwtUser user = (JwtUser) SecurityUtils.getSubject().getPrincipal();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(user.getRoles());//roles跟着user走，放到token里。

        //获取权限
        Set<String> stringPermissions = new HashSet<>();
        stringPermissions.add("addUser");

        simpleAuthorizationInfo.addStringPermissions(stringPermissions);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        User userFromDB = new User();
        userFromDB.setUsername("super");
        userFromDB.setPassword("123456");
        userFromDB.setRoles(Arrays.asList(new String[]{"admin"}));

        String passwordFromDB = userFromDB.getPassword();

        //在使用jwt访问时，shiro中能拿到的用户信息只能是token中携带的jwtUser，所以此处保持统一。
        JwtUser jwtUser = new JwtUser(userFromDB.getUsername(), userFromDB.getRoles());
        SimpleAuthenticationInfo res = new SimpleAuthenticationInfo(jwtUser, passwordFromDB, getName());
        return res;
    }
}
