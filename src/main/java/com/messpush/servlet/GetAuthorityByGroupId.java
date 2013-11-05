package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.messpush.controller.service.AuthorityService;


/**
 * 根据传入的groupID获取某个用户组的权限列表
 */
@WebServlet("/GetAuthorityByGroupId")
public class GetAuthorityByGroupId extends HttpServlet {
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
		//获取前台传入的groupId
	   Integer groupId = Integer.parseInt(request.getParameter("groupid"));
	   System.out.println("获取groupId===="+groupId.toString());
		AuthorityService authorityService = new AuthorityService(request);
		//获取该用户所在用户组的所有权限
		returnXML = authorityService.getAuthorityByGroupId(groupId);
		out.print(returnXML);
		System.out.println(returnXML);
	}

}
