package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.messpush.controller.factory.HibernateDaoFactory;
import com.messpush.controller.service.AuthorityService;
import com.messpush.model.bean.User;
import com.messpush.model.dao.UserDao;

/**
 * 获取某个用户的所有权限
 * 
 */
@WebServlet("/GetUserAuthority")
public class GetUserAuthority extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String returnXML = null;
		//获取session中的sessionId
		HttpSession session = request.getSession();
		//获取用户ID
		Integer userId =  (Integer) session.getAttribute("userid");
		//获取一个userDao对象
		UserDao userDao = new HibernateDaoFactory(request).getUserDao();
		//获取用户对象
		User user = userDao.getUserById(userId);
		AuthorityService authorityService = new AuthorityService(request);
		//获取该用户所在用户组的所有权限
		returnXML = authorityService.getAuthorityByGroupId(user.getUserGroupId());
		out.print(returnXML);
		System.out.println(returnXML);
	}

}
