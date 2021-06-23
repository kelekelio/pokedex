package com.grzegorznowakowski.pokedex.pokemon;

import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import com.grzegorznowakowski.pokedex.pokemonType.entity.PokemonType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class PkemonControllerTests {

    private final String authHeader = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSIsImV4cCI6MTYyNTMwNTY2N30.1MQX4DjJg3rkmoclywIsISIs7pVIWZFninUI7xKzvGRSdXAE-7geWnTLJ7FTP90nWlHMOi4N0PsMSXUZZwu3Pg";


    @Test
    public void test() {
        get("/api/pokemon/1").then().body("name", equalTo("Bulbasaur"));
    }


    //works
    @Test
    public void getSinglePokemon_NoAuthentication_status403() {
        get("/api/pokemon/1").then().statusCode(403);
    }

    //works
    @Test
    public void getSignlePokemon_AuthenticationHeader_status200() {
        given().header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSIsImV4cCI6MTYyNTMwNTY2N30.1MQX4DjJg3rkmoclywIsISIs7pVIWZFninUI7xKzvGRSdXAE-7geWnTLJ7FTP90nWlHMOi4N0PsMSXUZZwu3Pg")
                .when().get("/api/pokemon/1")
                .then().statusCode(200)
                .and().body("name", equalTo("Bulbasaur"));
    }

    @Test
    public void basicAuthenticationWithLoginAndPass_status200() {
        given().auth().basic("test1", "pass").when().get("/api/pokemon/1").then().statusCode(200);
    }

    @Test
    public void givenCredentials_tryToLoginIn_status200() {
        given().body("{\"username\": \"post\", \"password\": \"password\"}").when().post("/login").then().statusCode(200);
    }

    @Test
    public void givenCredentials_tryToLoginIn_status403() {
        given().body("{\"username\": \"postz\", \"password\": \"password\"}").when().post("/login").then().statusCode(403);
    }

    @Test
    public void registerUser_status200() {
        Map<String, String> jsonAsMap = new HashMap<>();
        jsonAsMap.put("username", "new3");
        jsonAsMap.put("password", "password");

        given().
                contentType("application/json").body(jsonAsMap).
        when().
                post("/users/signup").
        then().statusCode(200);
    }

    @Test
    public void givenPokemon_addToDatabse_NoAuthentication_status403() {
        Map<String, Object> pokemon = new HashMap<>();
        Map<String, Object> type = new HashMap<>();
        type.put("id", 1);
        type.put("name", "Grass");

        pokemon.put("id", 999999);
        pokemon.put("name", "addTest");
        pokemon.put("types", type);

        given().
                contentType("application/json").body(pokemon).
        when().
                post("/api/pokemon").
        then().statusCode(403);

    }

    @Test
    public void givenPokemon_addToDatabse_Authenticated_status200() {
        PokemonEntity pokemon = new PokemonEntity();
        pokemon.setId(999999);
        pokemon.setName("testName1");

        PokemonType pokemonType = new PokemonType();
        pokemonType.setId(1L);
        pokemonType.setName("Grass");

        Set<PokemonType> types = new HashSet<>();
        types.add(pokemonType);

        pokemon.setTypes(types);


        given().
                header("Authorization", authHeader).
                contentType("application/json").body(pokemon).
                when().
                post("/api/pokemon").
                then().statusCode(201);

    }


}
