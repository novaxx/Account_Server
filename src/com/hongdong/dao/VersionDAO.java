package com.hongdong.dao;

import com.hongdong.bean.AppVersion;

/**
 * �汾����
 * @author zhangxx
 *
 */
public interface VersionDAO {

	/*����id ��ѯ�ö���*/
	public AppVersion queryVersionById(int id);
}
