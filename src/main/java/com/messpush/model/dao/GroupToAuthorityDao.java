package com.messpush.model.dao;

import com.messpush.model.bean.GroupToAuthority;

/**
 * 定义用户组所拥有的权限的操作接口
 * @author Junv
 *
 */
public interface GroupToAuthorityDao {
	
	/**
	 * 通过用户组ID获取该组所拥有的权限<br>
	 * 同时也是判断某个用户组是否存在的依据
	 * @param groupId 用户组ID
	 * @return  GroupToAuthority 对象
	 */
	public GroupToAuthority getAuthorityByGroupId(Integer groupId);
	
	/**
	 * 保存一个用户组的权限信息
	 * @param authoritys 用户组对象
	 * @return GroupToAuthority 对象
	 */
	public  GroupToAuthority saveGoupAuthority(GroupToAuthority authoritys);
	
   /**
    * 更新一个用户组的权限信息
    * @param  authoritys 用户组对象
    * @return  GroupToAuthority
    */
	public  GroupToAuthority updateGroupAuthority(GroupToAuthority authoritys);


}
