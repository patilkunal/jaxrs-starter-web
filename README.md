# JAXRS Web application template
Web application template using JAXRS

## Hello World REST 
invoke the REST endpoint by curl command:

* curl -v -H "accept: application/json" http://localhost:8080/starter-web/rest/hello/kunal
* curl -v -H "accept: application/json" http://localhost:8080/starter-web/rest/hello/user/kunal

The last one outputs as json object of User POJO

## Multipart config
@MultipartConfig annotation is configured on the REST Application object (see RESTApplication.java)

* curl -v --form upload=@IMG_3131.JPG http://localhost:8080/starter-web/rest/upload

### UPDATE
Implemented the DAO layer using JPA and Hibernate using embedded H2 database.

Package the war 
* mvn clean package -DskipTests

Deploy to wildfly or your choice of J2EE Server. Use following XML snippet to configure the datasource in Wildfly standalone.xml

```XML
                <datasource jndi-name="java:/jdbc/StarterDS" pool-name="StartedDS" enabled="true" use-java-context="true">
                    <connection-url>jdbc:h2:mem:starterdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
                    <driver>h2</driver>
                    <security>
                        <user-name>sa</user-name>
                        <password>sa</password>
                    </security>
                </datasource>
```

## UNIT Tests
Integration UNIT tests are still working in progress. Trying to deploy the war to embedded Glassfish server. Currently I am stuck on creating JNDI database and facing issue. The persistence xml from test/resources folder need to be used, so need to modify pom.xml. Try the unit tests
* mvn test
You will need to download and unzip Glassfish server 5 to /opt/glassfish5 directory. Edit the domain.xml to have following datasource and related configuration created

```XML
<jdbc-connection-pool is-isolation-level-guaranteed="false" name="TestDB" datasource-classname="org.h2.jdbcx.JdbcDataSource" res-type="javax.sql.DataSource" allow-non-component-callers="false">
       <property name="url" value="jdbc:h2:mem:starterdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"/>
       <property name="user" value="sa"/>
       <property name="password" value="sa"/>
		<property name="driverclass" value="org.h2.Driver" />	   
   </jdbc-connection-pool>
    <jdbc-resource pool-name="TestDB" jndi-name="jdbc/StarterDS" object-type="user" />
    <jdbc-resource pool-name="TestDB" jndi-name="java:/jdbc/StarterDS" object-type="user" />
```

And add following resource-refs to servers/server section
```XML
<resource-ref ref="jdbc/StarterDS"/>	  
	  <resource-ref ref="java:/jdbc/StarterDS"/>	 
```
