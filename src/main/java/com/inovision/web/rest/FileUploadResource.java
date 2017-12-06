package com.inovision.web.rest;

import java.io.File;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/upload")
public class FileUploadResource {

	@POST
	@Path("/myfile")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(@Context HttpServletRequest request) {
		JsonBuilderFactory fac = Json.createBuilderFactory(null);
        JsonArrayBuilder array = fac.createArrayBuilder();
        JsonObject ret = null;
        try {
        	System.out.println("Parts size: " + request.getParts().size());
            for (Part part : request.getParts()) {
                String name = null;
                long size = 0;
                try {
//                    if (part.getContentType() == null
//                            || !part.getContentType().toLowerCase()
//                                    .startsWith("image/"))
//                        continue;
                	if(part != null) {
	                    name = part.getSubmittedFileName() != null ? part.getSubmittedFileName() : part.getName();
	                    size = part.getSize();
	
	                    array.add(addFile(name, size, "anId"));
	                    //part.delete();
                	}
                } catch (Exception e) {
                    array.add(addError(name, size, "ERROR"));
                }
            }
            //array.add(addFile(file.getName(), csize, "manual add"));
            ret = fac.createObjectBuilder().add("size2", 25).add("size1", (request.getParts().size())).add("files", array).build();
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }

        return Response.status(201).entity(ret).build();		
	}
	
    private JsonObjectBuilder addFile(String name, long size, String url) {
        return Json.createObjectBuilder().add("name", name).add("size", size)
                .add("lid", url);
    }

    private JsonObjectBuilder addError(String name, long size, String error) {
        return Json.createObjectBuilder().add("name", name).add("size", size)
                .add("error", error);
    }	
}
