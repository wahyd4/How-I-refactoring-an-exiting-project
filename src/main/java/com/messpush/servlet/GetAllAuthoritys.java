package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.messpush.controller.service.AuthorityService;

/**
 * 获取系统中所有的权限列表
 */
@WebServlet("/GetAllAuthoritys")
public class GetAllAuthoritys extends HttpServlet {
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
		//实例化权限服务类,获取系统所有权限
		AuthorityService  authorityService = new AuthorityService(request);
		returnXML = authorityService.getAllAuthoritysIgnoreLevel();
		out.print(returnXML);
		System.out.print(returnXML);
		
	}

}
