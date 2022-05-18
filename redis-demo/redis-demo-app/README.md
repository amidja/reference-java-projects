### Implement Hibernate 2nd level Cache with Redis, Spring Boot, and Spring Data JPA

Implement Hibernate 2nd level Cache with Redis, Spring Boot, and Spring Data JPA. Please checkout my [medium article](https://pavankjadda.medium.com/implement-hibernate-2nd-level-cache-with-redis-spring-boot-and-spring-data-jpa-7cdbf5632883) or [PDF document](src/main/resources/Implement%20Hibernate%202nd%20level%20cache%20with%20Redis%2C%20Spring%20Boot%2C%20and%20Spring%20Data%20JPA.pdf) for detailed instructions.

#### Running the app 
' 
./mvnw package &&  java -jar target/redis-demo-1.0.0.jar

docker build -t amidja/redis-demo-docker .
This command builds an image and tags it as amidja/redis-demo-docker

docker run -d -p8080:8080 amidja/redis-demo-docker

'

#### End-Points
- [Health](http://localhost:8080/actuator/health) 
- [All Employees](http://localhost:8080/api/v1/employee/find/all)


https://spring.io/guides/gs/spring-boot-docker/
https://github.com/bitnami/bitnami-docker-redis
https://github.com/redisson/redisson/wiki/2.-Configuration#27-sentinel-mode
https://www.developers-notebook.com/development/using-redis-sentinel-with-docker-compose/