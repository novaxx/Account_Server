package com.hongdong.controller;

import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hongdong.bean.UserResult;
import com.hongdong.bean.User;
import com.hongdong.service.UserService;
import com.hongdong.util.Constants;
import com.hongdong.util.InfoMation;
import com.hongdong.util.ResultCode;
import com.hongdong.util.StringSplit;

/**
 * �û�����������
 * 
 * @author zhangxx
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("update")
	public String test() {
		return "file";
	}

	/**
	 * �û�ע��
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "userRegistForPhone")
	@ResponseBody
	// ����json
	public Map<String, Object> userRegistForPhone(HttpServletRequest request)
			throws Exception {
		User user = new User();
		Map<String, Object> result = new HashMap<String, Object>();

		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		System.out.println("ע��ӿ�/���յ��û����ݲ���" + phone + "����" + password + "�ǳ�:"
				+ name);
		User u = userService.queryUserForName(phone);
		if (u != null) {
			result.put("data", ResultCode.SUCCESS);
			result.put("respcode", ResultCode.SUCCESS);
			result.put("errorcode", ResultCode.USEREXIST);
			result.put("message", "ע��ʧ��,�û��Ѿ�����!");
		} else {
			user.setPhone(phone);
			user.setPassword(password);
			user.setName(name);
			// ���ݿ����
			try {
				userService.userRegist(user);
				result.put("data", ResultCode.SUCCESS);
				result.put("respcode", ResultCode.SUCCESS);
				result.put("errorcode", "");
				result.put("message", "ע��ɹ�!");
			} catch (Exception e) {
				e.printStackTrace();
				result.put("data", "");
				result.put("respcode", ResultCode.FAIL);
				result.put("errorcode", ResultCode.FAIL);
				result.put("message", "ע��ʧ��!");
			}
		}
		return result;
	}

	/**
	 * �û�ע��ͨ��OpenId
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "userRegistForOpenId")
	@ResponseBody
	// ����json
	public Map<String, Object> userRegistForOpenId(HttpServletRequest request)
			throws Exception {
		User user = new User();
		Map<String, Object> result = new HashMap<String, Object>();

		String openId = request.getParameter("openid");
		String name = request.getParameter("name");
		int sex = Integer.valueOf(request.getParameter("sex"));
		String image = request.getParameter("head");
		System.out.println("ע��ӿ�/���յ��û����ݲ���" + openId + "�ǳ�:"
				+ name);
		User u = userService.queryUserForOpenId(openId);
		if (u != null) {
			System.out.println("ע��ӿ�/���յ��û����ݲ���" + openId + "�û�����!!");
			result.put("data", ResultCode.SUCCESS);
			result.put("respcode", ResultCode.SUCCESS);
			result.put("errorcode", ResultCode.USEREXIST);
			result.put("message", "ע��ʧ��,�û��Ѿ�����!");
		} else {
			user.setOpenid(openId);
			user.setName(name);
			user.setSex(sex);
			user.setImage(image);
			user.setPhone("");
			user.setPassword("0");
			// ���ݿ����
			try {
				userService.userRegist(user);
				result.put("data", ResultCode.SUCCESS);
				result.put("respcode", ResultCode.SUCCESS);
				result.put("errorcode", "");
				result.put("message", "ע��ɹ�!");
				System.out.println("ע��ӿ�/���յ��û����ݲ���" + openId + "ע��ɹ�");
			} catch (Exception e) {
				e.printStackTrace();
				result.put("data", "");
				result.put("respcode", ResultCode.FAIL);
				result.put("errorcode", ResultCode.FAIL);
				result.put("message", "ע��ʧ��!");
				System.out.println("ע��ӿ�/���յ��û����ݲ���" + openId + "ע��ʧ�� " + e.toString());
			}
		}
		return result;
	}

	
	/**
	 * �û���¼ͨ��OpenId
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "userLoginForOpenId")
	@ResponseBody
	public Map<String, Object> userLoginForOpenId(HttpServletRequest request)
			throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		User user = new User();

		String openId = request.getParameter("openid");
		user.setOpenid(openId);

		User u = userService.queryUserForOpenId(openId);
		if (u != null) {
			User users = userService.userLoginForOpenId(user);
			if (users != null) {
				UserResult userResult = new UserResult();
				userResult.setId(users.getId());
				userResult.setOpenId(user.getOpenid());
				userResult.setHead(users.getImage());
				userResult.setName(users.getName());
				userResult.setVip(users.getVip());
				userResult.setLv(users.getLevel());
				userResult.setGold(users.getGold());
				userResult.setCrys(users.getCrystal());
				userResult.setSex(users.getSex());
				result.put("data", userResult);
				result.put("respcode", ResultCode.SUCCESS);
				result.put("errorcode", "");
				result.put("message", "��½�ɹ�!");
				System.out.println("ͨ��openId��¼:" + openId + "���ɹ�");
			} else {
				result.put("data", "");
				result.put("respcode", ResultCode.FAIL);
				result.put("errorcode", ResultCode.FAIL);
				result.put("message", "��½ʧ��!");
				System.out.println("ͨ��openId��¼:" + openId + "��ʧ��");
			}
		} else {
			System.out.println("ͨ��openId��¼:" + openId + "����ע��");
			// ΢��openId��ע��
			result = userRegistForOpenId(request);
			User users = userService.userLoginForOpenId(user);
			UserResult userResult = new UserResult();
			userResult.setId(users.getId());
			userResult.setOpenId(user.getOpenid());
			userResult.setHead(users.getImage());
			userResult.setName(users.getName());
			userResult.setVip(users.getVip());
			userResult.setLv(users.getLevel());
			userResult.setGold(users.getGold());
			userResult.setCrys(users.getCrystal());
			userResult.setSex(users.getSex());
			result.put("data", userResult);
			result.put("respcode", ResultCode.SUCCESS);
			result.put("errorcode", "");
		}

		return result;

	}
	
	/**
	 * �û���¼ͨ���ֻ���
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "userLoginForPhone")
	@ResponseBody
	public Map<String, Object> userLogin(HttpServletRequest request)
			throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		User user = new User();

		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		user.setPhone(phone);
		user.setPassword(password);

		User u = userService.queryUserForName(phone);
		if (u != null) {

			User users = userService.userLogin(user);
			if (users != null) {
				UserResult userResult = new UserResult();
				userResult.setId(users.getId());
				userResult.setHead(users.getImage());
				userResult.setName(users.getName());
				userResult.setVip(users.getVip());
				userResult.setLv(users.getLevel());
				userResult.setGold(users.getGold());
				userResult.setCrys(users.getCrystal());
				userResult.setSex(users.getSex());
				result.put("data", userResult);
				result.put("respcode", ResultCode.SUCCESS);
				result.put("errorcode", "");
				result.put("message", "��½�ɹ�!");
			} else {
				result.put("data", "");
				result.put("respcode", ResultCode.FAIL);
				result.put("errorcode", ResultCode.FAIL);
				result.put("message", "��½ʧ��!");
			}

		} else {
			result.put("data", "");
			result.put("respcode", ResultCode.USEREXIST);
			result.put("errorcode", "");
			result.put("message", "��½ʧ��!");
		}

		return result;

	}

	/**
	 * �޸��û�����
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateUserPwd")
	@ResponseBody
	public Map<String, Object> updateUserPwd(HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		InfoMation infoMaction = new InfoMation();
		int id = Integer.valueOf(request.getParameter("id"));
		String userOldPwd = request.getParameter("oldpassword");
		String userNewPwd = request.getParameter("newpassword");
		
		
			User user = userService.getUserInfoId(id);
			if (user != null) {
				if (userOldPwd.equals(user.getPassword())) {
					user.setPassword(userNewPwd);
					userService.updateUserPwd(user);
					result.put("message", "�޸�����ɹ�!");
					result.put("respcode", ResultCode.SUCCESS);
					result.put("data", ResultCode.SUCCESS);
					result.put("errorcode", "");
				} else {
					result.put("message", "���������");
					result.put("respcode", ResultCode.FAIL);
					result.put("data", "");
					result.put("errorcode", ResultCode.OLDPWDFAIL);
				}
			} else {
				result.put("message", "�û�������");
				result.put("respcode", ResultCode.FAIL);
				result.put("data", "");
				result.put("errorcode", ResultCode.UNUSEREXIST);
			}
		
		return result;

	}

	/**
	 * �޸��û�ͼ��
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="updateUserIcon")
	@ResponseBody
	public Map<String, Object> uploadUserIcon(HttpServletRequest request,
			@RequestParam("file") MultipartFile file) throws Exception {
		Map<String,Object> result=new HashMap<String, Object>();
		User user=new User();
		int id = Integer.valueOf(request.getParameter("id"));
		System.out.println("�޸�ͼ�񴫵ݵ�id:"+id);
		String realPath = request.getSession().getServletContext()
				.getRealPath("userIcon/");
		
		String fileName = file.getOriginalFilename();
		System.out.println("�ϴ����ļ���");
		File path=new File(realPath);
		if(!path.exists()){
			path.mkdirs();
		}
		
		if(!fileName.contains(".jpg") || !fileName.contains(".png")){
			
			
			User u=userService.getUserInfoId(id);
			if(u.getImage()!=null){
				File userIcon=new File(realPath+"/"+StringSplit.userImage(u.getImage()));
				if(userIcon.exists()){
					userIcon.delete();
				}
			}
			
			File destFile = new File(realPath, fileName);
			if (!destFile.exists()) {
				destFile.mkdirs();
			}
			file.transferTo(destFile);
			String userIcon = "/userIcon/" + fileName;
			System.out.println("�û�ͼ�����ڵ�ַ:" + realPath+"/"+fileName);
			user.setImage(userIcon);
			user.setId(id);
			userService.updateUserIcon(user);
			
			result.put("data", "0");
			result.put("errorcode", "");
			result.put("message", "�ϴ��û�ͼ��ɹ�");
			result.put("respcode", ResultCode.SUCCESS);
		}else{
			result.put("data", "");
			result.put("errorcode", ResultCode.VINT);
			result.put("message", "�ļ����Ϸ�");
			result.put("respcode", ResultCode.FAIL);
		}
		
		return result;

	}

	/**
	 * �޸��û�����
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "uploadUserInfo")
	@ResponseBody
	public Map<String, Object> uploadUserInfo(HttpServletRequest request,
			@RequestParam("file") MultipartFile file) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String realPath = request.getSession().getServletContext()
				.getRealPath("userIcon/");
		User user = new User();

		int id = Integer.valueOf(request.getParameter("id"));
		String name = request.getParameter("name");
		String city = request.getParameter("city");
		String age = request.getParameter("age");
		String sex = request.getParameter("sex");
		int ages = 0;
		int sexs = 0;
		if (age != null || sex != null) {
			ages = Integer.valueOf(age);
			sexs = Integer.valueOf(sex);
		}

		String address = request.getParameter("address");
		String fileName = file.getOriginalFilename();

		System.out.println("�����û����ϴ��ݹ����Ĳ�����" + "�ǳ�:" + name + "����" + city
				+ "����:" + age + "�Ա�" + sex + "��ַ:" + address + "\n"
				+ "�û�ͼ���ŵĵ�ַ:" + realPath);

		File path = new File(realPath);
		if (!path.exists()) {
			path.mkdirs();
		}
		System.out.println("ͼ���ļ���:" + fileName);
		if (!fileName.isEmpty()) {
			// ��ͼ����޸�
			if (fileName.contains(".jpg") || fileName.contains(".png")) {

				File destFile = new File(realPath, fileName);
				if (!destFile.exists()) {
					destFile.mkdirs();
				}
				file.transferTo(destFile);
				String userIcon = "/userIcon/" + fileName;
				System.out.println("�û�ͼ�����ڵ�ַ:" + userIcon);

				user.setId(id);
				user.setName(name);
				user.setImage(userIcon);
				user.setCity(city);
				user.setSex(sexs);
				user.setAge(ages);
				user.setAddress(address);
				userService.updateUserInfoImage(user);

				result.put("message", "�޸��û����ϳɹ�");
				result.put("respcode", ResultCode.SUCCESS);
				result.put("data", "0");
				result.put("errorcode", ResultCode.SUCCESS);

			} else {
				// �Ƿ��ļ�
				result.put("message", "�Ƿ��ļ�");
				result.put("respcode", ResultCode.FAIL);
				result.put("data", "");
				result.put("errorcode", ResultCode.FILENOLEGAL);
			}
		} else {
			// û��ͼ����޸�
			user.setId(id);
			user.setName(name);
			user.setCity(city);
			user.setSex(sexs);
			user.setAge(ages);
			user.setAddress(address);
			userService.updateUserInfo(user);

			result.put("message", "�޸��û����ϳɹ�");
			result.put("respcode", ResultCode.SUCCESS);
			result.put("data", "0");
			result.put("errorcode", ResultCode.SUCCESS);
		}

		return result;

	}

	/**
	 * �����û�id��ȡ�û���ϸ��Ϣ 
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getUserInfoId")
	@ResponseBody
	public Map<String, Object> getUserInfo(HttpServletRequest request)
			throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		int id = Integer.parseInt(request.getParameter("id"));

		System.out.println("�û�id:" + id);
			UserResult userResult = new UserResult();
			
				User user = userService.getUserInfoId(id);
				if (user != null) {
					userResult.setId(user.getId());
					userResult.setName(user.getName());
					userResult.setHead(user.getImage());
					userResult.setVip(user.getVip());
					userResult.setLv(user.getLevel());
					userResult.setGold(user.getGold());
					userResult.setCrys(user.getCrystal());
					userResult.setSex(user.getSex());

					result.put("message", "��ѯ�ɹ�");
					result.put("respcode", ResultCode.SUCCESS);
					result.put("data", userResult);
					result.put("errorcode", "");
				} else {
					// û�в�ѯ�����û�
					result.put("message", "�û�������");
					result.put("respcode", ResultCode.FAIL);
					result.put("data", "");
					result.put("errorcode", ResultCode.UNUSEREXIST);
				}


		return result;

	}

    /**
     * ��ȡAll User	
     */
	@RequestMapping("allUsers")
	@ResponseBody
	public Map<String, Object> queryAllUsers(HttpServletRequest request) {
		Map<String, Object> returnData = new HashMap<String, Object>();
		
		try {
			List<User> users = userService.queryAllUsers();
			returnData.put("respcode", ResultCode.SUCCESS);
			returnData.put("message", "��ѯ�����û��ɹ���");
			returnData.put("data", users);
		} catch (Exception e) {
			returnData.put("respcode", ResultCode.FAIL);
			returnData.put("msg", "��ѯ�����û�ʧ�ܣ�");
			returnData.put("data", "");
	}
		
		return returnData;
	}
}
