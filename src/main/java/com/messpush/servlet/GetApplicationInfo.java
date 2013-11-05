package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.messpush.controller.service.SettingService;

/**
 * 获取应用程序的基本信息
 */
@WebServlet("/GetApplicationInfo")
public class GetApplicationInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
  
    /**
     * 在获取基本信息的基础上，还得到了用户的基本信息
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String returnXML = null;
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//获取用户ID和用户名信息
		HttpSession session  = request.getSession();
		sb.append("<userid>"+session.getAttribute("userid")+"</userid>");
		sb.append("<username>"+session.getAttribute("username")+"</username>");
		//获取设置服务对象
		SettingService ss=  new SettingService(request);
		returnXML = ss.getSettingInfo();
		//将系统设置信息也加到xml中
		sb.append(returnXML);
		sb.append("</result>");
		out.print(sb.toString());
		System.out.println(sb.toString());
	}

}
