package com.lu.j1993.shiro;

import com.lu.j1993.entity.SysMenu;
import com.lu.j1993.entity.SysRole;
import com.lu.j1993.entity.SysUser;
import com.lu.j1993.repository.UserRepository;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2019/8/3.
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private UserRepository userRepository;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser userInfo =(SysUser) principalCollection.getPrimaryPrincipal();
        for(SysRole role:userInfo.getRoleList()){
            authorizationInfo.addRole(role.getRoleName());
            for(SysMenu menu:role.getMenuList()){
                authorizationInfo.addStringPermission(menu.getMenuName());

            }

        }
        return authorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取当前用户的用户名
        String username =(String) authenticationToken.getPrincipal();
        //根据用户名找到对象
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUser byUserName = userRepository.findByUserName(username);
        SimpleAuthenticationInfo authenticationInfo = null;
        if(byUserName!=null){
            authenticationInfo = new SimpleAuthenticationInfo(username, byUserName.getPassWord(), null, this.getName());

        }
        return authenticationInfo;
    }
}
