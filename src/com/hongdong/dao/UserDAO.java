package com.hongdong.dao;

import java.util.List;

import com.hongdong.bean.User;
/**
 * 用户DAO层
 * @author zhangxx
 *
 */
public interface UserDAO {

	void regist(User user) throws Exception;//注册

	User login(User user) throws Exception;//用户登录
	
	User loginForOpenId(User user) throws Exception;//用户登录通过openId
	
	void updateUserPwd(User user) throws Exception;//修改用户密码
	
	void updateUserInfo(User user) throws Exception;//修改用户资料不带图像
	
	void updateUserInfoImage(User user) throws Exception;//修改用户资料带图像
	
	void updateUserIcon(User user) throws Exception;//修噶用户图像
	
	User getUserInfoId(int id) throws Exception;//根据用户id查询信息
	
	User queryUserForName(String name) throws Exception;//根据用户名查询是否存在该用户
	
	User queryUserForOpenId(String openid) throws Exception;//根据openId查询是否存在该用户
	
	List<User> queryAllUsers() throws Exception;//查询所有用户
}
