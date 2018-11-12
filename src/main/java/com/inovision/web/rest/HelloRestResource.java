package com.inovision.web.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.inovision.web.beans.User;

@Path("/hello")
public class HelloRestResource {
	
	private static final Logger LOGGER = Logger.getLogger(HelloRestResource.class);

	@GET
	@Path("/{name}")
	public Response sayHello(@PathParam("name") String name) {
		LOGGER.info("Hello Welcome API invoked");
		return Response.ok("Welcome " + name).build(); 
	}


	@GET
	@Path("/user/{name}")
	public User getUser(@PathParam("name") String name) {
		LOGGER.info("Hello user API invoked");
		return new User(name); 
	}
}

