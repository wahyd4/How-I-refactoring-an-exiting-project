package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.messpush.controller.service.UserService;
import com.messpush.model.bean.User;

/**
 *更新一个用户
 */
@WebServlet("/UpdateUser")
public class UpdateUser extends HttpServlet {
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
		//实例化一个用户类
		UserService userService = new UserService(request);
		User user = new User();
		user.setEmail(request.getParameter("update_email"));
		user.setMobilephone(request.getParameter("update_phone"));
		user.setTelphone(request.getParameter("update_tel"));
		user.setRealName(request.getParameter("update_real_name"));
		user.setUserGroupId(Integer.parseInt(request.getParameter("update_groupid")));
		user.setUserId(Integer.parseInt(request.getParameter("userid")));
		user.setIsBlocked(request.getParameter("update_isblocked"));
		//更新一个用户
		returnXML  =userService.updateUser(user);
		out.print(returnXML);
		System.out.println(returnXML);
	}

}
