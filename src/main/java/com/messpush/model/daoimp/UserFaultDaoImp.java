package com.messpush.model.daoimp;

import java.util.ArrayList;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.messpush.model.bean.UserFault;
import com.messpush.model.dao.UserFaultDao;

public class UserFaultDaoImp extends HibernateDaoSupport implements UserFaultDao{

	@Override
	public UserFault saveUserFault(UserFault usrFault) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserFault updateUserFault(UserFault usrFault) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<UserFault> selectUserFaults(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserFault deleteUserFault(Long usrFaultId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserFault checkUserFaultExist(Long usrFaultId) {
		// TODO Auto-generated method stub
		return null;
	}

}
