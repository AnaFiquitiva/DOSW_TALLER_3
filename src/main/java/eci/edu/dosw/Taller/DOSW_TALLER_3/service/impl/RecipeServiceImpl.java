package eci.edu.dosw.Taller.DOSW_TALLER_3.service.impl;
import eci.edu.dosw.Taller.DOSW_TALLER_3.model.*;
import eci.edu.dosw.Taller.DOSW_TALLER_3.repository.*;
import eci.edu.dosw.Taller.DOSW_TALLER_3.service.interfaces.*;
import eci.edu.dosw.Taller.DOSW_TALLER_3.dto.*;
import eci.edu.dosw.Taller.DOSW_TALLER_3.util.*;
import eci.edu.dosw.Taller.DOSW_TALLER_3.exception.*;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de recetas
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    /**
     * Genera el siguiente número consecutivo para una receta
     * @return siguiente número consecutivo disponible
     */
    private Long generateConsecutiveNumber() {
        return recipeRepository.findTopByOrderByConsecutiveNumberDesc()
                .map(recipe -> recipe.getConsecutiveNumber() + 1)
                .orElse(1L);
    }

    @Override
    @Transactional
    public RecipeResponse createViewerRecipe(ViewerRecipeRequest request) {
        log.info("Creating viewer recipe with title: {}", request.getTitle());

        Long consecutiveNumber = generateConsecutiveNumber();
        Recipe recipe = recipeMapper.toEntity(request, consecutiveNumber);
        Recipe savedRecipe = recipeRepository.save(recipe);

        log.info("Viewer recipe created successfully with consecutive number: {}", consecutiveNumber);
        return recipeMapper.toResponse(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeResponse createContestantRecipe(ContestantRecipeRequest request) {
        log.info("Creating contestant recipe with title: {} for season: {}",
                request.getTitle(), request.getSeason());

        if (request.getSeason() == null || request.getSeason() <= 0) {
            throw new ValidationException("Season must be a positive number for contestant recipes");
        }

        Long consecutiveNumber = generateConsecutiveNumber();
        Recipe recipe = recipeMapper.toEntity(request, consecutiveNumber);
        Recipe savedRecipe = recipeRepository.save(recipe);

        log.info("Contestant recipe created successfully with consecutive number: {}", consecutiveNumber);
        return recipeMapper.toResponse(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeResponse createJudgeChefRecipe(JudgeChefRecipeRequest request) {
        log.info("Creating judge chef recipe with title: {}", request.getTitle());

        Long consecutiveNumber = generateConsecutiveNumber();
        Recipe recipe = recipeMapper.toEntity(request, consecutiveNumber);
        Recipe savedRecipe = recipeRepository.save(recipe);

        log.info("Judge chef recipe created successfully with consecutive number: {}", consecutiveNumber);
        return recipeMapper.toResponse(savedRecipe);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeResponse> getAllRecipes() {
        log.info("Fetching all recipes");

        List<Recipe> recipes = recipeRepository.findAll();
        log.info("Found {} recipes", recipes.size());

        return recipes.stream()
                .map(recipeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeResponse getRecipeByConsecutiveNumber(Long consecutiveNumber) {
        log.info("Fetching recipe with consecutive number: {}", consecutiveNumber);

        Recipe recipe = recipeRepository.findByConsecutiveNumber(consecutiveNumber)
                .orElseThrow(() -> new RecipeNotFoundException(consecutiveNumber));

        return recipeMapper.toResponse(recipe);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeResponse> getContestantRecipes() {
        log.info("Fetching all contestant recipes");

        List<Recipe> recipes = recipeRepository.findByChefType(ChefType.CONTESTANT);
        log.info("Found {} contestant recipes", recipes.size());

        return recipes.stream()
                .map(recipeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeResponse> getViewerRecipes() {
        log.info("Fetching all viewer recipes");

        List<Recipe> recipes = recipeRepository.findByChefType(ChefType.VIEWER);
        log.info("Found {} viewer recipes", recipes.size());

        return recipes.stream()
                .map(recipeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeResponse> getJudgeChefRecipes() {
        log.info("Fetching all judge chef recipes");

        List<Recipe> recipes = recipeRepository.findByChefType(ChefType.JUDGE_CHEF);
        log.info("Found {} judge chef recipes", recipes.size());

        return recipes.stream()
                .map(recipeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeResponse> getRecipesBySeason(Integer season) {
        log.info("Fetching recipes for season: {}", season);

        if (season == null || season <= 0) {
            throw new ValidationException("Season must be a positive number");
        }

        List<Recipe> recipes = recipeRepository.findBySeason(season);
        log.info("Found {} recipes for season {}", recipes.size(), season);

        return recipes.stream()
                .map(recipeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeResponse> searchRecipesByIngredient(String ingredient) {
        log.info("Searching recipes with ingredient: {}", ingredient);

        if (ingredient == null || ingredient.isBlank()) {
            throw new ValidationException("Ingredient cannot be empty");
        }

        List<Recipe> recipes = recipeRepository.findByIngredientsContaining(ingredient.trim());
        log.info("Found {} recipes with ingredient '{}'", recipes.size(), ingredient);

        return recipes.stream()
                .map(recipeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RecipeResponse updateRecipe(Long consecutiveNumber, UpdateRecipeRequest request) {
        log.info("Updating recipe with consecutive number: {}", consecutiveNumber);

        Recipe recipe = recipeRepository.findByConsecutiveNumber(consecutiveNumber)
                .orElseThrow(() -> new RecipeNotFoundException(consecutiveNumber));

        recipeMapper.updateEntity(recipe, request);
        Recipe updatedRecipe = recipeRepository.save(recipe);

        log.info("Recipe {} updated successfully", consecutiveNumber);
        return recipeMapper.toResponse(updatedRecipe);
    }

    @Override
    @Transactional
    public void deleteRecipe(Long consecutiveNumber) {
        log.info("Deleting recipe with consecutive number: {}", consecutiveNumber);

        Recipe recipe = recipeRepository.findByConsecutiveNumber(consecutiveNumber)
                .orElseThrow(() -> new RecipeNotFoundException(consecutiveNumber));

        recipeRepository.delete(recipe);
        log.info("Recipe {} deleted successfully", consecutiveNumber);
    }
}