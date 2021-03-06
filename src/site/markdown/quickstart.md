# Quick Start


## Create the project structure

Create the project by calling

    mvn archetype:generate -DarchetypeGroupId=com.github.mwmahlberg.speedy \ 
    -DarchetypeArtifactId=speedy-archetype -DarchetypeVersion=0.1.1 \ 
    -DgroupId=org.example.webapp -DartifactId=ExampleApp -Dversion=0.0.1-SNAPSHOT \ 
    -DinteractiveMode=false

After the command finished, the following files and directories are created

    ExampleApp/
    ├── pom.xml
    └── src
        └── main
            └── java
                └── org
                    └── example
                        └── webapp
                            ├── App.java
                            ├── config
                            │   └── AppConfig.java
                            └── controller
                                └── Index.java

## Inspect the autogenerated controller

Let's first have a look at `Index.java`:

    package org.example.webapp.controller;

    import javax.ws.rs.GET;
    import javax.ws.rs.Path;

    @Path("/")
    public class Index {

            @GET
            public String sayHello() {
                    return "Hello, Speedyworld!";
            }
    }

The annotation `@Path` determines which path the controller is mapped to. So in this case, the controller is reponsible for the root of the web application.
The `@GET` annotation causes the method `sayHello()` to be called when the path is called with the [HTTP request method GET][httpget]. The method returns a [String][jlstring], which is returned as is. So when the root of your web application is called, `"Hello, Speedyworld!"` is shown in the browser.

Now, let us

## Run the server

First, we need to compile our project and create an executable JAR which contains all dependencies:

    cd ExampleApp
    mvn clean package shade:shade

Maven will create `ExampleApp-0.0.1-SNAPSHOT.jar` in the `target` directory. Now, we can run our app with

    java -jar target/ExampleApp-0.0.1-SNAPSHOT.jar

When the server has fired up, you can open http://localhost:8080/ in your browser and get a nice greeting!

[httpget]: http://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol#Request_methods
[jlstring]: http://docs.oracle.com/javase/6/docs/api/java/lang/String.html