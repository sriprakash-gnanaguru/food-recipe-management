package com.food.recipe.manage.adapter.in.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.food.recipe.manage.adapter.in.http.domain.Ingredient;
import com.food.recipe.manage.adapter.in.http.domain.InputRequest;
import com.food.recipe.manage.adapter.in.http.domain.Receipe;
import com.food.recipe.manage.adapter.out.db.dto.FoodReceipe;
import com.food.recipe.manage.common.FoodReceipeManagementService;
import com.food.recipe.manage.recipemanagement.RecipeManagementApplication;
import net.joshka.junit.json.params.JsonFileSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import javax.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = RecipeManagementApplication.class)
public class FoodReceipeManagmentControllerTest {
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    @MockBean
    FoodReceipeManagementService service;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @ParameterizedTest
    @JsonFileSource(resources = {"/input-request-positive.json"})
    void add_Receipe_Test_case1(final JsonObject request) throws Exception {
        when(service.addReceipe(getInput())).thenReturn(FoodReceipe.builder().recipeId(1L).build());
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/manage-receipe/addReceipe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.getJsonObject("request").toString()));
        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @ParameterizedTest
    @JsonFileSource(resources = {"/input-request-negative.json"})
    void add_Receipe_Test_case2(final JsonObject request) throws Exception {
        when(service.addReceipe(getInput())).thenReturn(FoodReceipe.builder().recipeId(1L).build());
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/manage-receipe/addReceipe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.getJsonObject("request").toString()));
        resultActions.andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    void delete_Receipe_Test_case1() throws Exception {
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/manage-receipe/deleteReceipe/{ReceipeId}",1)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void delete_Receipe_Test_case2() throws Exception {
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/manage-receipe/deleteReceipe")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    void get_Receipe_Test_case1() throws Exception {
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/manage-receipe/getReceipe/{ReceipeId}",1)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void get_Receipe_Test_case2() throws Exception {
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/manage-receipe/getReceipe",1)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    void get_Receipes_Test_case1() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("Dish", "VEG");
        params.add("Instruction", "with Barbeque");
        params.add("Ingredient", "Paneer");
        params.add("Servings", "4");
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/manage-receipe/getReceipes")
                                                .params(params).contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @ParameterizedTest
    @JsonFileSource(resources = {"/input-request-positive.json"})
    void update_Receipe_Test_case1(final JsonObject request) throws Exception {
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/manage-receipe/updateReceipe/{ReceipeId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.getJsonObject("request").toString()));
        when(service.updateReceipe(getInput(),"1")).thenReturn(FoodReceipe.builder().build());
        resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @ParameterizedTest
    @JsonFileSource(resources = {"/input-request-positive.json"})
    void update_Receipe_Test_case2(final JsonObject request) throws Exception {
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/manage-receipe/updateReceipe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.getJsonObject("request").toString()));
        when(service.updateReceipe(getInput(),null)).thenReturn(FoodReceipe.builder().build());
        resultActions.andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    private InputRequest getInput() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(Ingredient.builder().name("Garlic").ingredientId("1").build());
        ingredients.add(Ingredient.builder().name("Onion").ingredientId("2").build());
        ingredients.add(Ingredient.builder().name("Paneer").ingredientId("3").build());
        ingredients.add(Ingredient.builder().name("Butter").ingredientId("4").build());
        Receipe foodReceipe = Receipe.builder().name("Paneer Butter Masala").instruction("without oven").servings("3").dish("VEG").ingredients(ingredients).build();
        InputRequest inputRequest = InputRequest.builder().userId("user1").receipe(foodReceipe).build();
        return inputRequest;
    }



}
