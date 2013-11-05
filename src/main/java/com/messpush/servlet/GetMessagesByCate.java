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
 * 通过类别编号获取信息
 */
@WebServlet("/GetMessagesByCate")
public class GetMessagesByCate extends HttpServlet {
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
		int page = Integer.parseInt(request.getParameter("page"));
		int cateId = Integer.parseInt(request.getParameter("cateid"));
		//获取权限服务类
		AuthorityService authorityService = new AuthorityService(request);
		//获取Session中的userid
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userid");
		//获取信息服务对象
		MessageService   ms = new MessageService(request);
		//验证用户是否拥有查看和编辑所有信息的权限
		if(authorityService.checkUserHasAuthority(userId, 13)){
			if(cateId==0){
				returnXML = ms.getMessagesByPage(page);
			}else{
				returnXML = ms.getMessagesByCate(page, cateId);	
			}
		}else{
			if(cateId==0){
				returnXML = ms.getMessagesBySomeone(userId, page);
			}else{
				returnXML = ms.getMessagesBySomeoneAndCategory(userId, cateId, page);
			}
		}
		
	
		out.print(returnXML);
		System.out.println(returnXML);
	}

}
