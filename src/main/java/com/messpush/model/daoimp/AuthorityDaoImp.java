package com.messpush.model.daoimp;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.messpush.model.bean.Authority;
import com.messpush.model.dao.AuthorityDao;

/**
 * 权限接口方法的实现
 * @author Junv
 *
 */
public class AuthorityDaoImp extends HibernateDaoSupport implements AuthorityDao {

	@Override
	public List<Authority> getTopAuthoritys() {
		//顶级权限的parent属性为0
		@SuppressWarnings("unchecked")
		List<Authority> topAuthoritys = this.getHibernateTemplate().find("from Authority where parent = 0");
		return topAuthoritys;
	}

	@Override
	public List<Authority> getSonAuthoritysById(Integer authorityId) {
		//通过输入某个顶级权限的ID，获取改顶级权限的所有子权限
		@SuppressWarnings("unchecked")
		List<Authority> sonAuthoritys = this.getHibernateTemplate().find("from Authority where parent = ?",authorityId);
		return sonAuthoritys;
	}

	@Override
	public Authority getAuthorityById(Integer authorityId) {
		@SuppressWarnings("unchecked")
		List<Authority> authority = this.getHibernateTemplate().find("from Authority where authorityId = ?",authorityId);
		 //如果返回有对象，应该只会有一个对象，所以获取其第1个
		if(authority.size()>0){
			return authority.get(0);
		}
		return null;
	}

	@Override
	public Authority getAuthorityByName(String authorityName) {
		@SuppressWarnings("unchecked")
		List<Authority> authority = this.getHibernateTemplate().find("from Authority where authorityName = ?",authorityName);
		 //如果返回有对象，应该只会有一个对象，所以获取其第1个
		if(authority.size()>0){
			return authority.get(0);
		}
		return null;
	}

	@Override
	public List<Authority> getAllAuthoritys() {
		@SuppressWarnings("unchecked")
		List<Authority> authoritys = this.getHibernateTemplate().find("from Authority");
		return authoritys;
	}

}
