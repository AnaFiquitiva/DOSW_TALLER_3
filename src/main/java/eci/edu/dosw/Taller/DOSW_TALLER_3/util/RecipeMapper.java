package eci.edu.dosw.Taller.DOSW_TALLER_3.util;

import eci.edu.dosw.Taller.DOSW_TALLER_3.dto.*;
import eci.edu.dosw.Taller.DOSW_TALLER_3.model.ChefType;
import eci.edu.dosw.Taller.DOSW_TALLER_3.model.Recipe;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Utilidad para mapear entre entidades Recipe y DTOs
 */
@Component
public class RecipeMapper {

    /**
     * Convierte una entidad Recipe a RecipeResponse DTO
     * @param recipe entidad Recipe
     * @return RecipeResponse DTO
     */
    public RecipeResponse toResponse(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        return RecipeResponse.builder()
                .id(recipe.getId())
                .consecutiveNumber(recipe.getConsecutiveNumber())
                .title(recipe.getTitle())
                .ingredients(recipe.getIngredients())
                .preparationSteps(recipe.getPreparationSteps())
                .chefName(recipe.getChefName())
                .chefType(recipe.getChefType())
                .season(recipe.getSeason())
                .createdAt(recipe.getCreatedAt())
                .updatedAt(recipe.getUpdatedAt())
                .build();
    }

    /**
     * Convierte un ViewerRecipeRequest a una entidad Recipe
     * @param request DTO de solicitud de televidente
     * @param consecutiveNumber número consecutivo asignado
     * @return entidad Recipe
     */
    public Recipe toEntity(ViewerRecipeRequest request, Long consecutiveNumber) {
        LocalDateTime now = LocalDateTime.now();

        return Recipe.builder()
                .consecutiveNumber(consecutiveNumber)
                .title(request.getTitle())
                .ingredients(request.getIngredients())
                .preparationSteps(request.getPreparationSteps())
                .chefName(request.getChefName())
                .chefType(ChefType.VIEWER)
                .season(null)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    /**
     * Convierte un ContestantRecipeRequest a una entidad Recipe
     * @param request DTO de solicitud de participante
     * @param consecutiveNumber número consecutivo asignado
     * @return entidad Recipe
     */
    public Recipe toEntity(ContestantRecipeRequest request, Long consecutiveNumber) {
        LocalDateTime now = LocalDateTime.now();

        return Recipe.builder()
                .consecutiveNumber(consecutiveNumber)
                .title(request.getTitle())
                .ingredients(request.getIngredients())
                .preparationSteps(request.getPreparationSteps())
                .chefName(request.getChefName())
                .chefType(ChefType.CONTESTANT)
                .season(request.getSeason())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    /**
     * Convierte un JudgeChefRecipeRequest a una entidad Recipe
     * @param request DTO de solicitud de chef jurado
     * @param consecutiveNumber número consecutivo asignado
     * @return entidad Recipe
     */
    public Recipe toEntity(JudgeChefRecipeRequest request, Long consecutiveNumber) {
        LocalDateTime now = LocalDateTime.now();

        return Recipe.builder()
                .consecutiveNumber(consecutiveNumber)
                .title(request.getTitle())
                .ingredients(request.getIngredients())
                .preparationSteps(request.getPreparationSteps())
                .chefName(request.getChefName())
                .chefType(ChefType.JUDGE_CHEF)
                .season(null)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    /**
     * Actualiza una entidad Recipe existente con los datos de UpdateRecipeRequest
     * @param recipe entidad Recipe a actualizar
     * @param request DTO con los datos de actualización
     */
    public void updateEntity(Recipe recipe, UpdateRecipeRequest request) {
        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            recipe.setTitle(request.getTitle());
        }

        if (request.getIngredients() != null && !request.getIngredients().isEmpty()) {
            recipe.setIngredients(request.getIngredients());
        }

        if (request.getPreparationSteps() != null && !request.getPreparationSteps().isEmpty()) {
            recipe.setPreparationSteps(request.getPreparationSteps());
        }

        if (request.getChefName() != null && !request.getChefName().isBlank()) {
            recipe.setChefName(request.getChefName());
        }

        // Solo actualizar la temporada si la receta es de un participante
        if (recipe.getChefType() == ChefType.CONTESTANT && request.getSeason() != null) {
            recipe.setSeason(request.getSeason());
        }

        recipe.setUpdatedAt(LocalDateTime.now());
    }
}