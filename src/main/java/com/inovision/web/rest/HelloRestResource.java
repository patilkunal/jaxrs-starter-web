package com.inovision.web.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.inovision.web.beans.User;

@Path("/hello")
public class HelloRestResource {

	@GET
	@Path("/{name}")
	public Response sayHello(@PathParam("name") String name) {
		return Response.ok("Welcome " + name).build(); 
	}


	@GET
	@Path("/user/{name}")
	public User getUser(@PathParam("name") String name) {
		return new User(name); 
	}
}

