package com.hongdong.service;
import java.util.List;

import com.hongdong.bean.User;
/**
 * baseUserService
 * @author zhangxx
 *
 */
public interface UserService {
	void userRegist(User user) throws Exception;//
	
	User userLogin(User user) throws Exception;
	
	User userLoginForOpenId(User user) throws Exception;
	
	void updateUserPwd(User user) throws Exception;//修改用户密码
	
	void updateUserInfo(User user) throws Exception;//修改用户资料
	
	void updateUserIcon(User user) throws Exception;//修改用户图像
	
	void updateUserInfoImage(User user) throws Exception;//修改用户资料带图像
	
	User getUserInfoId(int id) throws Exception;//根据用户id查询用户信息
	
	User queryUserForName(String name) throws Exception;//根据用户名查询该用户是否存在
	
	User queryUserForOpenId(String openid) throws Exception;//根据openid查询该用户是否存在

	List<User> queryAllUsers() throws Exception;//查询所有用户
}
