package cn.et;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.lang.annotation.IncompleteAnnotationException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.Session;

public class TestShiro {
public static void main(String[] args) {
	//从ini中读取权限信息 构建SecurityManager对象
	Factory<org.apache.shiro.mgt.SecurityManager> factory=new IniSecurityManagerFactory("classpath : my.ini");
	org.apache.shiro.mgt.SecurityManager sercurityManager=factory.getInstance();
	SecurityUtils.setSecurityManager(sercurityManager);
	
	
	
	//获取当前的用户
	Subject  currentUser=SecurityUtils.getSubject();
	//当前用户的会话
	Session session=currentUser.getSession();
	//判断是否登录  未登录的时候才会登录
	if(!currentUser.isAuthenticated()){
		UsernamePasswordToken token =new UsernamePasswordToken("xht", "123456");
		token.setRememberMe(true);
		try {
			currentUser.login(token);  
			System.out.println("登录成功");
			System.out.println(currentUser.isAuthenticated());
			System.out.println(currentUser.isRemembered());
			//检查登录后的用户是否拥有某个角色
			if(currentUser.hasRole("role1")){
				System.out.println("是否拥有role1角色");
			}
			if(currentUser.isPermitted("user:delete:2")){
				System.out.println("拥有查询1号的功能");
			}
		} catch (UnknownAccountException uae) {
			System.out.println("账号错误");
		}catch (IncompleteAnnotationException ice) {
			System.out.println("密码不匹配");
		}catch(LockedAccountException lae){
			System.out.println("账号被锁定");
		}catch (AuthenticationException ae) {
			System.out.println("位置异常");
		}

	}
	}
}

