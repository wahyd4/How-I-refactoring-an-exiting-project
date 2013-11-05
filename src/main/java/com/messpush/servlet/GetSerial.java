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
 * 生成并得到激活码
 */
@WebServlet("/GetSerial")
public class GetSerial extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSerial() {
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
		//实例化一个激活码服务
		SerialService ss = new SerialService(request);
		String returnXML = null;
	    /**
	     * 	获取需要生成的激活码数量
	     */
		int count = Integer.parseInt(request.getParameter("serial_count"));
		 
			returnXML = ss.generatorAndSaveSerial(count);
		
		
		System.out.println("ret"+returnXML);
		out.print(returnXML);
	}

}
