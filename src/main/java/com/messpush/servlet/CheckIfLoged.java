package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.messpush.controller.service.UserService;

/**
 * 在用户打开登录页面时<br>
 * 判断用户是否已经登录，如果已经登录将其跳转至首页
 */
@WebServlet("/CheckIfLoged")
public class CheckIfLoged extends HttpServlet {
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
		//实例化一个用户服务对象
		UserService user = new UserService(request);
		returnXML = user.checkUserIsLoged();
		//输出信息
		out.print(returnXML);
		System.out.println(returnXML);
	}

}
