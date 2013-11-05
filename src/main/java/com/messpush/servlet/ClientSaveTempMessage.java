package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.messpush.controller.service.TempMessageService;
import com.messpush.model.bean.TempMessage;

/**
 *客户端提交待审核信息
 */
@WebServlet("/ClientSaveTempMessage")
public class ClientSaveTempMessage extends HttpServlet {
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
		//创建一个待审核信息服务对象
		TempMessageService tmpMsgService = new TempMessageService(request);
		TempMessage tempMessage = new TempMessage();
		tempMessage.setTitle(request.getParameter("title"));
		tempMessage.setPerson(request.getParameter("person"));
		tempMessage.setTel(request.getParameter("tel"));
		tempMessage.setSerialNumber(request.getParameter("new_mess_serial"));
		tempMessage.setMessage(request.getParameter("content"));
		//设置时间
		tempMessage.setTime(new Timestamp(new Date().getTime()));
		returnXML = tmpMsgService.saveTempMessage(tempMessage);
		out.print(returnXML);
		System.out.println(returnXML);
	}

}
