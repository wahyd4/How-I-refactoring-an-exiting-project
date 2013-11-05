package com.messpush.controller.service;

import javax.servlet.http.HttpServletRequest;
import com.messpush.controller.factory.HibernateDaoFactory;
import com.messpush.model.bean.Setting;
import com.messpush.model.dao.SettingDao;

/**
 * 定义设置信息的相关操作的封装集合
 * 
 * @author Junv
 * 
 */
public class SettingService {
	private HttpServletRequest request = null;

	/**
	 * 实例化SettingService，需要传入HttpServletRequest 对象
	 * 
	 * @param request
	 */
	public SettingService(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 获取系统设置信息
	 * 
	 * @return String xml
	 */
	public String getSettingInfo() {
		StringBuilder sb = new StringBuilder();
		// 实例化工厂类，并获取SettingDao
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		SettingDao settingDao = factory.getSettingDao();
		Setting set = settingDao.getSetting();
		sb.append("<result>");
		sb.append("<setting>");
		sb.append("<id>" + set.getId() + "</id>");
		sb.append("<appname>" + set.getAppName() + "</appname>");
		sb.append("<icp>" + set.getIcp() + "</icp>");
		sb.append("<copyright>" + set.getCopyright() + "</copyright>");
		sb.append("<logo>" + set.getLogo() + "</logo>");
		sb.append("<singlepagecount>" + set.getSinglepageCount()
				+ "</singlepagecount>");
		sb.append("<searchcount>" + set.getSearchCount() + "</searchcount>");
		sb.append("<defaultfreeday>" + set.getDefaultFreeDay()
				+ "</defaultfreeday>");
		sb.append("<firstpushtoclientcount>" + set.getFirstPushToClientCount()
				+ "</firstpushtoclientcount>");
		sb.append("</setting>");
		sb.append("</result>");
		return sb.toString();
	}

	/**
	 * 更新设置信息
	 * 
	 * @param setting
	 * @return String xml文档
	 */
	public String updateSettingInfo(Setting setting) {
		StringBuilder sb = new StringBuilder();
		// 实例化工厂类，并获取SettingDao
		HibernateDaoFactory factory = new HibernateDaoFactory(request);
		SettingDao settingDao = factory.getSettingDao();
		Setting set = settingDao.updateSetting(setting);
		sb.append("<result>");
		if (set == null) {
			sb.append("<value>false</value>");
		} else {
			sb.append("<value>true</value>");
			sb.append("<setting>");
			sb.append("<id>" + set.getId() + "</id>");
			sb.append("<appname>" + set.getAppName() + "</appname>");
			sb.append("<icp>" + set.getIcp() + "</icp>");
			sb.append("<copyright>" + set.getCopyright() + "</copyright>");
			sb.append("<logo>" + set.getLogo() + "</logo>");
			sb.append("<singlepagecount>" + set.getSinglepageCount()
					+ "</singlepagecount>");
			sb.append("<searchcount>" + set.getSearchCount() + "</searchcount>");
			sb.append("<defaultfreeday>" + set.getDefaultFreeDay()
					+ "</defaultfreeday>");
			sb.append("<firstpushtoclientcount>"
					+ set.getFirstPushToClientCount()
					+ "</firstpushtoclientcount>");
			sb.append("</setting>");
		}
		sb.append("</result>");
		return sb.toString();
	}
}
