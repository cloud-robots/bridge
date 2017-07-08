# cloud-robots.bridge.server

Bridge Server to run in any host

# Usage

```shell
$ java -jar cloud-robots.bridge.server-0.0.1-SNAPSHOT.jar
```

The server use a in memory H2 database that can be accessed thought the following url:

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

The default values to use are:
- Driver Class : org.h2.Driver
- JDBC URL: jdbc:h2:mem:serverDB
- User Name: sa
- Password: [blank]

This can be change in the application.yml
