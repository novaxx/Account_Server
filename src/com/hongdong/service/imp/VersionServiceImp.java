package com.hongdong.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hongdong.bean.AppVersion;
import com.hongdong.dao.VersionDAO;
import com.hongdong.service.VersionService;

/**
 * °æ±¾¿ØÖÆ
 * @author zhangxx
 *
 */
@Service
public class VersionServiceImp implements VersionService {

	@Autowired
	private VersionDAO versionDao;

	@Override
	public AppVersion queryVersionById(int id) {
		// TODO Auto-generated method stub
		AppVersion v=versionDao.queryVersionById(id);
		return v;
	}
	
}
