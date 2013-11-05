package com.messpush.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 定义所有权限的列表实体类
 * @author Junv
 *
 */
@Entity
@Table(name="authority")
public class Authority {
	@Id
	@Column(name="auth_id")
	private Integer authorityId;
	@Column(name="auth_name")
	private  String authorityName;
	@Column(name="auth_desc")
	private String authorityDescribe;
	
	@Column(name="auth_action")
	private String authorityAction;
	@Column(name="parent")
	private Integer parent;
	
	public  Authority(){
		
	}
	public Integer getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(Integer authorityId) {
		this.authorityId = authorityId;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	public String getAuthorityDescribe() {
		return authorityDescribe;
	}
	public void setAuthorityDescribe(String authorityDescribe) {
		this.authorityDescribe = authorityDescribe;
	}
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public String getAuthorityAction() {
		return authorityAction;
	}
	public void setAuthorityAction(String authorityAction) {
		this.authorityAction = authorityAction;
	}
	
    
}
