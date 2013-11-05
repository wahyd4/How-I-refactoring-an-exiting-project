package com.messpush.controller.factory;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.messpush.model.dao.AuthorityDao;
import com.messpush.model.dao.CategoryDao;
import com.messpush.model.dao.GroupToAuthorityDao;
import com.messpush.model.dao.LogDao;
import com.messpush.model.dao.MessageDao;
import com.messpush.model.dao.SerialDao;
import com.messpush.model.dao.SettingDao;
import com.messpush.model.dao.TempMessageDao;
import com.messpush.model.dao.UserDao;
import com.messpush.model.dao.UserGroupDao;

/**
 * 创建 生产Hibernate Dao实例的工厂类
 * @author Junv
 *
 */
public class HibernateDaoFactory {
	
     private	ApplicationContext ctx ;
    
    
	public  HibernateDaoFactory(HttpServletRequest request){
		//实例化ApplicationContext
		 ServletContext sc = request.getServletContext();
		ctx  = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		
	}
	
	/**
	 * 返回一个 Hibernate CategoryDao 实例
	 * @return  CategoryDao
	 */
	public  CategoryDao getCategoryDao( ){
		 
		
		return (CategoryDao) ctx.getBean("CategoryDao");
	}
	
	/**
	 * 创建并返回一个Hibernate MessageDao实例
	 * @return  MessageDao
	 */
	public  MessageDao getMessageDao( ){
		
	
		return (MessageDao)ctx.getBean("MessageDao");
	}
	/**
	 * 创建并返回一个Hibernate UserDao实例
	 * @return UserDao
	 */
	public  UserDao getUserDao( ){
		
		return  (UserDao)ctx.getBean("UserDao");
	}
	
	/**
	 * 创建并返回一个Hibernate  SerialDao 实例
	 * @return SerialDao
	 */
	public  SerialDao getSerialDao(){
	
		return (SerialDao)ctx.getBean("SerialDao");
	}
    
	/**
	 * 创建并返回一个SettingDao实例
	 * @return  SettingDao
	 */
	public  SettingDao getSettingDao(){
		
		return (SettingDao) ctx.getBean("SettingDao");
	}
	
	/**
	 * 创建并返回一个LogDao
	 * @return LogDao
	 */
	public LogDao getLogDao(){
		
		return(LogDao) ctx.getBean("LogDao");
	}
	/**
	 * 创建并返回一个UserGroupDao
	 * @return  UserGroupDao 对象
	 */
	public UserGroupDao getUserGroupDao(){
		return (UserGroupDao) ctx.getBean("UserGroupDao");
	}
	
	/**
	 * 创建并返回一个AuthorityDao实例
	 * @return AuthorityDao
	 */
	public AuthorityDao getAuthorityDao(){
		return (AuthorityDao) ctx.getBean("AuthorityDao");
	}
	/**
	 * 闯将并返回一个GroupToAuthorityDao实例
	 * @return  GroupToAuthorityDao
	 */
	public GroupToAuthorityDao getGroupToAuthorityDao(){
		return (GroupToAuthorityDao) ctx.getBean("GroupToAuthorityDao");
	}
	
	/**
	 * 获取并返回一个TempMessageDao实例
	 * @return TempMessageDao
	 */
	public TempMessageDao getTempMessageDao(){
		return (TempMessageDao) ctx.getBean("TempMessageDao");
	}
}
