package com.inovision.web.rest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestHelloRest extends BaseRestTest {

	//@Test
	public void testHelloWorld() throws Exception {
		String uri = REST_URI + "/hello/world";
		String response = doGet(uri);
	    assertEquals("Welcome world", response);
	}
	
}
