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
 * 删除一条待审核的信息
 */
@WebServlet("/DeleteTempMessage")
public class DeleteTempMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String returnXML = null;
		PrintWriter out = response.getWriter();
		//获取需要删除的信息ID
		Integer messageId = Integer.parseInt(request.getParameter("id"));
		TempMessageService tempService = new TempMessageService(request);
		returnXML = tempService.deleteTempMessage(messageId);
		out.print(returnXML);
		System.out.println(returnXML);
	}

}
