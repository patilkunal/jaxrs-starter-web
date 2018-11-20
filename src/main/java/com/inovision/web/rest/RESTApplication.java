/**
 * 
 */
package com.inovision.web.rest;

import java.util.Set;

import javax.servlet.annotation.MultipartConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author kunpatil
 *
 */
@ApplicationPath("/rest")
@MultipartConfig(location="/temp", maxFileSize=35000000L, maxRequestSize=218018841L, fileSizeThreshold=30000)
public class RESTApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> resources = new java.util.HashSet<>();
		resources.add(HelloRestResource.class);
		resources.add(FileUploadResource.class);
		resources.add(UserDetailResource.class);
		return resources;
	}
	
}
