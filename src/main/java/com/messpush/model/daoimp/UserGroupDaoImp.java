package com.messpush.model.daoimp;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.messpush.model.bean.UserGroup;
import com.messpush.model.dao.UserGroupDao;

/**
 * 用户类别DAO的具体实现方法
 * @author Junv
 *
 */
public class UserGroupDaoImp extends HibernateDaoSupport implements UserGroupDao  {

	@Override
	public UserGroup getUserGroupById(Integer groupId) {
		@SuppressWarnings("unchecked")
		List<UserGroup> groups = this.getHibernateTemplate().find("from UserGroup where groupId = ?",groupId);
	   //如果存在结果说明已经取到我们需要的值
		if(groups.size()>0){
			return groups.get(0);
		}
		return null;
	}

	@Override
	public List<UserGroup> getAllUserGroups() {
		//获取所有用户类别
		@SuppressWarnings("unchecked")
		List<UserGroup> groups = this.getHibernateTemplate().find("from UserGroup");
		return groups;
	}

}
