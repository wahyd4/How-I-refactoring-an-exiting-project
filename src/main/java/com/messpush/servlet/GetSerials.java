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
 * 通过页面编号获取若干个激活码信息
 */
@WebServlet("/GetSerials")
public class GetSerials extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSerials() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doPost(request,response);
	}

   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		//实例化激活码服务对象
		SerialService ss = new SerialService(request);
		String returnXML = null;
		int page = Integer.parseInt(request.getParameter("page"));
		returnXML = ss.getSerialsByPage(page);
		
		System.out.println(returnXML);
		out.print(returnXML);
	}

}
