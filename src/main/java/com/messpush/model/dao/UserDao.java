package com.messpush.model.dao;

import java.util.List;

import com.messpush.model.bean.User;

/**
 * 
 * @author Junv
 *执行用户信息的相关操作，eg:CRUD
 */
public interface UserDao {
	/**
	 * 保存一个用户信息
	 * @param user
	 * @return  User
	 */
	public User  saveUser(User user);
	
	/**
	 * 更新一个用户信息
	 * @param user
	 * @return User
	 */
	public User updateUser(User user);
	
	
    /**
     * 通过页面信息来获取多个用户信息
     * @param hql
     * @param start
     * @param count
     * @return   List<User>
     */
	public List<User> selectUsers(String hql,Integer start,Integer count);
	
	/**
	 * 删除一个用户信息，删除正确，将返回空的User对象
	 * @param userId
	 * @return User
	 */
	public User deleteUser(Integer   userId);
	
	
	/**
	 * 通过用户吗判断某用户是否存在，并返回User对象<br>
	 * 同样也作为返回一个用户信息的方法
	 * @param username
	 * @return  User
	 */
	public User getUserByName(String  username);
	/**
	 * 返回用户表中总的记录条数
	 * @return int
	 */
	public int getCount( );
	
	/**
	 * 通过用户ID获取一个用户的信息
	 * @param userId
	 * @return  User
	 */
	public  User getUserById(Integer userId);
	/**
	 * 通过用户ID改变用户密码
	 * @param userId 用户ID
	 * @param newPassword 新密码
	 * @return  User
	 */
	public User changePassword(Integer userId,String newPassword);
	
	

}
