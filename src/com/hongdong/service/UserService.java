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
	
	void updateUserPwd(User user) throws Exception;//�޸��û�����
	
	void updateUserInfo(User user) throws Exception;//�޸��û�����
	
	void updateUserIcon(User user) throws Exception;//�޸��û�ͼ��
	
	void updateUserInfoImage(User user) throws Exception;//�޸��û����ϴ�ͼ��
	
	User getUserInfoId(int id) throws Exception;//�����û�id��ѯ�û���Ϣ
	
	User queryUserForName(String name) throws Exception;//�����û�����ѯ���û��Ƿ����
	
	User queryUserForOpenId(String openid) throws Exception;//����openid��ѯ���û��Ƿ����

	List<User> queryAllUsers() throws Exception;//��ѯ�����û�
}
