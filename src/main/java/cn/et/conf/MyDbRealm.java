package cn.et.conf;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.et.dao.UserMapper;
import cn.et.entity.UserInfo;
@Component
public class MyDbRealm extends AuthorizingRealm{
	@Autowired
	UserMapper userMapper;
	/**
	 * 认证 
	 * 将登陆输入的用户名和密码和数据库中的用户名和密码对比 是否相等
	 * 返回值 null表示认证失败  非null认证通过
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//页面传入的token
		UsernamePasswordToken upt=(UsernamePasswordToken)token;
		UserInfo queryUser = userMapper.queryUser(token.getPrincipal().toString());
		if(queryUser!=null && queryUser.getPassword().equals(new String(upt.getPassword()))){
			System.out.println("成功");
			SimpleAccount sa=new SimpleAccount(upt.getUsername(),upt.getPassword(),"MyDbRealm");
			return sa;
		}
		System.out.println("失败");
		return null;
	}
	/**
	 * 获取当前用户的授权数据
	 * 将当前用户在数据库的角色和权限 加载到AuthorizationInfo
	 */
	@Override
	public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("获取权限信息");
		String userName=principals.getPrimaryPrincipal().toString();
		Set<String> roleList=userMapper.querRoleByName(userName);
		Set<String> permsList=userMapper.queryPermsByName(userName);
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();  
        authorizationInfo.setRoles(roleList);  
        authorizationInfo.setStringPermissions(permsList);  
        return authorizationInfo;  
	}

}
