package com.messpush.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 定义用户类别的bean
 * @author Junv
 *
 */
@Entity
@Table(name="user_group")
public class UserGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="cate_id")
	private  Integer groupId;
	@Column(name="user_group_name")
	private String groupName;
	@Column(name="user_group_desc")
	private String groupDesc;
	
	public UserGroup(){
		
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	
	

}
