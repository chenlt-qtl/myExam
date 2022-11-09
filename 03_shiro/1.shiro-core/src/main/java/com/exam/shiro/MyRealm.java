package com.exam.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.HashSet;
import java.util.Set;


public class MyRealm extends AuthorizingRealm {


    /**
     *
     * 2.授权 doGetAuthorizationInfo
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //1.获取用户名，去数据库查数据
        String userName = principalCollection.getPrimaryPrincipal().toString();
        System.out.println("userName:"+userName);

        //2.授权角色
        Set<String> roles = new HashSet<>(2);
        roles.add("super");
        roles.add("manager");

        info.setRoles(roles);

        //3.授权权限
        Set<String> permissions = new HashSet<>(2);
        permissions.add("user:view");
        permissions.add("user:update");

        info.setStringPermissions(permissions);
        System.out.println("授权成功");
        return info;
    }

    /**
     *
     *  1.登陆认证 doGetAuthenticationInfo
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.获取登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());

        //2.根据username去数据库获取用户数据
        User user = null;
        if(username.equals("admin")){
            user = new User("admin","123456",1);
        }else  if(username.equals("admin1")){
            user = new User("admin1","123456",0);
        } if(username.equals("admin2")){
            user = new User("admin2","123456",2);
        }

        //3.对比凭证
        if(user == null){
            throw new UnknownAccountException("用户不存在");
        }
        if(!user.getPassword().equals(password)){
            throw new CredentialsException("密码错误");
        }
        if(user.getStatus()==0){
            throw new DisabledAccountException("用户已停用");
        }
        if(user.getStatus()==2){
            throw new LockedAccountException("用户已锁定");
        }


        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password,getName());
        System.out.println("认证成功");
        return info;
    }
}
