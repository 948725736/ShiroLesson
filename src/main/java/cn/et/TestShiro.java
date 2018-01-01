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
	//��ini�ж�ȡȨ����Ϣ ����SecurityManager����
	Factory<org.apache.shiro.mgt.SecurityManager> factory=new IniSecurityManagerFactory("classpath : my.ini");
	org.apache.shiro.mgt.SecurityManager sercurityManager=factory.getInstance();
	SecurityUtils.setSecurityManager(sercurityManager);
	
	
	
	//��ȡ��ǰ���û�
	Subject  currentUser=SecurityUtils.getSubject();
	//��ǰ�û��ĻỰ
	Session session=currentUser.getSession();
	//�ж��Ƿ��¼  δ��¼��ʱ��Ż��¼
	if(!currentUser.isAuthenticated()){
		UsernamePasswordToken token =new UsernamePasswordToken("xht", "123456");
		token.setRememberMe(true);
		try {
			currentUser.login(token);  
			System.out.println("��¼�ɹ�");
			System.out.println(currentUser.isAuthenticated());
			System.out.println(currentUser.isRemembered());
			//����¼����û��Ƿ�ӵ��ĳ����ɫ
			if(currentUser.hasRole("role1")){
				System.out.println("�Ƿ�ӵ��role1��ɫ");
			}
			if(currentUser.isPermitted("user:delete:2")){
				System.out.println("ӵ�в�ѯ1�ŵĹ���");
			}
		} catch (UnknownAccountException uae) {
			System.out.println("�˺Ŵ���");
		}catch (IncompleteAnnotationException ice) {
			System.out.println("���벻ƥ��");
		}catch(LockedAccountException lae){
			System.out.println("�˺ű�����");
		}catch (AuthenticationException ae) {
			System.out.println("λ���쳣");
		}

	}
	}
}

