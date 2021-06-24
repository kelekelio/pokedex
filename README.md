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

![alt text](https://aionpowerbook.com/pokedex/1.jpg)

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

![alt text](https://aionpowerbook.com/pokedex/2.jpg)

![alt text](https://aionpowerbook.com/pokedex/3.jpg)

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
|GET|/api/pokemon||Returns a paginated list of all Pokemon. size=, page= and sort= variables available! By default, only 20 records per page are returned.
|GET|/api/pokemon/type/{type}|String|Returns a list of all Pokemon of provided Type|
|GET|/api/pokemon/{value}|String or Integer|Returns a pokemon of given ID or name|
|POST|/api/pokemon|Pokemon Object|Inserts a given Pokemon into the database|
|PUT|/api/pokemon/{id}|Integer|Updates a Pokemon of given ID|
|DELETE|/api/pokemon/{id}|Integer|Deletes a Pokemon of Given ID|


```
{
    "id": 1,
    "name": "Bulbasaur",
    "types": [
        {
            "id": 2,
            "name": "Poison"
        },
        {
            "id": 1,
            "name": "Grass"
        }
    ],
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/pokemon/1"
        },
        "pokemons": {
            "href": "http://localhost:8080/api/allpokemon"
        }
    }
}
```

## Pokemon Type

Available endpoints:

|Type|URL|variable|Description|
|---|---|---|---|
|GET|/api/type||Returns a paginated list of all Pokemon Types. size=, page= and sort= variables available! By default, only 20 records per page are returned.
|GET|/api/type/{id}|Long|Returns a pokemon type of given ID|
|POST|/api/type|PokemonType Object|Inserts a given Pokemon type into the database|
|PUT|/api/type/{id}|Long|Updates a Pokemon type of given ID|
|DELETE|/api/type/{id}|Long|Deletes a Pokemon type of Given ID|


```
{
    "id": 1,
    "name": "Grass",
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/type/1"
        },
        "types": {
            "href": "http://localhost:8080/api/alltypes"
        }
    }
}
```