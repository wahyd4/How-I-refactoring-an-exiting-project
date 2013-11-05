package com.messpush.controller.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.messpush.constant.Constant;
import com.messpush.controller.factory.HibernateDaoFactory;
import com.messpush.model.bean.Serials;
import com.messpush.model.dao.SerialDao;
import com.messpush.tool.MyTools;

/**
 * 一个激活码相关操作的具体实现类，返回xml
 * 
 * @author Junv
 * 
 */
public class SerialService {
	 
	HttpServletRequest request;
	/**
	 * 实例化request对象
	 * @param request
	 */
	public  SerialService(HttpServletRequest request){
		this.request = request;
	}

	/**
	 * 生成并保存一个激活码到数据库中<br>
	 * 同时返回字符串
	 * 
	 * @param count
	 *            需要生成的激活码数量
	 * @return xml数据，包含生成的激活码号码
	 */
	public String generatorAndSaveSerial(int count) {

		// 保存激活码
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		sb.append("<status>true</status>");
		sb.append("<serials size='" + count + "'>");
		for (int i = 0; i < count; i++) {
			Serials ser = new Serials();
			// 新建一个日期，默认提供30天的使用时间
			Calendar cl = Calendar.getInstance();
			cl.add(Calendar.DAY_OF_YEAR, Constant.DEFAULTFREEDAYS);
			Date availTo = new Date(cl.getTimeInMillis());
		
			//生成随机字符串，默认长度为10
			ser.setSerialNumber(MyTools.getRandomString(10));
			ser.setBolcked("false");
			ser.setSerialIsUsed("false");
			ser.setAvailibleTo(availTo);
			//实例化工厂类，并获取serialDao
			HibernateDaoFactory factory = new HibernateDaoFactory(request);
			SerialDao serDao = factory.getSerialDao();
			Serials retObj = serDao.saveSerial(ser);

			if (retObj == null) {
				// 如果任意一个激活码出错立即返回
				return "<result><status>false</status></result>";
			} else {
				// 将新产生的号码构建入并返回
				sb.append("<serialitem>");
				sb.append("<availto>" + retObj.getAvailibleTo() + "</availto>");
				sb.append("<blocked>" + retObj.getBolcked() + "</blocked>");
				sb.append("<serialnumber>" + retObj.getSerialNumber()
						+ "</serialnumber>");
				sb.append("<id>" + retObj.getId() + "</id>");
				sb.append("<used>" + retObj.getSerialIsUsed() + "</used>");
				sb.append("</serialitem>");
			}
		}
		sb.append("</serials>");
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 更新一个激活码的相关信息 并返回xml
	 * 
	 * @param ser
	 * @return String
	 */
	public String updateSerial(Serials ser) {
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//实例化工厂类，并获取serialDao
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		SerialDao serDao = factory.getSerialDao();
		// 更改相关信息
		Serials temp = serDao.getSerialById(ser.getId());
		System.out.println(temp.getAvailibleTo());
		temp.setAvailibleTo(ser.getAvailibleTo());
		temp.setBolcked(ser.getBolcked());
		// 两个GUID均不允许修改
		// temp.setComputerGUID(ser.getComputerGUID());
		// temp.setMobileGUID(ser.getMobileGUID());
		temp.setSerialIsUsed(ser.getSerialIsUsed());
		// 激活码不允许修改
		// temp.setSerialNumber(ser.getSerialNumber());
		temp.setId(ser.getId());
		Serials retObj = serDao.updateSerial(temp);

		if (retObj == null) {
			sb.append("<value>false</value>");
		} else {
			sb.append("<value>true</value>");

		}

		sb.append("</result>");
		return sb.toString();

	}

	/**
	 * 通过页面编号获取当前页面的所有激活码信息
	 * 
	 * @param page
	 * @return String
	 */
	public String getSerialsByPage(int page) {
		int pageCount = Constant.SINGLEPAGECOUNT;
		String hql = "from Serials order by id desc "
				+ (page * pageCount - 1) + " , " + pageCount;
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//实例化工厂类，并获取serialDao
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		SerialDao serDao = factory.getSerialDao();
		sb.append("<totalcount><value>" + serDao.getCount()
				+ "</value></totalcount>");
		sb.append("<singlepage><value>" + Constant.SINGLEPAGECOUNT
				+ "</value></singlepage>");
		List<Serials> sers = serDao.selectSerials(hql, (page * pageCount - 1),
				pageCount);

		sb.append("<serials size ='" + sers.size() + "'>");
		Iterator<Serials> it = sers.iterator();

		while (it.hasNext()) {
			Serials ser = it.next();
			sb.append("<serialitem>");
			sb.append("<availto>" + ser.getAvailibleTo() + "</availto>");
			sb.append("<blocked>" + ser.getBolcked() + "</blocked>");
			sb.append("<serialnumber>" + ser.getSerialNumber()
					+ "</serialnumber>");
			sb.append("<id>" + ser.getId() + "</id>");
			sb.append("<used>" + ser.getSerialIsUsed() + "</used>");
			sb.append("</serialitem>");

		}
		sb.append("</serials>");
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 通过ID删除一个激活码信息
	 * 
	 * @param id
	 * @return String
	 */
	public String deleteSerial(Long id) {

		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//实例化工厂类，并获取serialDao
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		SerialDao serDao = factory.getSerialDao();
		Serials retObj = serDao.deleteSerial(id);
		if (retObj == null) {

			sb.append("<value>true</value>");
		} else {
			sb.append("<value>false</value>");
		}
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 通过关键字搜索激活码
	 * 
	 * @param keyword
	 * @return xml结果
	 */
	public String getSerialsBySearch(String keyword) {
		int pageCount = Constant.SINGLESERCHCOUNT;
		int page = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		//实例化工厂类，并获取serialDao
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		SerialDao serDao = factory.getSerialDao();
		List<Serials> sers = serDao.searchSerials(keyword,
				(page * pageCount - 1), pageCount);
		sb.append("<totalcount><value>" + sers.size() + "</value></totalcount>");
		sb.append("<singlepage><value>" + Constant.SINGLESERCHCOUNT
				+ "</value></singlepage>");
		sb.append("<serials size ='" + sers.size() + "'>");
		Iterator<Serials> it = sers.iterator();

		while (it.hasNext()) {
			Serials ser = it.next();
			sb.append("<serialitem>");
			sb.append("<availto>" + ser.getAvailibleTo() + "</availto>");
			sb.append("<blocked>" + ser.getBolcked() + "</blocked>");
			sb.append("<serialnumber>" + ser.getSerialNumber()
					+ "</serialnumber>");
			sb.append("<id>" + ser.getId() + "</id>");
			sb.append("<used>" + ser.getSerialIsUsed() + "</used>");
			sb.append("</serialitem>");
		}
		sb.append("</serials>");
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 执行激活码的相关检测<br>
	 * 首先会对激活码进行相应判断，判断是否能激活<br>
	 * 并激活激活码的状态为使用状态
	 * @param serialNumber 激活码
	 * @return String xml字符串
	 */
	public String SerialLogin(String serialNumber) {
		StringBuilder sb = new StringBuilder();
		sb.append("<result>");
		sb.append("<ser>");
		//实例化工厂类，并获取serialDao
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		SerialDao serDao = factory.getSerialDao();
		Serials ser = serDao.getSerialByNumber(serialNumber);
		if (ser == null) {
			sb.append("<state>" + false + "</state>");
			sb.append("<desc>该激活码不存在，请更改激活码！</desc>");
		} else {
			// 如果激活码存在则继续判断
		      if (ser.getBolcked().equals("true")) {
				sb.append("<state>" + false + "</state>");
				sb.append("<desc>该激活码已被封锁,请更改激活码！</desc>");
			} else if (ser.getAvailibleTo().getTime() < new Date(
					System.currentTimeMillis()).getTime()) {
				sb.append("<state>" + false + "</state>");
				sb.append("<desc>该激活码已过期,请更改激活码！</desc>");
			} else {
				sb.append("<state>" + true + "</state>");
				sb.append("<desc>该激活码可以正常使用！已登录</desc>");
				//向session中和Application存入数据
				this.writeIntoSession(serialNumber);
			}
		}
		sb.append("</ser>");
		sb.append("</result>");
		return sb.toString();
	}
	
	/**
	 * 将用户登录信息写入session中
	 * @param serialNumber
	 */
	public void writeIntoSession(String serialNumber){
		HttpSession session = request.getSession();
		ServletContext  application = session.getServletContext();
		//尝试从session中读取值,键为激活码
		String sessionId = (String) application.getAttribute(serialNumber);
		//说明没有使用该激活码进行请求的操作
		//以激活码为键，sessionId为值，存入其中
		if(sessionId==null){
			//在后面统一写入信息
		}else{
			//说明已经有激活码的记录，因此需要判断是否是该用户本身
			if(sessionId.equals(session.getAttribute(serialNumber))){
				//为该用户本身。什么都不做
				return;
			}
		}
		application.setAttribute(serialNumber, session.getId());
		session.setAttribute("serial", serialNumber);
		System.out.println("这里是设置session"+session.getAttribute("serial"));
	}
	/**
	 * 检查激活码的状态,判断该激活码是否可以使用
	 * @param serialNumber
	 * @return 如果激活码为可用的返回为true
	 */
	public boolean checkSerialIsAvailable(String  serialNumber){
	
		//实例化工厂类，并获取serialDao
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		SerialDao serDao = factory.getSerialDao();
		Serials ser = serDao.getSerialByNumber(serialNumber);
		if (ser == null) {
			return false;
		} else {
			// 如果激活码存在则继续判断
		    if (ser.getBolcked().equals("true")) {
				return false;
			} else if (ser.getAvailibleTo().getTime() < new Date(
					System.currentTimeMillis()).getTime()) {
		      return false;
			} 
		}
     //排除错误的情况，其他返回true
		return true;
	}
	
	/**
	 * 通过判断其session中是否有 键"serial"的值，判断其是否已经登录
	 * @return String xml
	 */
	public String checkIfLogedBySerial( ){
		StringBuilder sb  = new StringBuilder();
		sb.append("<result>");
		HttpSession session = request.getSession();
		System.out.println("查看一下session"+session.getAttribute("serial"));
		if(session.getAttribute("serial")!=null){
			sb.append("<value>true</value>");
		}else{
			sb.append("<value>false</value>");
		}
		sb.append("</result>");
		return sb.toString();
	}

}
