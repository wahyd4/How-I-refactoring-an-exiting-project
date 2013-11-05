package com.messpush.model.dao;

import com.messpush.model.bean.Setting;

/**
 * 主要包含获取和更新系统设置的相关信息
 * @author Junv
 *
 */
public interface SettingDao {
	
	/**
	 * 获取设置信息
	 * @return Setting 对象
	 */
	public Setting getSetting();
	
	/**
	 * 更新设置的信息
	 * @param setting
	 * @return   Setting
	 */
	public Setting updateSetting(Setting setting);
	
	/**
	 * 通过id获取设置信息<br>
	 * 同时也可以作为检测相关操作是否成功的标志
	 * @param id
	 * @return  Setting
	 */
	public Setting   getSettingById(Integer id);

}
