# pokedex Spring Boot, MySQL, JPA, Hibernate, RESTful API
Pokedex RESTful API


## Requirements
1. Java - 1.8.x

2. Maven - 3.x.x

3. Mysql - 8.x.x

## Setup
**1. Clone the application**

```
git clone https://github.com/kelekelio/pokedex.git
```

**2. Create Mysql database**
```
create database pokedex
```

or change the database name in application.properties
```
spring.datasource.url=jdbc:mysql://localhost:3306/pokedex?useSSL=false&serverTimezone=CET
'''

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation


**4. Build and run the app**

```
mvn package
java -jar target/easy-notes-1.0.0.jar
```

Or run the app directly -

```
mvn spring-boot:run
```

The app will run at http://localhost:8080

## Available APIs

** 1. Pokemon**
