package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.messpush.controller.service.AuthorityService;
import com.messpush.model.bean.GroupToAuthority;

/**
 * 更新某个用户组的权限信息
 */
@WebServlet("/UpdateAuthority")
public class UpdateAuthority extends HttpServlet {
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
		//将更改信息存入权限与用户组表
		GroupToAuthority  authority = new GroupToAuthority();
		System.out.println("权限列表为"+request.getParameter("update_auth_lists"));
		System.out.println("用户组ID为"+request.getParameter("update_auth_group_select"));
		System.out.println("权限ID为"+request.getParameter("group_to_auth_id"));
		authority.setAuthorityList(request.getParameter("update_auth_lists"));
		authority.setGroupId(Integer.parseInt(request.getParameter("update_auth_group_select")));
		authority.setId(Integer.parseInt(request.getParameter("group_to_auth_id")));
		//实例化权限服务类,获取系统所有权限
		AuthorityService  authorityService = new AuthorityService(request);
		returnXML = authorityService.updateAuthority(authority);
		out.print(returnXML);
		System.out.println(returnXML);
	}

}
