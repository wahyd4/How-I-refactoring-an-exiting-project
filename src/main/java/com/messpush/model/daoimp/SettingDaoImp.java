package com.messpush.model.daoimp;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.messpush.model.bean.Setting;
import com.messpush.model.dao.SettingDao;

public class SettingDaoImp  extends HibernateDaoSupport implements SettingDao{

	@Override
	public Setting getSetting() {
		Setting setting = null;
		@SuppressWarnings("unchecked")
		List<Setting> settings = this.getHibernateTemplate().find("from Setting");
		if(settings.size()>0){
			setting = settings.get(0);
		}
		return setting;
	}

	@Override
	public Setting updateSetting(Setting setting) {
		this.getHibernateTemplate().update(setting);
		return  this.getSettingById(setting.getId());
	}
	@Override
	public Setting getSettingById(Integer id) {
		@SuppressWarnings("unchecked")
		List<Setting> settings = this.getHibernateTemplate().find("from Setting where id =?",id);
		if(settings.size()>0){
			return settings.get(0);		
		}
		return null;
	}

}
