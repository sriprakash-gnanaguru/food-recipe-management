package com.food.recipe.manage.recipemanagement.http;

/*@RequestMapping("/manage-receipe")*/
public interface FoodReceipeManagementApi {
/*
    @Operation(summary = "This Service is used to add recipe with given detail in the database.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Receipe added Successfully in the database.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OutputResponse.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "1) If required request body is missing.<br/>" +
                            "2) If JSON Content is missing.<br/>" +
                            "3) If interactive process  is missing."),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception while connecting the database"),
            @ApiResponse(
                    responseCode = "503",
                    description = "Unavailability of the Service")
    })
 *//*   @PostMapping("/addReceipe")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody*//*
    ResponseEntity<OutputResponse>  addReceipe(@RequestBody InputRequest request) throws BusinessException;

   *//* @DeleteMapping("/deleteReceipe")
    @ResponseStatus(HttpStatus.OK)*//*
    ResponseEntity<OutputResponse>  deleteReceipe(@RequestParam(value = "ReceipeId") String receipeId) throws BusinessException;*/


}
