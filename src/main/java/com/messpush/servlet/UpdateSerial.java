package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.messpush.controller.service.SerialService;
import com.messpush.model.bean.Serials;

/**
 * 更新一个激活码的相关信息
 */
@WebServlet("/UpdateSerial")
public class UpdateSerial extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateSerial() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		PrintWriter out = response.getWriter();
		//实例化激活码服务对象
		SerialService ss = new SerialService(request);
		String returnXML = null;
		Serials ser = new Serials();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Date availTo = null;
		String date = request.getParameter("avail_to");
		try {
		     availTo =  format.parse(date);
		} catch (ParseException e) {
			System.out.println("日期转码失败");
			e.printStackTrace();
		}
		//将数据转型
		java.sql.Date sqlDate = new java.sql.Date(availTo.getTime());
		//设置相关值
		ser.setAvailibleTo(sqlDate);
		ser.setBolcked(request.getParameter("blocked"));
		ser.setSerialIsUsed(request.getParameter("used"));
		ser.setId(Long.parseLong(request.getParameter("serial_id")));
		returnXML = ss.updateSerial(ser);
		
		out.print(returnXML);
		System.out.println(returnXML);
		
	}

}
