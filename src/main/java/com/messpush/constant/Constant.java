package com.messpush.constant;

import javax.servlet.http.HttpServletRequest;

import com.messpush.controller.factory.HibernateDaoFactory;
import com.messpush.model.bean.Setting;
import com.messpush.model.dao.SettingDao;

/**
 * 为了是常量的值是从数据库中读取的<br>
 * 需要在用户登录的时候对该类进行实例化
 * @author Junv
 *
 */
public class Constant {
	/**
	 * 从数据库中获取每页显示信息的条数<br>
	 * 如数据库读取失败，显示默认的20条<br>
	 * 定义单个页面显示的数据量为20条
	 */
	public static  int SINGLEPAGECOUNT = 20;
	/**
	 * 从数据库中读取默认的免费使用时间<br>
	 * 若读取失败，将显示默认30条<br>
	 * 为每个初始激活的账号默认提供30天的使用时间
	 */
	public static  int  DEFAULTFREEDAYS = 30;

	/**
	 * 从数据库中获取搜索结果显示的条数<br>
	 * 如果获取失败将默认最大显示100条<br>
	 * 设置默认搜索时最大显示结果数量
	 */
	public static  int SINGLESERCHCOUNT = 100;
	
	/**
	 * 从数据库中获取客户端首次打开应用获取的信息数量<br>
	 * 若从数据库获取失败，将默认显示40条  <br>
	 * 设置客户端首次开大应用时获取到的信息数量
	 */
	public static  int FIRSTPUSHCOUNTTOCLIENT = 40;
	
	/**
	 * 我们用户登录的时候进行常量的实例化。
	 * @param request
	 */
	public Constant(HttpServletRequest request) {
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		SettingDao settingDao =  factory.getSettingDao();
		Setting  set = settingDao.getSetting();
		//将从数据库中获取的值取出并为其赋值
		SINGLEPAGECOUNT = set.getSinglepageCount();
		DEFAULTFREEDAYS = set.getDefaultFreeDay();
		SINGLESERCHCOUNT = set.getSearchCount();
		FIRSTPUSHCOUNTTOCLIENT = set.getFirstPushToClientCount();
		
	}

}
