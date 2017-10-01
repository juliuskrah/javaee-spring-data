# Spring Data with EE7
Integrating Spring Data into a Java EE Application. This project demonstrates using Spring Data standalone
in a Java EE container without the `ApplicationContext` interface of Spring:

<http://juliuskrah.com/blog/2017/08/20/setting-up-spring-data-in-a-java-ee-environment/>

## Quick Run
```bash
$ git clone https://github.com/juliuskrah/javaee-spring-data.git
$ cd javaee-spring-data
$ ./mvnw clean package
$ java -jar target\javaee-spring-data-swarm.jar # Windows
$ java -jar target/*.jar                        # Linux and Mac
```
