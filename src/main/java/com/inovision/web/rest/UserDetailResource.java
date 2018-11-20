package com.inovision.web.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;

import com.inovision.dao.UserDetailDAO;
import com.inovision.ejb.entity.UserDetail;

@Path("/userdetail")
public class UserDetailResource {

	private static final Logger LOGGER = Logger.getLogger(UserDetailResource.class);
	
	@Inject
	private UserDetailDAO dao;
	
	@GET
	public List<UserDetail> getUserDetailList() {
		LOGGER.info("Hello Welcome API invoked");
		return  dao.getAllUsers();
	}
	
	@POST
	public UserDetail saveUserDetail(UserDetail ud) {
		return dao.saveUserDetail(ud);
	}
	
}
