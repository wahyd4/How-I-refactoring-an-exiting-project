package com.messpush.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户组与权限的对应表
 * @author Junv
 *
 */
@Entity
@Table(name="group_authoritys")
public class GroupToAuthority {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(name="groupid")
	private Integer groupId;
	@Column(name="authority_list")
	private String authorityList;
	
	public  GroupToAuthority(){
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getAuthorityList() {
		return authorityList;
	}
	public void setAuthorityList(String authorityList) {
		this.authorityList = authorityList;
	}
	
    
}
