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

# Local
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/a312a136183a20ea06f3#?env%5Blocal%5D=W3siZW5hYmxlZCI6dHJ1ZSwia2V5Ijoic2VydmVyIiwidmFsdWUiOiJsb2NhbGhvc3Q6ODA4MCIsInR5cGUiOiJ0ZXh0In1d)

# Heroku
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/a312a136183a20ea06f3#?env%5Bheroku%5D=W3siZW5hYmxlZCI6dHJ1ZSwia2V5Ijoic2VydmVyIiwidmFsdWUiOiJodHRwczovL2JyaWNrLWJyaWRnZS5oZXJva3VhcHAuY29tIiwidHlwZSI6InRleHQifV0=)
