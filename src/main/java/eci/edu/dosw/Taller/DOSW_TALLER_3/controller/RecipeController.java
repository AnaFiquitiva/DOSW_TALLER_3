package eci.edu.dosw.Taller.DOSW_TALLER_3.controller;
import eci.edu.dosw.Taller.DOSW_TALLER_3.dto.*;
import eci.edu.dosw.Taller.DOSW_TALLER_3.service.interfaces.RecipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para la gestión de recetas de MasterChef Celebrity
 */
@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
@Tag(name = "Recipes", description = "Recipe management API for MasterChef Celebrity")
public class RecipeController {

    private final RecipeService recipeService;

    /**
     * Endpoint 1: Registra una nueva receta de un televidente
     */
    @PostMapping("/viewer")
    @Operation(summary = "Create viewer recipe",
            description = "Register a new recipe created by a TV viewer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recipe created successfully",
                    content = @Content(schema = @Schema(implementation = RecipeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<RecipeResponse> createViewerRecipe(
            @Valid @RequestBody ViewerRecipeRequest request) {
        RecipeResponse response = recipeService.createViewerRecipe(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Endpoint 2: Registra una nueva receta de un participante
     */
    @PostMapping("/contestant")
    @Operation(summary = "Create contestant recipe",
            description = "Register a new recipe created by a program contestant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recipe created successfully",
                    content = @Content(schema = @Schema(implementation = RecipeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<RecipeResponse> createContestantRecipe(
            @Valid @RequestBody ContestantRecipeRequest request) {
        RecipeResponse response = recipeService.createContestantRecipe(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Endpoint 3: Registra una nueva receta de un chef jurado
     */
    @PostMapping("/judge-chef")
    @Operation(summary = "Create judge chef recipe",
            description = "Register a new recipe created by a judge chef")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recipe created successfully",
                    content = @Content(schema = @Schema(implementation = RecipeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<RecipeResponse> createJudgeChefRecipe(
            @Valid @RequestBody JudgeChefRecipeRequest request) {
        RecipeResponse response = recipeService.createJudgeChefRecipe(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Endpoint 4: Obtiene todas las recetas guardadas
     */
    @GetMapping
    @Operation(summary = "Get all recipes",
            description = "Retrieve all recipes stored in the system")
    @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully")
    public ResponseEntity<List<RecipeResponse>> getAllRecipes() {
        List<RecipeResponse> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    /**
     * Endpoint 5: Obtiene una receta por su número consecutivo
     */
    @GetMapping("/{consecutiveNumber}")
    @Operation(summary = "Get recipe by consecutive number",
            description = "Retrieve a specific recipe by its consecutive number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe found",
                    content = @Content(schema = @Schema(implementation = RecipeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    public ResponseEntity<RecipeResponse> getRecipeByConsecutiveNumber(
            @Parameter(description = "Consecutive number of the recipe")
            @PathVariable Long consecutiveNumber) {
        RecipeResponse response = recipeService.getRecipeByConsecutiveNumber(consecutiveNumber);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint 6: Obtiene todas las recetas de participantes
     */
    @GetMapping("/contestant")
    @Operation(summary = "Get contestant recipes",
            description = "Retrieve all recipes created by program contestants")
    @ApiResponse(responseCode = "200", description = "Contestant recipes retrieved successfully")
    public ResponseEntity<List<RecipeResponse>> getContestantRecipes() {
        List<RecipeResponse> recipes = recipeService.getContestantRecipes();
        return ResponseEntity.ok(recipes);
    }

    /**
     * Endpoint 7: Obtiene todas las recetas de televidentes
     */
    @GetMapping("/viewer")
    @Operation(summary = "Get viewer recipes",
            description = "Retrieve all recipes created by TV viewers")
    @ApiResponse(responseCode = "200", description = "Viewer recipes retrieved successfully")
    public ResponseEntity<List<RecipeResponse>> getViewerRecipes() {
        List<RecipeResponse> recipes = recipeService.getViewerRecipes();
        return ResponseEntity.ok(recipes);
    }

    /**
     * Endpoint 8: Obtiene todas las recetas de chefs jurado
     */
    @GetMapping("/judge-chef")
    @Operation(summary = "Get judge chef recipes",
            description = "Retrieve all recipes created by judge chefs")
    @ApiResponse(responseCode = "200", description = "Judge chef recipes retrieved successfully")
    public ResponseEntity<List<RecipeResponse>> getJudgeChefRecipes() {
        List<RecipeResponse> recipes = recipeService.getJudgeChefRecipes();
        return ResponseEntity.ok(recipes);
    }

    /**
     * Endpoint 9: Obtiene recetas por temporada
     */
    @GetMapping("/season/{season}")
    @Operation(summary = "Get recipes by season",
            description = "Retrieve all recipes from a specific season")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Season recipes retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid season number")
    })
    public ResponseEntity<List<RecipeResponse>> getRecipesBySeason(
            @Parameter(description = "Season number")
            @PathVariable Integer season) {
        List<RecipeResponse> recipes = recipeService.getRecipesBySeason(season);
        return ResponseEntity.ok(recipes);
    }

    /**
     * Endpoint 10: Busca recetas por ingrediente
     */
    @GetMapping("/search")
    @Operation(summary = "Search recipes by ingredient",
            description = "Find recipes that contain a specific ingredient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ingredient parameter")
    })
    public ResponseEntity<List<RecipeResponse>> searchRecipesByIngredient(
            @Parameter(description = "Ingredient to search for")
            @RequestParam String ingredient) {
        List<RecipeResponse> recipes = recipeService.searchRecipesByIngredient(ingredient);
        return ResponseEntity.ok(recipes);
    }

    /**
     * Endpoint 11: Elimina una receta
     */
    @DeleteMapping("/{consecutiveNumber}")
    @Operation(summary = "Delete recipe",
            description = "Delete a recipe by its consecutive number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    public ResponseEntity<MessageResponse> deleteRecipe(
            @Parameter(description = "Consecutive number of the recipe to delete")
            @PathVariable Long consecutiveNumber) {
        recipeService.deleteRecipe(consecutiveNumber);

        MessageResponse message = MessageResponse.builder()
                .message("Recipe deleted successfully")
                .timestamp(LocalDateTime.now().toString())
                .build();

        return ResponseEntity.ok(message);
    }

    /**
     * Endpoint 12: Actualiza una receta
     */
    @PutMapping("/{consecutiveNumber}")
    @Operation(summary = "Update recipe",
            description = "Update an existing recipe by its consecutive number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe updated successfully",
                    content = @Content(schema = @Schema(implementation = RecipeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Recipe not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<RecipeResponse> updateRecipe(
            @Parameter(description = "Consecutive number of the recipe to update")
            @PathVariable Long consecutiveNumber,
            @Valid @RequestBody UpdateRecipeRequest request) {
        RecipeResponse response = recipeService.updateRecipe(consecutiveNumber, request);
        return ResponseEntity.ok(response);
    }
}