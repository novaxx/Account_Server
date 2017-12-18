package com.hongdong.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hongdong.bean.AppVersion;
import com.hongdong.service.VersionService;
import com.hongdong.service.imp.VersionServiceImp;
import com.hongdong.util.ResultCode;

/**
 * �汾������
 * @author zhangxx
 *
 */

@Controller
@RequestMapping("/version")
public class VersionController {

	@Autowired
	VersionService versionService;
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> updateVersion(HttpServletRequest request){
		Map<String,Object> result=new HashMap<String, Object>();
		
		int id=Integer.parseInt(request.getParameter("id"));
		int vCode=Integer.parseInt(request.getParameter("vcode"));
		
		AppVersion appBean=versionService.queryVersionById(id);
		if(appBean!=null){
			
			if(vCode>appBean.getVcode()){
				result.put("respcode", "0");
				result.put("message", "���°汾");
				result.put("data", appBean);
				result.put("errorcode", "");
			}else{
				result.put("code", "0");
				result.put("msg", "�Ѿ������°汾");
				result.put("data", "");
			}
			
		}else{
			result.put("code", "1");
			result.put("msg", "�汾��Ϣ��ѯʧ��");
			result.put("data", "");
		}
		return result;
		
	}
}
