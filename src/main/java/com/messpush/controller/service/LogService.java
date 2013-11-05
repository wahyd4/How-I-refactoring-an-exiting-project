package com.messpush.controller.service;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.messpush.constant.Constant;
import com.messpush.controller.factory.HibernateDaoFactory;
import com.messpush.model.bean.Log;
import com.messpush.model.dao.LogDao;

/**
 * 定义日志记录的相关封装方法，并返回xml
 * @author Junv
 *
 */
public class LogService {
	HttpServletRequest request;
	/**
	 * 实例化日志服务，需要HttpServletRequest 对象
	 * @param request
	 */
	public LogService(HttpServletRequest request){
		this.request = request;
	}
	
	/**
	 * 通过指定的页面返回日志记录数据
	 * @param page
	 * @return
	 */
	public String getLogsByPage(int page){
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//实例化获取 HibernateDaoFactory 
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		LogDao logDao = factory.getLogDao();
		String hql = "from Log order by id desc";
		//获取常量
		//获取需要获取的当前的数据起始值
		Integer start = (Constant.SINGLEPAGECOUNT*page-1);
		//获取所有的日志记录，在执行前台分页的时候需要该记录
		sb.append("<totalcount><value>"+logDao.getCount()+"</value></totalcount>");
		  sb.append("<singlepage><value>"+Constant.SINGLEPAGECOUNT+"</value></singlepage>");
		List<Log> logs = logDao.selectLogsByPage(hql, start, Constant.SINGLEPAGECOUNT);
		//获取返回的日志信息的条数
		sb.append("<logs size='"+logs.size()+"'>");
		Iterator<Log> it = logs.iterator();
		//执行遍历
		while(it.hasNext()){
			Log  temp = (Log) it.next();
			sb.append("<logitem>");
			sb.append("<id>"+temp.getLogId()+"</id>");
			sb.append("<target>"+temp.getTarget()+"</target>");
			sb.append("<loglevel>"+temp.getLogLevel()+"</loglevel>");
			sb.append("<logdesc>"+temp.getLogDesc()+"</logdesc>");
			sb.append("<time>"+temp.getTime()+"</time>");
			sb.append("</logitem>");
		}
		sb.append("</logs>");
		sb.append("</result>");
		return sb.toString();
	}

}
