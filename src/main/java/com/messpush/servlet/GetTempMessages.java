package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.messpush.controller.service.TempMessageService;

/**
 * 通过页面编号获取暂时信息
 */
@WebServlet("/GetTempMessage")
public class GetTempMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String returnXML = null;
		PrintWriter out = response.getWriter();
		Integer page = Integer.parseInt(request.getParameter("page"));
		TempMessageService tempService = new TempMessageService(request);
		returnXML = tempService.getTempMessagesByPage(page);
		out.print(returnXML);
		System.out.println(returnXML);
	}

}
