package com.grzegorznowakowski.pokedex;

import com.grzegorznowakowski.pokedex.pokemon.entity.PokemonEntity;
import com.grzegorznowakowski.pokedex.pokemon.repository.PokemonRepository;
import com.grzegorznowakowski.pokedex.pokemonType.entity.PokemonType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PokedexApplicationTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    PokemonRepository pokemonRepository;

    private MockMvc mockMvc;


    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }



    PokemonEntity pokemon1 = new PokemonEntity(100, "test", Set.of(new PokemonType()));
    PokemonEntity pokemon2 = new PokemonEntity(101, "test", Set.of(new PokemonType()));
    PokemonEntity pokemon3 = new PokemonEntity(102, "test", Set.of(new PokemonType()));




    @Test
    void contextLoads() {
    }

    @Test
    public void exampleTest() {
        String body = this.restTemplate.getForObject("/", String.class);
        assertThat(body).isEqualTo("Hello World");
    }

    @Test
    public void getPokemon_UnothorisedAccess_Return404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/pokemon/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllPokemon_success() throws Exception {
        List<PokemonEntity> pokemons = new ArrayList<>(Arrays.asList(pokemon1, pokemon2, pokemon3));

        Mockito.when(pokemonRepository.findAll()).thenReturn(pokemons);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/pokemon")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSIsImV4cCI6MTYyNTMwNTY2N30.1MQX4DjJg3rkmoclywIsISIs7pVIWZFninUI7xKzvGRSdXAE-7geWnTLJ7FTP90nWlHMOi4N0PsMSXUZZwu3Pg")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllPokemon_fail_Unotherised() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/pokemon")
                .header("Authorization", "Bearer key")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getPokemonById_success() throws Exception {

        Mockito.when(pokemonRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(pokemon1));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/pokemon/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/hal+json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.types").isArray())
                .andExpect(status().isOk());

    }

    @Test
    public void savePokemon_success() throws Exception {

        PokemonType type = new PokemonType();
        type.setId(1L);
        type.setName("Grass");
        Set<PokemonType> types = new HashSet<PokemonType>();
        types.add(type);

        PokemonEntity pokemon = new PokemonEntity(33, "test33", types);



        pokemonRepository.save(pokemon);


        Mockito.when(pokemonRepository.findById(33)).thenReturn(java.util.Optional.of(pokemon));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/pokemon/33").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



}
