package com.inovision.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.inovision.ejb.entity.UserDetail;

@Stateless
public class LocalUserDetailDAO implements UserDetailDAO {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager entityManager;
	
	@Override
	public UserDetail getUserDetail(int id) {		
		return entityManager.find(UserDetail.class, id);
	}
	
	@Override
	public List<UserDetail> getAllUsers() {
		return entityManager.createQuery("from UserDetail", UserDetail.class).getResultList();
	}
	
	@Override
	public UserDetail saveUserDetail(UserDetail u) {
		return entityManager.merge(u);
	}

}
