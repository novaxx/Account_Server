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
 * 用户操作控制器
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
	 * 用户注册
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "userRegistForPhone")
	@ResponseBody
	// 返回json
	public Map<String, Object> userRegistForPhone(HttpServletRequest request)
			throws Exception {
		User user = new User();
		Map<String, Object> result = new HashMap<String, Object>();

		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		System.out.println("注册接口/接收到用户传递参数" + phone + "密码" + password + "昵称:"
				+ name);
		User u = userService.queryUserForName(phone);
		if (u != null) {
			result.put("data", ResultCode.SUCCESS);
			result.put("respcode", ResultCode.SUCCESS);
			result.put("errorcode", ResultCode.USEREXIST);
			result.put("message", "注册失败,用户已经存在!");
		} else {
			user.setPhone(phone);
			user.setPassword(password);
			user.setName(name);
			// 数据库操作
			try {
				userService.userRegist(user);
				result.put("data", ResultCode.SUCCESS);
				result.put("respcode", ResultCode.SUCCESS);
				result.put("errorcode", "");
				result.put("message", "注册成功!");
			} catch (Exception e) {
				e.printStackTrace();
				result.put("data", "");
				result.put("respcode", ResultCode.FAIL);
				result.put("errorcode", ResultCode.FAIL);
				result.put("message", "注册失败!");
			}
		}
		return result;
	}

	/**
	 * 用户注册通过OpenId
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "userRegistForOpenId")
	@ResponseBody
	// 返回json
	public Map<String, Object> userRegistForOpenId(HttpServletRequest request)
			throws Exception {
		User user = new User();
		Map<String, Object> result = new HashMap<String, Object>();

		String openId = request.getParameter("openid");
		String name = request.getParameter("name");
		int sex = Integer.valueOf(request.getParameter("sex"));
		String image = request.getParameter("head");
		System.out.println("注册接口/接收到用户传递参数" + openId + "昵称:"
				+ name);
		User u = userService.queryUserForOpenId(openId);
		if (u != null) {
			System.out.println("注册接口/接收到用户传递参数" + openId + "用户存在!!");
			result.put("data", ResultCode.SUCCESS);
			result.put("respcode", ResultCode.SUCCESS);
			result.put("errorcode", ResultCode.USEREXIST);
			result.put("message", "注册失败,用户已经存在!");
		} else {
			user.setOpenid(openId);
			user.setName(name);
			user.setSex(sex);
			user.setImage(image);
			user.setPhone("");
			user.setPassword("0");
			// 数据库操作
			try {
				userService.userRegist(user);
				result.put("data", ResultCode.SUCCESS);
				result.put("respcode", ResultCode.SUCCESS);
				result.put("errorcode", "");
				result.put("message", "注册成功!");
				System.out.println("注册接口/接收到用户传递参数" + openId + "注册成功");
			} catch (Exception e) {
				e.printStackTrace();
				result.put("data", "");
				result.put("respcode", ResultCode.FAIL);
				result.put("errorcode", ResultCode.FAIL);
				result.put("message", "注册失败!");
				System.out.println("注册接口/接收到用户传递参数" + openId + "注册失败 " + e.toString());
			}
		}
		return result;
	}

	
	/**
	 * 用户登录通过OpenId
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
				result.put("message", "登陆成功!");
				System.out.println("通过openId登录:" + openId + "，成功");
			} else {
				result.put("data", "");
				result.put("respcode", ResultCode.FAIL);
				result.put("errorcode", ResultCode.FAIL);
				result.put("message", "登陆失败!");
				System.out.println("通过openId登录:" + openId + "，失败");
			}
		} else {
			System.out.println("通过openId登录:" + openId + "，自注册");
			// 微信openId自注册
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
	 * 用户登录通过手机号
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
				result.put("message", "登陆成功!");
			} else {
				result.put("data", "");
				result.put("respcode", ResultCode.FAIL);
				result.put("errorcode", ResultCode.FAIL);
				result.put("message", "登陆失败!");
			}

		} else {
			result.put("data", "");
			result.put("respcode", ResultCode.USEREXIST);
			result.put("errorcode", "");
			result.put("message", "登陆失败!");
		}

		return result;

	}

	/**
	 * 修改用户密码
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
					result.put("message", "修改密码成功!");
					result.put("respcode", ResultCode.SUCCESS);
					result.put("data", ResultCode.SUCCESS);
					result.put("errorcode", "");
				} else {
					result.put("message", "旧密码错误");
					result.put("respcode", ResultCode.FAIL);
					result.put("data", "");
					result.put("errorcode", ResultCode.OLDPWDFAIL);
				}
			} else {
				result.put("message", "用户不存在");
				result.put("respcode", ResultCode.FAIL);
				result.put("data", "");
				result.put("errorcode", ResultCode.UNUSEREXIST);
			}
		
		return result;

	}

	/**
	 * 修改用户图像
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
		System.out.println("修改图像传递的id:"+id);
		String realPath = request.getSession().getServletContext()
				.getRealPath("userIcon/");
		
		String fileName = file.getOriginalFilename();
		System.out.println("上传的文件名");
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
			System.out.println("用户图像所在地址:" + realPath+"/"+fileName);
			user.setImage(userIcon);
			user.setId(id);
			userService.updateUserIcon(user);
			
			result.put("data", "0");
			result.put("errorcode", "");
			result.put("message", "上传用户图像成功");
			result.put("respcode", ResultCode.SUCCESS);
		}else{
			result.put("data", "");
			result.put("errorcode", ResultCode.VINT);
			result.put("message", "文件不合法");
			result.put("respcode", ResultCode.FAIL);
		}
		
		return result;

	}

	/**
	 * 修改用户资料
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

		System.out.println("更新用户资料传递过来的参数：" + "昵称:" + name + "城市" + city
				+ "年龄:" + age + "性别" + sex + "地址:" + address + "\n"
				+ "用户图像存放的地址:" + realPath);

		File path = new File(realPath);
		if (!path.exists()) {
			path.mkdirs();
		}
		System.out.println("图像文件名:" + fileName);
		if (!fileName.isEmpty()) {
			// 有图像的修改
			if (fileName.contains(".jpg") || fileName.contains(".png")) {

				File destFile = new File(realPath, fileName);
				if (!destFile.exists()) {
					destFile.mkdirs();
				}
				file.transferTo(destFile);
				String userIcon = "/userIcon/" + fileName;
				System.out.println("用户图像所在地址:" + userIcon);

				user.setId(id);
				user.setName(name);
				user.setImage(userIcon);
				user.setCity(city);
				user.setSex(sexs);
				user.setAge(ages);
				user.setAddress(address);
				userService.updateUserInfoImage(user);

				result.put("message", "修改用户资料成功");
				result.put("respcode", ResultCode.SUCCESS);
				result.put("data", "0");
				result.put("errorcode", ResultCode.SUCCESS);

			} else {
				// 非法文件
				result.put("message", "非法文件");
				result.put("respcode", ResultCode.FAIL);
				result.put("data", "");
				result.put("errorcode", ResultCode.FILENOLEGAL);
			}
		} else {
			// 没有图像的修改
			user.setId(id);
			user.setName(name);
			user.setCity(city);
			user.setSex(sexs);
			user.setAge(ages);
			user.setAddress(address);
			userService.updateUserInfo(user);

			result.put("message", "修改用户资料成功");
			result.put("respcode", ResultCode.SUCCESS);
			result.put("data", "0");
			result.put("errorcode", ResultCode.SUCCESS);
		}

		return result;

	}

	/**
	 * 根据用户id获取用户详细信息 
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

		System.out.println("用户id:" + id);
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

					result.put("message", "查询成功");
					result.put("respcode", ResultCode.SUCCESS);
					result.put("data", userResult);
					result.put("errorcode", "");
				} else {
					// 没有查询到该用户
					result.put("message", "用户不存在");
					result.put("respcode", ResultCode.FAIL);
					result.put("data", "");
					result.put("errorcode", ResultCode.UNUSEREXIST);
				}


		return result;

	}

    /**
     * 获取All User	
     */
	@RequestMapping("allUsers")
	@ResponseBody
	public Map<String, Object> queryAllUsers(HttpServletRequest request) {
		Map<String, Object> returnData = new HashMap<String, Object>();
		
		try {
			List<User> users = userService.queryAllUsers();
			returnData.put("respcode", ResultCode.SUCCESS);
			returnData.put("message", "查询所有用户成功！");
			returnData.put("data", users);
		} catch (Exception e) {
			returnData.put("respcode", ResultCode.FAIL);
			returnData.put("msg", "查询所有用户失败！");
			returnData.put("data", "");
	}
		
		return returnData;
	}
}
