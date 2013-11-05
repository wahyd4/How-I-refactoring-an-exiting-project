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
 *检查激活码的状态
 */
@WebServlet("/CheckSerialState")
public class CheckSerialState extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckSerialState() {
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
		//得到SerialService 服务对象
		SerialService ss = new SerialService(request);
		String returnXML = null;
		String serialNumber  = null;
		serialNumber =request.getParameter("serial");
	
		//激活激活码，其中会先对激活码进行相应判断
		returnXML = ss.SerialLogin(serialNumber);
		out.print(returnXML);
		System.out.println(returnXML );
	}

}
