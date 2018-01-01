package cn.et.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Select;

import cn.et.entity.UserInfo;
public interface UserMapper {
	@Select("select user_name as userName,pass_word as password from user_info where user_name=#{0}")
	public UserInfo queryUser(String userName);
	@Select("SELECT r.role_name FROM user_info u "
			+ "INNER JOIN user_role_relation urr ON u.user_id=urr.user_id"
			+ " INNER JOIN role r ON urr.role_id=r.role_id    "
			+ "  WHERE user_name=#{0}")
	public Set<String> querRoleByName(String userName);
	
	@Select("SELECT p.perm_tag FROM user_info u INNER JOIN user_role_relation urr ON u.user_id=urr.user_id "+
                     "    INNER JOIN role r ON urr.role_id=r.role_id  "+
                     "    INNER JOIN role_perms_relation rpr ON r.role_id=rpr.role_id   "+
                     "    INNER JOIN perms p ON rpr.perm_id=p.perm_id "+
					"WHERE user_name=#{0}")
	public Set<String> queryPermsByName(String userName);
	
	
}
