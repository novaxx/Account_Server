package com.hongdong.dao;

import com.hongdong.bean.AppVersion;

/**
 * 版本控制
 * @author zhangxx
 *
 */
public interface VersionDAO {

	/*根据id 查询该对象*/
	public AppVersion queryVersionById(int id);
}
