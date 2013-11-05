package com.messpush.controller.service;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.messpush.controller.factory.HibernateDaoFactory;
import com.messpush.model.bean.GroupToAuthority;
import com.messpush.model.bean.UserGroup;
import com.messpush.model.dao.GroupToAuthorityDao;
import com.messpush.model.dao.UserGroupDao;

/**
 * 所有用户类别方法的封装
 * @author Junv
 *
 */
public class UserGroupService {
	HttpServletRequest request = null;
	/**
	 * 实例化该服务类，需要传入HttpServletRequest 对象
	 * @param request
	 */
	public UserGroupService(HttpServletRequest request){
		this.request = request;
	}
	/**
	 * 获取所有用户类别信息
	 * @return  String 封装好的xml
	 */
	public String getAllUserGroups(){
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//实例化daofactory
		HibernateDaoFactory factory = new HibernateDaoFactory(request); 
		UserGroupDao  userGroupDao = factory.getUserGroupDao();
		//获取所有用户类别
		List<UserGroup> groups=  userGroupDao.getAllUserGroups();
		sb.append("<groups size='"+groups.size()+"'>");
		Iterator<UserGroup> it = groups.iterator();
		while(it.hasNext()){
			UserGroup temp  = it.next();
			sb.append("<groupitem>");
			sb.append("<groupid>"+temp.getGroupId()+"</groupid>");
			sb.append("<groupname>"+temp.getGroupName()+"</groupname>");
			sb.append("<groupdesc>"+temp.getGroupDesc()+"</groupdesc>");
			sb.append("</groupitem>");
		}
		sb.append("</groups>");
		sb.append("</result>");
		return sb.toString();
		
	}
	
	/**
	 * 判断某个用户组是否有某个权限
	 * @param groupId 用户组ID
	 * @param authorityId  权限ID
	 * @return boolean 布尔值
	 */
	public boolean  checkIfGroupHaveAuthority(Integer groupId,Integer authorityId){
		 GroupToAuthorityDao groupAuthoritys = new HibernateDaoFactory(request).getGroupToAuthorityDao();
		 //获取改用户组的所有权限
		 GroupToAuthority authoritys = groupAuthoritys.getAuthorityByGroupId(groupId);
		 //获取所有权限字符串
		 String authorityList = authoritys.getAuthorityList();
		 //通过 ,将字符串分割
		 String [] tempList = authorityList.split(",");
		 for(int length=0;length<tempList.length;length++){
			 //如果权限匹配则返回true
			if( authorityId.toString().equals(tempList[length])){
				return true;
			}
		 }
		 //如果没有找到匹配的权限则返回false
		return false;
	}
    
	/**
	 * 通过用户类别ID获取该用户类别名称
	 * @param userGroupId
	 * @return  String 用户类别名称
	 */
    public String getUserGroupNameById(Integer userGroupId){
    	HibernateDaoFactory factory = new HibernateDaoFactory(request); 
		UserGroupDao  userGroupDao = factory.getUserGroupDao();
		//获取
		UserGroup returnObj = userGroupDao.getUserGroupById(userGroupId);
		return returnObj.getGroupName();
    }
}
