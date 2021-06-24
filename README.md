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
```

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

## User

All APIs are protected and require authentication to be used.
In order to receive an API key, register a new user.

** Register a user **


```
POST http://localhost:8080/users/signup
```

and set the body to JSON
```
{
    "username": "username",
    "password": "password"
}
```

** Retrive API key **

With a user registered, login and the API key should be in the header of the response.


```
POST http://localhost:8080/login
```

```
{
    "username": "testing",
    "password": "password"
}
```

From now on, set the key in the header of every request to consume the API.

## Pokemon

Available endpoints:

|Type|URL|variable|Description|
|---|---|---|---|
|GET|/api/pokemon||Returns a paginated list of all Pokemon. size=, page= variables available!
|GET|/api/pokemon/type/{type}|String|Returns a list of all Pokemon of provided Type|
|GET|/api/pokemon/{value}|String or Integer|Returns o pokemon of given ID or name|
|POST|/api/pokemon|Pokemon Object|Insterts a given Pokemon into the database|
|PUT|/api/pokemon/{id}|Integer|Updates a Pokemon of given ID|
|DELETE|/api/pokemon/{id}|Integer|Deletes a Pokemon of Given ID|

