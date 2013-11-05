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
import com.messpush.controller.service.MessageService;
/**
 * 通过页面编号获取数据
 */
@WebServlet("/GetMessages")
public class GetMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String returnXML = null;
		int page = Integer.parseInt(request.getParameter("page"));
		//获取信息服务对象
		MessageService   ms = new MessageService(request);
		//获取权限服务类
		AuthorityService authorityService = new AuthorityService(request);
		//获取Session中的userid
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userid");
		//验证用户是否拥有查看和编辑所有信息的权限
		if(authorityService.checkUserHasAuthority(userId, 13)){
			System.out.println("-----------------22222222222222222222222222");
		    returnXML = ms.getMessagesByPage(page);   //获取第n页的数据
		}else{
			System.out.println("-----------------1111111111111111111");
			returnXML = ms.getMessagesBySomeone(userId, page);
		}
	
		System.out.print(returnXML);
		out.print(returnXML);
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
