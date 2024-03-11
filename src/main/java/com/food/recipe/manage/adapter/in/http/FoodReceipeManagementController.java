package com.food.recipe.manage.adapter.in.http;

import com.food.recipe.manage.adapter.out.db.FoodReceipe;
import com.food.recipe.manage.adapter.out.http.OutputResponse;
import com.food.recipe.manage.common.BusinessException;
import com.food.recipe.manage.common.CommonUtil;
import com.food.recipe.manage.common.FoodReceipeManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping( path="/manage-receipe")
public class FoodReceipeManagementController {
    private final FoodReceipeManagementService service;

    @PostMapping(path  = "/addReceipe")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<OutputResponse> addReceipe(@RequestBody InputRequest request) throws BusinessException {
        try{
            FoodReceipe data = service.addReceipe(request);
            request.getReceipe().setReceipeId(String.valueOf(data.getRecipeId()));
        }catch (Exception e){
            throw new BusinessException(request.getReceipe().getReceipeId() ,e);
        }
        return new ResponseEntity<OutputResponse>(OutputResponse.builder().receipeId(request.getReceipe().getReceipeId()).status("Success").msg("Receipe persisted successfully").build(), HttpStatus.CREATED);
    }

   @DeleteMapping(value = "/deleteReceipe/{ReceipeId}")
   @ResponseStatus(HttpStatus.OK)
   public ResponseEntity<OutputResponse> deleteReceipe(@PathVariable(value = "ReceipeId")String receipeId) throws BusinessException {
        try{
            service.deleteReceipe(receipeId);
        }catch (Exception e){
            throw new BusinessException(receipeId ,e);
        }
        return new ResponseEntity<OutputResponse>(OutputResponse.builder().receipeId(receipeId).status("Success").msg("Receipe is deleted successfully").build(), HttpStatus.OK);
    }

    @GetMapping(value = "/getReceipe/{ReceipeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputRequest> getReceipe(@PathVariable("ReceipeId")String receipeId) throws BusinessException {
        InputRequest data = null;
        try{
            data = service.getReceipe(receipeId);
            if(data == null || data.getReceipe().getReceipeId() == null){
                data = InputRequest.builder().msg("No data found").build();
            }
        }catch (Exception e){
            throw new BusinessException(receipeId ,e);
        }
        return new ResponseEntity<InputRequest>(data, HttpStatus.OK);
    }

    @GetMapping(value = "/getReceipes")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<InputRequest>> getReceipes(@RequestParam(value = "Dish", required = false)  final String dish,
                                                          @RequestParam(value = "Ingredient", required = false)  final String ingredient,
                                                          @RequestParam(value = "Servings", required = false)  final String servings,
                                                          @RequestParam(value = "Instruction", required = false)  final String instruction) throws BusinessException {
        List<InputRequest> data = new ArrayList<>();
        try{
            data = service.getReceipes(dish, ingredient, servings, instruction);

        }catch (Exception e){
            throw new BusinessException("Unable to fetch the receipes on combination" ,e);
        }
        return new ResponseEntity<List<InputRequest>>(data, HttpStatus.OK);
    }

    @PutMapping("/updateReceipe/{ReceipeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OutputResponse> updateReceipe(@RequestBody InputRequest request, @PathVariable("ReceipeId") String receipeId){
        OutputResponse response = null;
        try{
            FoodReceipe data = service.updateReceipe(request, receipeId);
            if(data == null || data.getRecipeId() == null){
                 response = OutputResponse.builder().status("Failure").msg("No data found").build();
            }else{
                response = OutputResponse.builder().receipeId(String.valueOf(data.getRecipeId())).status("Success").msg("Receipe is updated successfully").build();
            }
        }catch (Exception e){
            throw new BusinessException(request.getReceipe().getReceipeId() ,e);
        }
        return new ResponseEntity<OutputResponse>(response, HttpStatus.CREATED);
    }

}
