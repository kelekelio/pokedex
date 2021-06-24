package com.grzegorznowakowski.pokedex.PokemonType;

import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import com.grzegorznowakowski.pokedex.pokemonType.entity.PokemonType;
import com.grzegorznowakowski.pokedex.user.entity.User;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PokemonTypeTests {


    public static String getAuthKey(User user) {
        Response response = given().
                contentType("application/json").body(user).
                when().
                post("/login");

        return response.getHeader("Authorization");
    }

    public static User getTestUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testUser");

        return user;
    }

    public static PokemonType getTestPokemonType() {
        PokemonType type = new PokemonType();
        type.setId(9999L);
        type.setName("TestType");

        return type;
    }

    public static void addTestPokemonType() {
        given().
                header("Authorization", getAuthKey(getTestUser())).
                contentType("application/json").body(getTestPokemonType()).
         when().
                post("/api/type");
    }

    public static void registerATestUser() {
        given().
                contentType("application/json").body(getTestUser()).
        when().
                post("/users/signup");
    }


    @BeforeEach
    public void createTestUserAndPokemonType_SetAuthKey() {
        registerATestUser();
        addTestPokemonType();
    }

    @AfterEach
    public void deleteTestPokemonTypeAfterTest() {
        given().
                header("Authorization", getAuthKey(getTestUser())).
        when().
                delete("/api/type/" + getTestPokemonType().getId());
    }


    @Test
    public void getTestPokemonType_WithAuthentication_status200() {
        given().
                header("Authorization", getAuthKey(getTestUser()))
        .when().
                get("/api/type/" + getTestPokemonType().getId())
        .then().statusCode(200).
                and().
                body("name", equalTo(getTestPokemonType().getName()));
    }

    @Test
    public void getTestPokemonType_NoAuthentication_status403() {
        given().
                when().
                get("/api/type/" + getTestPokemonType().getId()).
        then().
                statusCode(403);
    }

    @Test
    public void getAllPokemonTypes_NoAuthentication_status403() {
        get("/api/type").
        then().
                statusCode(403);
    }

    //TODO: Finish adding tests as PokemonControllerTests
}
