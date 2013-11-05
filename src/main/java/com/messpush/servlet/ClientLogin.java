package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.messpush.controller.service.SerialService;

/**
 * 用户客户端通过激活码进行登录
 */
@WebServlet("/ClientLogin")
public class ClientLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 得到SerialService 服务对象
		SerialService ss = new SerialService(request);
		String returnXML = null;
		String serialNumber = null;

		serialNumber = request.getParameter("serial");

		// 使用激活码进行客户端登录
		returnXML = ss.SerialLogin(serialNumber);
		out.print(returnXML);
		System.out.println(returnXML);
	}

}
