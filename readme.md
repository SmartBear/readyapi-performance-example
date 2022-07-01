# ReadyAPI performance target server

This is a simple spring-boot HTTP server that is used as a target for ReadyAPI performance testing.


# Build
Build requirements

```
 - java17  
 - maven 3.8.x+
```

Build

```sh
$ mvn clean install
```


## Run
```sh
$ java [-Dserver.port=portnumber] -jar perf-target-0.0.1.jar
```

*default port is **8080***

##  Endpoints
OpenAPI definition
 ```
 /swagger-ui/index.html#/
 ```

# ReadyAPIPerf Test project

```
ReadyAPIPerfTest.xml
```
A sample project to run basic ReadyAPI performance tests. It comes preconfigured with a public test target server that runs the code in this project.

## 