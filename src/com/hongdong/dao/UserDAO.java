package com.hongdong.dao;

import java.util.List;

import com.hongdong.bean.User;
/**
 * �û�DAO��
 * @author zhangxx
 *
 */
public interface UserDAO {

	void regist(User user) throws Exception;//ע��

	User login(User user) throws Exception;//�û���¼
	
	User loginForOpenId(User user) throws Exception;//�û���¼ͨ��openId
	
	void updateUserPwd(User user) throws Exception;//�޸��û�����
	
	void updateUserInfo(User user) throws Exception;//�޸��û����ϲ���ͼ��
	
	void updateUserInfoImage(User user) throws Exception;//�޸��û����ϴ�ͼ��
	
	void updateUserIcon(User user) throws Exception;//�޸��û�ͼ��
	
	User getUserInfoId(int id) throws Exception;//�����û�id��ѯ��Ϣ
	
	User queryUserForName(String name) throws Exception;//�����û�����ѯ�Ƿ���ڸ��û�
	
	User queryUserForOpenId(String openid) throws Exception;//����openId��ѯ�Ƿ���ڸ��û�
	
	List<User> queryAllUsers() throws Exception;//��ѯ�����û�
}
