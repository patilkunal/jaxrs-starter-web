package com.inovision.web.rest;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inovision.ejb.entity.UserDetail;

public class UserDetailResourceTest extends BaseRestTest {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Test
	public void testSaveUserDetail() throws Exception {
		UserDetail ud = new UserDetail();
		ud.setContactNumber("123");
		ud.setEmail("user1@example.com");
		ud.setEnabled(true);
		ud.setFirstName("User");
		ud.setLastName("First");
		ud.setRole("USER");
		
		String uri = REST_URI + "/userdetail";
		String response = doPost(uri, MAPPER.writeValueAsString(ud));
	    System.out.println(response);
		
	}
}
