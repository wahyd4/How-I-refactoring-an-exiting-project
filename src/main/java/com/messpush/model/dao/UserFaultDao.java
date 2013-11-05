package com.messpush.model.dao;

import java.util.ArrayList;

import com.messpush.model.bean.UserFault;

/**
 * 提供处理用户恶意访问行文和后台错误操作行为的相关接口
 * @author Junv
 *
 */
public interface UserFaultDao {
	
	/**
	 * 保存用户的错误操作信息
	 * @param usrFault
	 * @return UserFault
	 */
	public UserFault saveUserFault(UserFault usrFault);
	
	/**
	 * 更新用户的错误操作信息
	 * @param usrFault
	 * @return  UserFault
	 */
	public  UserFault updateUserFault(UserFault usrFault);
	
	/**
	 * 获取多条用户操作的错误信息
	 * @param hql
	 * @return  ArrayList<UserFault>
	 */
    public ArrayList<UserFault> selectUserFaults(String hql);
    
    /**
     * 删除某条用户的错误操作信息，如果返回对象为空则删除成功
     * @param usrFault
     * @return  UserFault
     */
    public UserFault deleteUserFault(Long  usrFaultId);
    
    /**
     * 检查某条用户错误操作信息是否存在
     * @param usrFaultId
     * @return  UserFault
     */
    public UserFault checkUserFaultExist(Long usrFaultId);

}
