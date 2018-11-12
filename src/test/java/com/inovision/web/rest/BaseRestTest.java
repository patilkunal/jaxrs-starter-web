package com.inovision.web.rest;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.glassfish.embeddable.Deployer;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;
import org.glassfish.embeddable.archive.ScatteredArchive;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

public class BaseRestTest {
	
	private static final Logger LOG = Logger.getLogger(BaseRestTest.class.getCanonicalName());
	
	private static GlassFish glassfish = null;
	private static final String WEB_APP_NAME = "starter-web";
	private static final String BASE_URI = "http://localhost:" + 8080 + "/" + WEB_APP_NAME;
	protected static final String REST_URI = BASE_URI + "/rest";
	

	@BeforeClass
	public static void startServer() {
	    try {
	        GlassFishProperties gfProps = new GlassFishProperties();
	        gfProps.setPort("http-listener", 8080); // NB: not sure where name comes from - a standard property?

	        glassfish = GlassFishRuntime.bootstrap().newGlassFish(gfProps);
	        glassfish.start();

	        Deployer deployer = glassfish.getDeployer();
	        ScatteredArchive archive = new ScatteredArchive(WEB_APP_NAME, ScatteredArchive.Type.WAR);
	        File buildDir = new File("/Users/kunpatil/projects/patilkunal/jaxrs-starter-web/target", "classes");         // NB: location depends on IDE setup
	        archive.addClassPath(buildDir);
	        deployer.deploy(archive.toURI());
	    } catch (GlassFishException ex) {
	        LOG.error(ex);
	    } catch (IOException ex) {
	        LOG.error(ex);
	    }
	}

	@AfterClass
	public static void shutDownServer() {
	    if (glassfish != null) {
	        try {
	            glassfish.stop();
	            glassfish.dispose();
	            glassfish = null;
	        } catch (GlassFishException ex) {
	            LOG.error("tearDownClass(): exception: ", ex);
	        }
	    }
	}	
	
	protected String doGet(String uri) throws Exception {
	    URL url = new URL(uri);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.connect();

	    InputStream inputStream = conn.getInputStream();
	    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
	    return br.readLine();
	}
}
