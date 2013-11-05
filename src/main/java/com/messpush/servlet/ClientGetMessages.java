package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.NDC;

import com.messpush.controller.service.MessageService;
import com.messpush.controller.service.SerialService;

/**
 * 为客户端提供数据
 */
@WebServlet("/ClientGetMessages")
public class ClientGetMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Log logger = LogFactory.getLog(getClass());

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 设置所有客户端均可以访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String returnXML = null;
		System.out.println("马涛穿过来的" + request.getQueryString());
		// 获取最新的信息ID
		Long messageId = Long.parseLong(request.getParameter("msgid"));
		// 获取激活码
		String serialNumber = request.getParameter("serial");
		// 如果没有传参或者参数为空，直接返回
		if (serialNumber.equals("null") || serialNumber == null
				|| messageId == null) {
			return;
		}
		// 实例化激活码服务
		SerialService serialService = new SerialService(request);
		// 如果激活码验证不通过则直接输出错误信息并返回
		if (!serialService.checkSerialIsAvailable(serialNumber)) {
			returnXML = "<result><value>false</value><state>激活码错误！</state><messages></messages></result>";
			out.print(returnXML);
			System.out.println(returnXML);
			return;
		}
		HttpSession session = (HttpSession) request.getSession();
		ServletContext application = (ServletContext) session
				.getServletContext();
		/*
		 * 如果在application已经存在该激活码的信息，说明有两个以上的人在使用改激活码， 则将弹出另外一个正在使用改激活码当用户
		 */
		if (application.getAttribute(serialNumber.toString()) != null
				&& application.getAttribute(serialNumber.toString()) != session
						.getId()) {

			// 将该激活码送到LOG4J中
			NDC.push(serialNumber.toString());
			// 获取log4j对象
			logger.warn("激活码" + serialNumber + "有多人使用,Ip为"
					+ request.getRemoteHost());
			// 清除session
			session.removeAttribute("serial");
			returnXML = "<result><value>false</value><state>该账号已在其他地方登录！</state><messages></messages></result>";
			out.print(returnXML);
			System.out.println(returnXML);
			return;
		}
		// 实例化信息服务对象
		MessageService ms = new MessageService(request);
		if (messageId == (long) 0) {
			returnXML = ms.getMessagesByPageASC();
		} else {
			returnXML = ms.getMessagesBehindMsgId(messageId);
		}

		out.print(returnXML);
		System.out.println(returnXML);
	}

}
