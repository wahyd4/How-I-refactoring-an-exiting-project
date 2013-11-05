package com.messpush.model.daoimp;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.messpush.model.bean.GroupToAuthority;
import com.messpush.model.dao.GroupToAuthorityDao;

/**
 * 用户组权限DAO的方法实现
 * @author Junv
 *
 */
public class GroupToAuthorityDaoImp extends HibernateDaoSupport implements GroupToAuthorityDao {

	@Override
	public GroupToAuthority getAuthorityByGroupId(Integer groupId) {
	    @SuppressWarnings("unchecked")
		List<GroupToAuthority> authoritys = this.getHibernateTemplate().find("from GroupToAuthority where groupId = ? ",groupId);
	    if(authoritys.size()>0){
	    	return  authoritys.get(0);
	    }
		return null;
	}

	@Override
	public GroupToAuthority saveGoupAuthority(GroupToAuthority authoritys) {
		this.getHibernateTemplate().save(authoritys);
		//验证保存是否成功
		return this.getAuthorityByGroupId(authoritys.getGroupId());
	}

	@Override
	public GroupToAuthority updateGroupAuthority(GroupToAuthority authoritys) {
		this.getHibernateTemplate().update(authoritys);
		//验证保存是否成功
		return this.getAuthorityByGroupId(authoritys.getGroupId());
	}

}
