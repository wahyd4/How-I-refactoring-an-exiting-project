package com.messpush.controller.service;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.NDC;

import com.messpush.constant.Constant;
import com.messpush.controller.factory.HibernateDaoFactory;
import com.messpush.model.bean.User;
import com.messpush.model.dao.UserDao;

/**
 * 定义用户用户相关操作的高级封装，返回XML
 * 
 * @author Junv
 * 
 */
public class UserService {
	private HttpServletRequest request = null;
	//定义log4j的logger
	private final Log logger = LogFactory.getLog(getClass());
	/**
	 * 实例化用户服务对象，需要传入HttpServletRequest对象
	 * @param request
	 */
	public UserService(HttpServletRequest request){
		this.request = request;
	}
	/**
	 * 闯将添加用户的service类。并返回xml
	 * 
	 * @param user
	 * @return String
	 */
	public String addUser(User user ) {
		//调用UserGroupService 将 用户类别ID转成用户类别名称
		UserGroupService userGroupService = new UserGroupService(request);
		// 开始构建返回xml
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//实例化工厂类
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		// 获取hibernateDao
		UserDao userDao =factory.getUserDao();
		// 得到返回对象
		User retObj = userDao.saveUser(user);
		// 构建
		if (retObj == null) {
			sb.append("<value>false</value>");
		} else {
			sb.append("<value>true</value>");
			sb.append("<users size='1'>");
			sb.append("<useritem>");
			sb.append("<username>" + retObj.getUsername() + "</username>");
			sb.append("<email>" + retObj.getEmail() + "</email>");
			sb.append("<isblocked>" + retObj.getIsBlocked()
					+ "</isblocked>");
			sb.append("<realname>" + retObj.getRealName() + "</realname>");
			sb.append("<tel>" + retObj.getTelphone() + "</tel>");
			sb.append("<mobilephone>" + retObj.getMobilephone()
					+ "</mobilephone>");
			sb.append("<userid>" + retObj.getUserId() + "</userid>");
			sb.append("<usergroupid>"+retObj.getUserGroupId()+"</usergroupid>");
			sb.append("<usergroupname>"+userGroupService.getUserGroupNameById(retObj.getUserGroupId())+"</usergroupname>");
			sb.append("</useritem>");
			sb.append("</users>");
		}
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 更新一个用户，需要输入你想改变的对象
	 * 
	 * @param user
	 * @return String
	 */
	public String updateUser(User user) {
		//调用UserGroupService 将 用户类别ID转成用户类别名称
				UserGroupService userGroupService = new UserGroupService(request);
		// 包装返回对象并返回xml数据
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//实例化工厂类
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		// 获取hibernateDao
		UserDao userDao =factory.getUserDao();
		// 通过用户名得到我们需要修改的用户对象
		User temp = userDao.getUserById(user.getUserId());
		// 更新对象的所有内容
		temp.setEmail(user.getEmail());
		temp.setIsBlocked(user.getIsBlocked());
		temp.setMobilephone(user.getMobilephone());
		temp.setRealName(user.getRealName());
		temp.setTelphone(user.getTelphone());
		// 开始更新数据
		User retObj = userDao.updateUser(temp);
		if (retObj == null) {
			sb.append("<value>false</value>");
		} else {
			sb.append("<value>true</value>");
			sb.append("<users size='1'>");
			sb.append("<useritem>");
			sb.append("<username>" + retObj.getUsername() + "</username>");
			sb.append("<email>" + retObj.getEmail() + "</email>");
			sb.append("<isblocked>" + retObj.getIsBlocked()
					+ "</isblocked>");
			sb.append("<realname>" + retObj.getRealName() + "</realname>");
			sb.append("<tel>" + retObj.getTelphone() + "</tel>");
			sb.append("<mobilephone>" + retObj.getMobilephone()
					+ "</mobilephone>");
			sb.append("<userid>" + retObj.getUserId() + "</userid>");
			sb.append("<usergroupid>"+retObj.getUserGroupId()+"</usergroupid>");
			sb.append("<usergroupname>"+userGroupService.getUserGroupNameById(retObj.getUserGroupId())+"</usergroupname>");
			sb.append("</useritem>");
			sb.append("</users>");
		}
		sb.append("</result>");

		return sb.toString();
	}

	/**
	 * 通过输入的某一页	ID，返回一个页面的用户信息<br>
	 * 默认一个页面显示20条数据
	 * 
	 * @param page
	 * @return String
	 */
	public String getUsersByPage(int page ) {
		//调用UserGroupService 将 用户类别ID转成用户类别名称
		UserGroupService userGroupService = new UserGroupService(request);
		
		int pageCount = Constant.SINGLEPAGECOUNT;
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");

		// 构建我们需要的hql，查询分页数据
		String hql = "from User";
		//实例化工厂类
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		// 获取hibernateDao
		UserDao userDao =factory.getUserDao();
		int count = userDao.getCount();
		sb.append("<totalcount><value>" + count + "</value></totalcount>");
		sb.append("<singlepage><value>" + Constant.SINGLEPAGECOUNT
				+ "</value></singlepage>");
		// 获取当前页面的所有用户
		List<User> users = userDao.selectUsers(hql, (page * pageCount - 1),
				pageCount);
		sb.append("<users size='" + users.size() + "'>");
		Iterator<User> it = users.iterator();
		while (it.hasNext()) {
			User temp = it.next();
			sb.append("<useritem>");
			sb.append("<username>" + temp.getUsername() + "</username>");
			sb.append("<email>" + temp.getEmail() + "</email>");
			sb.append("<isblocked>" + temp.getIsBlocked()
					+ "</isblocked>");
			sb.append("<realname>" + temp.getRealName() + "</realname>");
			sb.append("<tel>" + temp.getTelphone() + "</tel>");
			sb.append("<mobilephone>" + temp.getMobilephone()
					+ "</mobilephone>");
			sb.append("<userid>" + temp.getUserId() + "</userid>");
			sb.append("<usergroupid>"+temp.getUserGroupId()+"</usergroupid>");
			sb.append("<usergroupname>"+userGroupService.getUserGroupNameById(temp.getUserGroupId())+"</usergroupname>");
			sb.append("</useritem>");
		}
		sb.append("</users>");
		sb.append("</result>");

		return sb.toString();

	}

	/**
	 * 通过用户名删除一个用户信息
	 * 
	 * @param userId
	 * @return String
	 */
	public String deleteUser(Integer userId) {

		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//实例化工厂类
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		// 获取hibernateDao
		UserDao userDao =factory.getUserDao();
		User retObj = userDao.deleteUser(userId);
		// 如果返回对象为空表示删除成功
		if (retObj == null) {
			sb.append("<status>true</status>");
		} else {
			sb.append("<status>false</status>");
		}
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 
	 * @param user  前台用户的用户名和密码
	 * @return String 验证用户登录是否成功，并且返回相应xml，如果登录成功，将存入session
	 */
	public String checkLogin(User user) {
	
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//实例化工厂类
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		// 获取hibernateDao
		UserDao userDao =factory.getUserDao();
		User retObj = userDao.getUserByName(user.getUsername());
		System.out.println("user======" + user.getUsername());
		System.out.println("pass=====" + user.getPassword());
		if (retObj == null) {
			sb.append("<status>none</status>");

		} else {
			if (retObj.getPassword().equals(user.getPassword())) {
				//如果该用户被屏蔽,返回屏蔽相关字符串，并立即返回
				if(retObj.getIsBlocked().equals("true")){
					return "<result><status>blocked</status></result>";
				}
				// 存入userid和当前的Session id也存入session中。可以防止一个账号多人登录
				HttpSession session = request.getSession();
				// 如果系统已经存在该用户的登录情况，则进行下一步判断
				String userId = retObj.getUserId().toString();
				// 存入userid
				session.setAttribute("userid", retObj.getUserId());
				//将用户名存入其中，方便返回到客户端
				session.setAttribute("username", retObj.getUsername());
				// 存入sessionid 。键为userid,将其存入Application 中
				ServletContext  application = session.getServletContext();
				application.setAttribute(userId, session.getId());
				// 返回xml数据
				sb.append("<status>true</status>");
				sb.append("<userid>" + retObj.getUserId() + "</userid>");
			} else {
				//将用户名传入LOG4J中，并向数据库存入信息
				NDC.push(retObj.getUsername());
				logger.warn(retObj.getUsername()+"尝试错误登录");
				sb.append("<status>false</status>");
			}
		}

		sb.append("</result>");

		return sb.toString();
	}

	/**
	 * 用户退出系统时，清除该用户的所有session信息。<br>
	 * 并返回清除结果，成功返回信息
	 * 
	 * @return xml
	 */
	public String userLogout() {

		HttpSession session = request.getSession();
		Integer userid = (Integer) session.getAttribute("userid");
		if (userid != null) {
			Enumeration<String> e = session.getAttributeNames();
			// 当session中不止一条记录时，清除所有记录
			while (e.hasMoreElements()) {
				String sessionName = (String) e.nextElement();
				session.removeAttribute(sessionName);
			}
			//清空存储在Application中的信息
			ServletContext servletContext = request.getServletContext();
			servletContext.removeAttribute(userid.toString());
			System.out.println("session deleted..............................");
		//	logger.error("用户退出了，测试时写成错误，UserService 269");
			return "<result><value>true</value></result>";
		}
		//清除信息失败
		return "<result><value>false</value></result>";
	}
	
	/**
	 * 判断用户是否已经登录，如已经登录将不需要再进行登录
	 * @return  String xml
	 */
	public String checkUserIsLoged(){
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//获取session
		HttpSession session = request.getSession();
		//如果不为空，说明用户已经登录
		if(session.getAttribute("userid")!=null){
			sb.append("<value>"+true+"</value>");
		}else{
			sb.append("<value>"+false+"</value>");
		}
		sb.append("</result>");
		return sb.toString();
	}
	/**
	 * 改变密码，用户数据通过session获取
	 * @param orginalPassword
	 * @param newPassword
	 * @return  String xml数据
	 */
	public String changePassword(String orginalPassword,String newPassword){
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		HttpSession session = request.getSession();
		Integer userId  = (Integer) session.getAttribute("userid");
		//获取dao
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		// 获取hibernateDao
		UserDao userDao =factory.getUserDao();
		//通过userid获取用户数据
		User returnObj = userDao.getUserById(userId);
		System.out.println("原有密码"+returnObj.getPassword());
		System.out.println("传入密码"+orginalPassword);
		//如果用户输入激活码正确
		if(returnObj.getPassword().equals(orginalPassword)){
			
			//执行更改密码
			userDao.changePassword(userId, newPassword);
			sb.append("<value>true</value>");
		}else{
			sb.append("<value>false</value>");
		}
		//返回数据
		sb.append("</result>");
		return sb.toString();
	}
   
}
