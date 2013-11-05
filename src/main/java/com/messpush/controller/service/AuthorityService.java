package com.messpush.controller.service;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.messpush.controller.factory.HibernateDaoFactory;
import com.messpush.model.bean.Authority;
import com.messpush.model.bean.GroupToAuthority;
import com.messpush.model.bean.User;
import com.messpush.model.bean.UserGroup;
import com.messpush.model.dao.AuthorityDao;
import com.messpush.model.dao.GroupToAuthorityDao;
import com.messpush.model.dao.UserDao;
import com.messpush.model.dao.UserGroupDao;

/**
 * 权限相关方法的封装实现
 * 
 * @author Junv
 * 
 */
public class AuthorityService {
	HttpServletRequest request = null;

	/**
	 * 实例化权限服务类，需要传入HttpServletRequest 对象
	 * 
	 * @param request
	 */
	public AuthorityService(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 获取某个用户组的所有权限并以xml的形式返回
	 * 
	 * @param groupId
	 * @return String xml文本
	 */
	public String getAuthorityByGroupId(Integer groupId) {
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//工厂实例
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		GroupToAuthorityDao groupToAuthDao =factory.getGroupToAuthorityDao();
		//获取用户组信息
		UserGroupDao  userGroupDao = factory.getUserGroupDao();
		UserGroup retUserGroup = userGroupDao.getUserGroupById(groupId);
		//返回该用户组的组信息
		sb.append("<groupinfo>");
		    sb.append("<groupid>"+retUserGroup.getGroupId()+"</groupid>");
		    sb.append("<groupname>"+retUserGroup.getGroupName()+"</groupname>");
		sb.append("</groupinfo>");
		//获取所有权限信息
		AuthorityDao authorityDao = factory.getAuthorityDao();
		//获取该用户组的所有权限列表
		GroupToAuthority  groupToAuthority = groupToAuthDao.getAuthorityByGroupId(groupId);
		//取得权限字符串
		String authoritys = groupToAuthority.getAuthorityList();
		//通过，分割字符串
		String [] authList = authoritys.split(",");
		sb.append("<authoritys size='"+authList.length+"' id='"+groupToAuthority.getId()+"'>");
	    for(int length=0;length<authList.length;length++){
	    	//通过权限ID获得该权限对象
	    	Authority temp = authorityDao.getAuthorityById(Integer.parseInt(authList[length]));
	    	sb.append("<authorityitem>");
	    	sb.append("<authid>"+temp.getAuthorityId()+"</authid>");
	    	sb.append("<authname>"+temp.getAuthorityName()+"</authname>");
	    	sb.append("<authdesc>"+temp.getAuthorityDescribe()+"</authdesc>");
	    	sb.append("<authaction>"+temp.getAuthorityAction()+"</authaction>");
	    	sb.append("<parent>"+temp.getParent()+"</parent>");
	    	sb.append("</authorityitem>");
	    }
	    sb.append("</authoritys>");
	    sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 通过权限ID获取权限名称
	 * 
	 * @param authorityId
	 * @return String 权限名称
	 */
	public String getAuthorityNameById(Integer authorityId) {
		AuthorityDao authorityDao = new HibernateDaoFactory(request)
				.getAuthorityDao();
		// 获取该权限
		Authority authority = authorityDao.getAuthorityById(authorityId);
		// 返回权限名称
		return authority.getAuthorityName();
	}

	/**
	 * 以xml的形式返回系统中所有的权限,并以分级的形式表示
	 * 
	 * @return String
	 */
	public String getAllAuthoritys() {
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		AuthorityDao authorityDao = new HibernateDaoFactory(request)
				.getAuthorityDao();
		List<Authority> topAuthorities = authorityDao.getTopAuthoritys();
		// 遍历顶级权限
		Iterator<Authority> it = topAuthorities.iterator();
		while (it.hasNext()) {
			Authority tempTopAuthority = it.next();
			sb.append("<topauth  name='" + tempTopAuthority.getAuthorityName()
					+ "' id='" + tempTopAuthority.getAuthorityId()
					+ "'  desc='" + tempTopAuthority.getAuthorityDescribe()
					+ "'>");
			List<Authority> secondAuthoritys = authorityDao
					.getSonAuthoritysById(tempTopAuthority.getAuthorityId());
			Iterator<Authority> secondIt = secondAuthoritys.iterator();
			while (secondIt.hasNext()) {
				Authority tempSecondAuthority = secondIt.next();
				sb.append("<secondauth name='"
						+ tempSecondAuthority.getAuthorityName() + "' id='"
						+ tempSecondAuthority.getAuthorityId() + "' desc = '"
						+ tempSecondAuthority.getAuthorityDescribe() +"' action = '"
						+ tempSecondAuthority.getAuthorityAction()+"'>");
				sb.append("</secondauth>");
			}
			sb.append("</topauth>");
		}
		sb.append("</result>");
		return sb.toString();
	}
	
	/**
	 *  以xml的形式返回系统中所有的权限,<br>
	 *  忽略分级
	 * @return
	 */
	public String getAllAuthoritysIgnoreLevel(){
		StringBuilder  sb = new StringBuilder();
		sb.append("<result>");
		AuthorityDao authorityDao = new HibernateDaoFactory(request)
				.getAuthorityDao();
		List<Authority> authoritys = authorityDao.getAllAuthoritys();
		Iterator<Authority> it = authoritys.iterator();
		sb.append("<authoritys size='"+authoritys.size()+"'>");
		//遍历该List
		while(it.hasNext()){
			Authority tempAuthority = it.next();
			//构建一个权限的xml
			sb.append("<authorityitem>");
			sb.append("<authid>"+tempAuthority.getAuthorityId()+"</authid>");
			sb.append("<authname>"+tempAuthority.getAuthorityName()+"</authname>");
			sb.append("<authdesc>"+tempAuthority.getAuthorityDescribe()+"</authdesc>");
			sb.append("<authaction>"+tempAuthority.getAuthorityAction()+"</authaction>");
			sb.append("<parent>"+tempAuthority.getParent()+"</parent>");
			sb.append("</authorityitem>");
		}
		sb.append("</authoritys>");
		sb.append("</result>");
		return sb.toString();
	}
	
	/**
	 * 保存一个用户类别的权限
	 * @param groupToAuthority
	 * @return String xml
	 */
	public String updateAuthority(GroupToAuthority groupToAuthority){
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		GroupToAuthorityDao groupToAuthDao = new HibernateDaoFactory(request).getGroupToAuthorityDao();
		//更新该用户组的权限，并验证更新是否成功
		GroupToAuthority returnObj =  groupToAuthDao.updateGroupAuthority(groupToAuthority);
		//根据更改是否成功添加相应的xml数据
		if(returnObj ==null){
			sb.append("<value>false</value>");
		}else{
			sb.append("<value>true</value>");
		}
		sb.append("</result>");
		return sb.toString();
	}
	
    /**
     * 判断某个用户是否有某个权限，返回布尔值
     * @param userId 用户ID
     * @param authorityId 权限ID
     * @return  boolean 布尔值
     */
	public boolean checkUserHasAuthority(Integer userId,Integer authorityId){
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		//获取一个用户dao
		UserDao userDao = factory.getUserDao();
		//获取一个用户和权限dao
		GroupToAuthorityDao groupToAuthorityDao =factory.getGroupToAuthorityDao();
       //获取该用户所对应的权限
		User tempUser = userDao.getUserById(userId);
		GroupToAuthority  tempAuthority = groupToAuthorityDao.getAuthorityByGroupId(tempUser.getUserGroupId());
		//获取权限列表
		String  authorityList  = tempAuthority.getAuthorityList();
		//通过,分隔
		String auths[] = authorityList.split(",");
		//遍历数组
		for(String temp:auths){
			//如果找到该权限则返回true
			if(temp.equals(authorityId.toString())){
				return true;
			}
		}
		return false;
	}
}
