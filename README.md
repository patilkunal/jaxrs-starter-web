# JAXRS Web application template
Web application template using JAXRS

## Hello World REST 
invoke the REST endpoint by curl command:

* curl -v -H "accept: application/json" http://localhost:8080/starter-web/rest/hello/kunal
* curl -v -H "accept: application/json" http://localhost:8080/starter-web/rest/hello/user/kunal

The last one outputs as json object of User POJO

##Multipart config
@MultipartConfig annotation is configured on the REST Application object (see RESTApplication.java)

* curl -v --form upload=@IMG_3131.JPG http://localhost:8080/starter-web/rest/upload


