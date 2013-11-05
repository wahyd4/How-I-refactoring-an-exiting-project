package com.messpush.model.dao;

import java.util.List;

import com.messpush.model.bean.UserGroup;

/**
 * 定义用户类别Dao的接口方法
 * 
 * @author Junv
 * 
 */
public interface UserGroupDao {
	/**
	 * 通过用户类别ID获取一个用户类别信息<br>
	 * 也可以作为验证某个用户类别是否存在
	 * 
	 * @param groupId
	 * @return UserCate
	 */
	public UserGroup getUserGroupById(Integer groupId);

	/**
	 * 获取所有用户类别信息
	 * 
	 * @return List<UserCate>
	 */
	public List<UserGroup> getAllUserGroups();

}
