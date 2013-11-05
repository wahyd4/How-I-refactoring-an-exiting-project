package com.messpush.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.messpush.controller.service.MessageService;
import com.messpush.model.bean.Message;

/**
 * 保存一条消息
 */
@WebServlet("/SaveMessage")
public class SaveMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveMessage() {
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
		HttpSession session = request.getSession();
		//获取存在session中的userid
		Integer userid = (Integer) session.getAttribute("userid");
		//将页面数据装入对象
		Message msg = new Message();
		msg.setMessage(request.getParameter("content"));
		//类别信息
		msg.setCategoryId(Integer.parseInt(request.getParameter("cate")));
		msg.setTitle(request.getParameter("title"));
		msg.setPreson(request.getParameter("person"));
		msg.setTelephone(request.getParameter("tel"));
		msg.setTags("none");
		//msg.setTime(new Timestamp(System.currentTimeMillis()));  //设置时间
		msg.setUserId(userid);  //从session中获取的信息
		//实例化信息服务对象
		MessageService  ms = new MessageService(request);
	   String returnXML = ms.saveMessage(msg);
	   System.out.print("xml"+returnXML);
	   out.print(returnXML);
	   
	}

}
