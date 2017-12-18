package com.hongdong.service;

import com.hongdong.bean.AppVersion;

/**
 * 版本控制
 * @author zhangxx
 *
 */
public interface VersionService {

	/*根据id查询该对象*/
	public AppVersion queryVersionById(int id);
}
