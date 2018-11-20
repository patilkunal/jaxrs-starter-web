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

## UNIT Tests
Integration UNIT tests are still working in progress. Trying to deploy the war to embedded Glassfish server. Currently I am stuck on creating JNDI database and facing issue. The persistence xml from test/resources folder need to be used, so need to modify pom.xml. Try the unit tests
* mvn test
