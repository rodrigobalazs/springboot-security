## Spring Boot Security
This repository contains an Spring Boot Application which provides a securized API for Product´s management.
The App it´s configured with Spring Security. The main goal is to show 2 of the main features of Spring Security:<br/>
#### a) User´s Authentication via JWT Authentication Token:
-a given User will be able to sign-up into the Application via POST /signup. After registration, the User will be able
to sign-in into the App via POST /login ( providing a user email and password ). If success, the endpoint will return an
Authentication Token which will allow the User to perform several operations related to Product´s management via some
REST endpoints.
#### b) User´s Authorization via Roles:
-some Product´s API endpoints will be accessible only for specific Roles
-e.g Product´s Creation is only accessible for Admin Roles

<br/>

### 🔧 Technology Stack

```
Java 17
Spring Boot 3 ( REST API )
Spring Security / Java JWT ( JSON Web Token )
Spring Data ( MySQL )
Misc Libraries (  Maven  /  Docker  /  SpringDoc OpenAPI  /  Apache Commons  /  Lombok  )
```

<br/>

### ⚒️ Getting Started

```bash
# clone the project
git clone https://github.com/rodrigobalazs/springboot-security.git;
cd springboot-security;

# start a mysql docker container
docker run --name security_db -e MYSQL_DATABASE=security_db -e MYSQL_USER=<user> \
    -e MYSQL_PASSWORD=<pass> -e MYSQL_ROOT_PASSWORD=<pass> \
    -p 3306:3306 -d mysql:latest;

# make sure to update application.properties with the
# MYSQL_USER and MYSQL_PASSWORD values defined in the previous point
spring.datasource.username=<user>
spring.datasource.password=<pass>

# also, make sure to set into the application.properties the 'secret key' and 'expiration time'
# required for JWT authentication
jwt.auth.token.secret.key=<value>
jwt.auth.token.expiration.time=<value_in_milliseconds>

# compile and start the spring boot server
mvn clean install;
mvn spring-boot:run;
```

<br/>

### 💡 API Examples

#### 1. first, create a new User with 'Admin' Role e.g =>
```
curl -X POST http://localhost:8080/authentication/signup \
     -H 'accept: application/json' \
     -H 'Content-Type: application/json' \
     -d '{"email": "james.lexwey@example.com", "password": "fewf", "role": "role_admin"}';
```

#### 2. then, proceed to sign-in the previous User, should retrieve a valid <authentication_token>
```
curl -X POST http://localhost:8080/authentication/login \
     -H 'accept: application/json' \
     -H 'Content-Type: application/json' \
     -d '{"email": "james.lexwey@example.com", "password": "fewf"}';
```

#### 3. create a new User with 'Customer' Role e.g =>
```
curl -X POST http://localhost:8080/authentication/signup \
     -H 'accept: application/json' \
     -H 'Content-Type: application/json' \
     -d '{"email": "tom.grawb@example.com", "password": "fwq2a", "role": "role_customer"}';
```

#### 4. proceed to sign-in the previous User, should retrieve a valid <authentication_token>
```
curl -X POST http://localhost:8080/authentication/login \
     -H 'accept: application/json' \
     -H 'Content-Type: application/json' \
     -d '{"email": "tom.grawb@example.com", "password": "fwq2a"}';
```

#### 5. get all the Available Products ( this API endpoint it´s accessible for both Roles 'Admin' and 'Customer' )
-note: make sure to include the <authentication_token> into the request header
```
curl -X GET http://localhost:8080/products/getProducts \
     -H 'accept: application/json' \
     -H "Authorization: Bearer <authentication_token>";
```

#### 6. creates a new Product ( this API endpoint it´s accessible only for 'Admin' Roles )
-note: make sure to include the <authentication_token> into the request header<br/>
-note(2): if this endpoint it´s executed with a User with 'Customer' Role, the product won´t be created and the API endpoint will return nothing
```
curl -X POST http://localhost:8080/products \
     -H 'accept: application/json' \
     -H 'Content-Type: application/json' \
     -H "Authorization: Bearer <authentication_token>" \
     -d '{"name": "Classic Armchair", "availableQuantity": 100}';
```

<br/>

### 🔍 API Documentation / Swagger

the API documentation could be accessed from => http://localhost:8080/swagger-ui/index.html

![](https://github.com/rodrigobalazs/springboot-security/blob/main/src/main/resources/static/security_app_swagger.png)
