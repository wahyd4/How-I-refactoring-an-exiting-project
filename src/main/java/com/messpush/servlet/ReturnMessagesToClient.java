package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.messpush.controller.service.MessageService;

/**
 * 返回信息到客户端
 */
@WebServlet("/ReturnMessagesToClient")
public class ReturnMessagesToClient extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//允许所有机器访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String returnXML = null;
		Long messageId = null;
		//实例化信息服务对象
		MessageService ms = new MessageService(request);
		/*
		 * HttpSession session=(HttpSession)request.getSession(); ServletContext
		 * application=(ServletContext)session.getServletContext();
		 */
		try {
			messageId = Long.parseLong(request.getParameter("msgid"));
			if (messageId == (long) 0) {
				returnXML = ms.getMessagesByPageASC();
			} else {
				returnXML = ms.getMessagesBehindMsgId(messageId);
			}
		} catch (NumberFormatException e) {
			System.out.println("数字编码出错，ReturnMessagesToClient");
		}
		out.print(returnXML);

		System.out.println(returnXML);
	}

}
