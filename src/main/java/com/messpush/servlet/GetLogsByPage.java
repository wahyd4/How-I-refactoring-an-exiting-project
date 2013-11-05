package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.messpush.controller.service.LogService;

/**
 * 通过页面编号获取日志记录
 */
@WebServlet("/GetLogsByPage")
public class GetLogsByPage extends HttpServlet {
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
		//获取请求参数。页面编号
		int page =Integer.parseInt(request.getParameter("page"));
		//获取日志记录服务
		LogService logService = new LogService(request);
		returnXML = logService.getLogsByPage(page);
		System.out.println(returnXML);
		out.print(returnXML);
	}

}
