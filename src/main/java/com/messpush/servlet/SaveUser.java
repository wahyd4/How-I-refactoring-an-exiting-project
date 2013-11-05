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
 * 保存一个用户信息
 */
@WebServlet("/SaveUser")
public class SaveUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveUser() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String returnXML = null;
		//实例化用户服务对象
		UserService us = new UserService(request);
		User user = new User();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setRealName(request.getParameter("real_name"));
		user.setTelphone(request.getParameter("tel"));
		user.setMobilephone(request.getParameter("phone"));
		user.setEmail(request.getParameter("email"));
		user.setUserGroupId(Integer.parseInt(request.getParameter("groupid")));
		user.setIsBlocked("false");  //默认设置不屏蔽该用户
		returnXML = us.addUser(user);
		out.print(returnXML);
		System.out.println(returnXML);
	}

}
