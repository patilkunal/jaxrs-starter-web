package com.inovision.dao;

import java.util.List;

import com.inovision.ejb.entity.UserDetail;

public interface UserDetailDAO {

	public UserDetail getUserDetail(int id);
	public List<UserDetail> getAllUsers();
	public UserDetail saveUserDetail(UserDetail u);
}
