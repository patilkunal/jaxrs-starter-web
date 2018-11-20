package com.inovision.web.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.glassfish.embeddable.BootstrapProperties;
import org.glassfish.embeddable.Deployer;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;
import org.glassfish.embeddable.archive.ScatteredArchive;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class BaseRestTest {
	
	private static final Logger LOG = Logger.getLogger(BaseRestTest.class.getCanonicalName());
	
	private static GlassFish glassfish = null;
	private static final String WEB_APP_NAME = "starter-web";
	private static final String BASE_URI = "http://localhost:" + 8080 + "/" + WEB_APP_NAME;
	protected static final String REST_URI = BASE_URI + "/rest";
	
	static {
		try {
			startServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		shutDownServer();
	}
	public static void startServer() throws Exception {
	    try {
	    	if(glassfish == null) {
		    	BootstrapProperties bp = new BootstrapProperties();
		    	bp.setInstallRoot("c:/opt/glassfish5");
		        GlassFishProperties gfProps = new GlassFishProperties();
		        URL uri = BaseRestTest.class.getClassLoader().getResource("/domain.xml");
		        File config = new File("file:///Users/kunpatil/projects/patilkunal/jaxrs-starter-web/src/test/resources/domain.xml");
		        //gfProps.setConfigFileURI("file:///opt/glassfish5/glassfish/domains/domain1/config/domain.xml");
		        //gfProps.setPort("http-listener", 8080); // NB: not sure where name comes from - a standard property?
		        gfProps.setInstanceRoot("c:/opt/glassfish5/glassfish/domains/domain1");
		        glassfish = GlassFishRuntime.bootstrap(bp).newGlassFish(gfProps);
		        glassfish.start();
		        Deployer deployer = glassfish.getDeployer();
		        ScatteredArchive archive = new ScatteredArchive(WEB_APP_NAME, ScatteredArchive.Type.WAR);
		        File buildDir = new File("/Users/kunpatil/projects/patilkunal/jaxrs-starter-web/target", "classes");         // NB: location depends on IDE setup
		        archive.addClassPath(buildDir);
		        deployer.deploy(archive.toURI());
		        //Thread.sleep(60000);
	    	} else {
	    		LOG.info("GLASSFISH already started");
	    	}
	    } catch (GlassFishException ex) {
	        LOG.error(ex);
	    } catch (IOException ex) {
	        LOG.error(ex);
	    }
	}

	//@AfterClass
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
	
	protected String doGet(String url) throws Exception {
		/*
	    URL url = new URL(uri);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.connect();

	    InputStream inputStream = conn.getInputStream();
	    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
	    return br.readLine();
	    */
		HttpGet httpRequest = new HttpGet(url);
		httpRequest.addHeader("accept", "application/json");
		//httpRequest.addHeader(authName, authValue);
		return executeRequest(httpRequest);

	}

	protected String doPost(String url, String postdata) throws Exception {
		HttpPost httpRequest = new HttpPost(url);
		httpRequest.addHeader("accept", "application/json");
		httpRequest.addHeader("content-type", "application/json");
		//httpRequest.addHeader(authName, authValue);
		if(postdata != null) {
			try {
				httpRequest.setEntity(new StringEntity(postdata));
			} catch (UnsupportedEncodingException e) {
				LOG.error("Error in setting POST data", e);
			}
		}
		return executeRequest(httpRequest);
	}

	@SuppressWarnings("deprecation")
	protected String executeRequest(HttpRequestBase httpRequest) throws Exception {
		StringBuilder buf = new StringBuilder();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpRequest);
		} catch (Exception e) {
			LOG.error(e + " to " + httpRequest.getURI().toString() );
		}
		if(httpResponse != null) {
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if((statusCode == 200) || (statusCode == 204)) {
				if(httpResponse.getEntity() != null) {
					try {
						InputStream is = httpResponse.getEntity().getContent();
						BufferedReader reader = new BufferedReader(new InputStreamReader(is));
						String str = null;
						while((str=reader.readLine()) != null) {
							buf.append(str);
						}
						reader.close();
					} catch(Exception e) {
						LOG.error("Error trying to read response from back end server", e);
					}
				} else {
					LOG.warn(String.format("Response code : %d  with no response content" , statusCode));
				}
			} else {
				//On any other error throw RestException with return value from call
				LOG.error("Error in getting HTTP Response for : " + httpRequest.getURI() + " : " + httpResponse.getStatusLine());
				httpClient.close();
				throw new Exception("Invalid response from server: " + httpResponse.toString());
			}
		}
		httpClient.close();
		return buf.toString();
	}
}
