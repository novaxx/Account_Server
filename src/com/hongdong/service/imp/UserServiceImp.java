package com.hongdong.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hongdong.bean.User;
import com.hongdong.dao.UserDAO;
import com.hongdong.service.UserService;

/**
 * 用户service 实现类
 * 
 * @author zhangxx
 *
 */
@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserDAO userDao;

	@Override
	public void userRegist(User user) throws Exception {
		userDao.regist(user);

	}

	@Override
	public User userLogin(User user) throws Exception {
		User userInfo = userDao.login(user);
		return userInfo;
	}

	@Override
	public User userLoginForOpenId(User user) throws Exception {
		User userInfo = userDao.loginForOpenId(user);
		return userInfo;
	}
	
	@Override
	public void updateUserPwd(User user) throws Exception {
		userDao.updateUserPwd(user);
	}

	@Override
	public User getUserInfoId(int id) throws Exception {
		User user = userDao.getUserInfoId(id);
		return user;
	}

	@Override
	public User queryUserForName(String name) throws Exception {
		User user = userDao.queryUserForName(name);
		return user;
	}
	
	@Override
	public User queryUserForOpenId(String openid) throws Exception {
		User user = userDao.queryUserForOpenId(openid);
		return user;
	}

	@Override
	public void updateUserInfo(User user) throws Exception {
		userDao.updateUserInfo(user);
	}

	@Override
	public void updateUserIcon(User user) throws Exception {
		userDao.updateUserIcon(user);
	}

	@Override
	public void updateUserInfoImage(User user) throws Exception {
		userDao.updateUserInfoImage(user);
	}

	@Override
	public List<User> queryAllUsers() throws Exception {
		return userDao.queryAllUsers();
	}

}
