package com.grzegorznowakowski.pokedex.pokemon;

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

public class PokemonControllerTests {

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

    public static PokemonEntity getTestPokemon() {
        PokemonEntity pokemon = new PokemonEntity();
        pokemon.setId(9999999);
        pokemon.setName("TestPokemon");

        PokemonType pokemonType = new PokemonType();
        pokemonType.setId(1L);
        pokemonType.setName("Grass");

        Set<PokemonType> types = new HashSet<>();
        types.add(pokemonType);

        pokemon.setTypes(types);

        return pokemon;
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

    public static void addTestPokemon() {
        given().
                header("Authorization", getAuthKey(getTestUser())).
                contentType("application/json").body(getTestPokemon()).
        when().
                post("/api/pokemon");
    }

    @BeforeEach
    public void createTestUserAndPokemon_SetAuthKey() {
        registerATestUser();
        addTestPokemon();
        addTestPokemonType();
    }

    @AfterEach
    public void deleteTestPokemonAfterTest() {
        given().
                header("Authorization", getAuthKey(getTestUser())).
        when().
                delete("/api/pokemon/" + getTestPokemon().getId());
    }


    @Test
    public void getPokemonAfterLogingInAndRetrivingAuthKey_status200() {
        given().header("Authorization", getAuthKey(getTestUser()))
                .when().get("/api/pokemon/" + getTestPokemon().getId())
                .then().statusCode(200)
                .and().body("name", equalTo(getTestPokemon().getName()));
    }



    @Test
    public void getSinglePokemon_NoAuthentication_status403() {
        get("/api/pokemon/1").then().statusCode(403);
    }


    @Test
    public void givenCredentials_tryToLoginIn_status200() {
        given().
                contentType("application/json").body(getTestUser()).
        when().
                post("/login").
        then().
                statusCode(200);

    }

    @Test
    public void givenCredentials_tryToLoginInWithFakeAccount_status403() {
        given().body("{\"username\": \"postz\", \"password\": \"password\"}").when().post("/login").then().statusCode(403);
    }


    @Test
    public void registerAlreadyExistingUser_status500() {
        given().
                contentType("application/json").body(getTestUser()).
        when().
                post("/users/signup").
        then().statusCode(500);
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
    public void deleteGivenPokemon_NoAuthentication_status403() {
        given().
                contentType("application/json").body(getTestPokemon()).
        when().
                delete("/api/pokemon/" + getTestPokemon().getId()).
        then().statusCode(403);
    }

    @Test
    public void deleteGivenPokemon_WithAuthentication_status204() {
        given().
                header("Authorization", getAuthKey(getTestUser())).
        when().
                delete("/api/pokemon/" + getTestPokemon().getId()).
        then().statusCode(204);
    }

    @Test
    public void updateGivenPokemon_NoAuthentication_status403() {
        PokemonEntity pokemon = getTestPokemon();
        pokemon.setName("updatedName");

        given().
                contentType("application/json").body(pokemon).
        when().
                put("/api/pokemon/" + getTestPokemon().getId()).
        then().statusCode(403);
    }

    @Test
    public void updateGivenPokemon_WithAuthentication_status201() {
        PokemonEntity pokemon = getTestPokemon();
        pokemon.setName("updatedName");

        given().
                header("Authorization", getAuthKey(getTestUser())).
                contentType("application/json").body(pokemon).
        when().
                put("/api/pokemon/" + getTestPokemon().getId()).
        then().
                statusCode(201).
                and().
                body("name", equalTo("updatedName"));
    }

    @Test
    public void getAllPokemon_WithAuthentication_status200() {
        given().
                param("page", "0").
                param("size", "1").
                header("Authorization", getAuthKey(getTestUser()))
        .when().
                get("/api/pokemon").
                then().
                statusCode(200).
                and().
                body("page.size", equalTo(1));
    }

    @Test
    public void getPokemonByType_status200() {
        PokemonEntity pokemonEntity = getTestPokemon();
        pokemonEntity.setId(5555);
        pokemonEntity.setName("testPokemonType");
        Set<PokemonType> typeSet = new HashSet<>();
        typeSet.add(getTestPokemonType());
        pokemonEntity.setTypes(typeSet);


        //insert test pokemon
        given().
                header("Authorization", getAuthKey(getTestUser())).
                contentType("application/json").body(pokemonEntity).
        when().
                post("/api/pokemon");



        //assert pokemon
        given().
                header("Authorization", getAuthKey(getTestUser())).
        when().
                get("/api/pokemon/type/" + getTestPokemonType().getName()).
        then().
                statusCode(200).
                and().
                body("page.totalElements", equalTo(1));

        //delete test pokemon
        given().
                header("Authorization", getAuthKey(getTestUser())).
        when().
                delete("/api/pokemon/" + pokemonEntity.getId());

    }

}
