package com.hongdong.bean;
/**
 * 用户
 * @author zhangxx
 *
 */
public class User {

	//ID
	private int id;

	//OPENID
	private String openid;
	
	//用户名
	private String phone;
	
	//密码
	private String password;
	
	//用户图像
	private String image;
	
	//用户昵称
	private String name;
	
	//城市
	private String city;
	
	//年龄
	private int age;
	
	//性别
	private int sex;
	
	//地址
	private String address;
	
	//金币
	private int gold;

	//水晶
	private int crystal;
	
	//会员
	private int vip;

	//Level
	private int level;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String id) {
		this.openid = id;
	}

	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getCrystal() {
		return this.crystal;
	}

	public void setCrystal(int crystal) {
		this.crystal = crystal;
	}
	
	public int getVip() {
		return this.vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
