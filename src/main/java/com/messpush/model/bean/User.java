package com.messpush.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户相关信息的Bean
 * @author Junv
 *
 */
@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="username")
	private String username;
	
	@Column(name="pass")
	private String password;
	
	@Column(name="real_name")
	private String realName;
	
	
	@Column(name="email")
	private String email;
	
	@Column(name="tel")
	private String telphone;
	
	@Column(name="mobile_phone")
	private String mobilephone;
	
	@Column(name="is_blocked")
	private String isBlocked;
	
	@Column(name="user_cate_id")
	private Integer userGroupId;   //用户类别ID
	
    public User(){
    	
    }

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(String isBlocked) {
		this.isBlocked = isBlocked;
	}

	public Integer getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}



    
    
}
