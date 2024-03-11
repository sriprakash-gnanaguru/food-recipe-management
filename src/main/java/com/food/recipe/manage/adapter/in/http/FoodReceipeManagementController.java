package com.food.recipe.manage.adapter.in.http;

import com.food.recipe.manage.adapter.in.http.domain.InputRequest;
import com.food.recipe.manage.adapter.out.db.dto.FoodReceipe;
import com.food.recipe.manage.adapter.out.http.domain.OutputResponse;
import com.food.recipe.manage.common.Constants;
import com.food.recipe.manage.common.exception.BusinessException;
import com.food.recipe.manage.common.FoodReceipeManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping( path="/manage-receipe")
public class FoodReceipeManagementController {
    private final FoodReceipeManagementService service;

    @Operation(summary = "This Service is used to add recipe with given detail in the database.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Receipe added Successfully in the database.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OutputResponse.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "If required request body is missing."),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception while connecting the database"),
            @ApiResponse(
                    responseCode = "503",
                    description = "Unavailability of the Service")
    })
    @PostMapping(path  = "/addReceipe")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<OutputResponse> addReceipe(@RequestBody InputRequest request) throws BusinessException {
        try{
            FoodReceipe data = service.addReceipe(request);
            request.getReceipe().setReceipeId(String.valueOf(data.getRecipeId()));
        }catch (Exception e){
            throw new BusinessException(request.getReceipe().getReceipeId() ,e);
        }
        return new ResponseEntity<OutputResponse>(OutputResponse.builder().receipeId(request.getReceipe().getReceipeId()).status(Constants.SUCCESS).msg(Constants.CREATE_MSG).build(), HttpStatus.CREATED);
    }

    @Operation(summary = "This Service is used to deleta a recipe with receipeId in the database.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Receipe added Successfully in the database.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OutputResponse.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "If receipId is missing."),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception while connecting the database"),
            @ApiResponse(
                    responseCode = "503",
                    description = "Unavailability of the Service")
    })
   @DeleteMapping(value = "/deleteReceipe/{ReceipeId}")
   @ResponseStatus(HttpStatus.OK)
   public ResponseEntity<OutputResponse> deleteReceipe(@PathVariable(value = Constants.RECEIPE_ID)String receipeId) throws BusinessException {
        try{
            service.deleteReceipe(receipeId);
        }catch (Exception e){
            throw new BusinessException(receipeId ,e);
        }
        return new ResponseEntity<OutputResponse>(OutputResponse.builder().receipeId(receipeId).status(Constants.SUCCESS).msg(Constants.DELETE_MSG).build(), HttpStatus.OK);
    }

    @Operation(summary = "This Service is used to fetch recipe with recipeId from the database.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Receipe is fetched successfully from the database.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OutputResponse.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "If receipeId is missing."),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception while connecting the database."),
            @ApiResponse(
                    responseCode = "503",
                    description = "Unavailability of the Service.")
    })
    @GetMapping(value = "/getReceipe/{ReceipeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputRequest> getReceipe(@PathVariable(Constants.RECEIPE_ID)String receipeId) throws BusinessException {
        InputRequest data = null;
        try{
            data = service.getReceipe(receipeId);
            if(data == null || data.getReceipe().getReceipeId() == null){
                data = InputRequest.builder().msg(Constants.NO_DATA_MSG).build();
            }
        }catch (Exception e){
            throw new BusinessException(receipeId ,e);
        }
        return new ResponseEntity<InputRequest>(data, HttpStatus.OK);
    }
    @Operation(summary = "This Service is used to fetch recipe based on combination of request parameter like Ingredient, Servings, instruction etc. from the database.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Receipe is fetched successfully from the database.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OutputResponse.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Alteast one request parameter from combination<I>(Ingredient, Dish, Servings,Instruction)</I> is required"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception while connecting the database"),
            @ApiResponse(
                    responseCode = "503",
                    description = "Unavailability of the Service")
    })
    @GetMapping(value = "/getReceipes")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<InputRequest>> getReceipes(@RequestParam(value = Constants.DISH, required = false)  final String dish,
                                                          @RequestParam(value = Constants.INGREDIENT, required = false)  final String ingredient,
                                                          @RequestParam(value = Constants.SERVINGS, required = false)  final String servings,
                                                          @RequestParam(value = Constants.INSTRUCTION, required = false)  final String instruction) throws BusinessException {
        List<InputRequest> data = new ArrayList<>();
        try{
            data = service.getReceipes(dish, ingredient, servings, instruction);

        }catch (Exception e){
            throw new BusinessException("Unable to fetch the receipes on combination of filter criteria" ,e);
        }
        return new ResponseEntity<List<InputRequest>>(data, HttpStatus.OK);
    }

    @Operation(summary = "This Service is used to update the recipe with receipId with combination of request parameter like Ingredient, Servings, instruction etc. in the database.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Receipe is updated successfully in the database.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OutputResponse.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "If receipeId is missing."),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception while connecting the database"),
            @ApiResponse(
                    responseCode = "503",
                    description = "Unavailability of the Service")
    })
    @PutMapping("/updateReceipe/{ReceipeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OutputResponse> updateReceipe(@RequestBody InputRequest request, @PathVariable(Constants.RECEIPE_ID) String receipeId){
        OutputResponse response = null;
        try{
            FoodReceipe data = service.updateReceipe(request, receipeId);
            if(data == null || data.getRecipeId() == null){
                 response = OutputResponse.builder().status(Constants.FAILURE).msg(Constants.NO_DATA_MSG).build();
            }else{
                response = OutputResponse.builder().receipeId(String.valueOf(data.getRecipeId())).status(Constants.SUCCESS).msg(Constants.UPDATE_MSG).build();
            }
        }catch (Exception e){
            throw new BusinessException(request.getReceipe().getReceipeId() ,e);
        }
        return new ResponseEntity<OutputResponse>(response, HttpStatus.CREATED);
    }

}