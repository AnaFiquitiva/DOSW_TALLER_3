package eci.edu.dosw.Taller.DOSW_TALLER_3.service.interfaces;

import eci.edu.dosw.Taller.DOSW_TALLER_3.dto.*;

import java.util.List;

/**
 * Interface que define los servicios de gestión de recetas
 */
public interface RecipeService {

    /**
     * Registra una nueva receta creada por un televidente
     * @param request datos de la receta del televidente
     * @return respuesta con la receta creada
     */
    RecipeResponse createViewerRecipe(ViewerRecipeRequest request);

    /**
     * Registra una nueva receta creada por un participante del programa
     * @param request datos de la receta del participante
     * @return respuesta con la receta creada
     */
    RecipeResponse createContestantRecipe(ContestantRecipeRequest request);

    /**
     * Registra una nueva receta creada por un chef jurado
     * @param request datos de la receta del chef
     * @return respuesta con la receta creada
     */
    RecipeResponse createJudgeChefRecipe(JudgeChefRecipeRequest request);

    /**
     * Obtiene todas las recetas guardadas en el sistema
     * @return lista de todas las recetas
     */
    List<RecipeResponse> getAllRecipes();

    /**
     * Obtiene una receta específica por su número consecutivo
     * @param consecutiveNumber número consecutivo de la receta
     * @return respuesta con la receta encontrada
     */
    RecipeResponse getRecipeByConsecutiveNumber(Long consecutiveNumber);

    /**
     * Obtiene todas las recetas creadas por participantes del programa
     * @return lista de recetas de participantes
     */
    List<RecipeResponse> getContestantRecipes();

    /**
     * Obtiene todas las recetas creadas por televidentes
     * @return lista de recetas de televidentes
     */
    List<RecipeResponse> getViewerRecipes();

    /**
     * Obtiene todas las recetas creadas por chefs jurado
     * @return lista de recetas de chefs
     */
    List<RecipeResponse> getJudgeChefRecipes();

    /**
     * Obtiene todas las recetas de una temporada específica
     * @param season número de temporada
     * @return lista de recetas de esa temporada
     */
    List<RecipeResponse> getRecipesBySeason(Integer season);

    /**
     * Busca recetas que contengan un ingrediente específico
     * @param ingredient nombre del ingrediente a buscar
     * @return lista de recetas que contienen ese ingrediente
     */
    List<RecipeResponse> searchRecipesByIngredient(String ingredient);

    /**
     * Actualiza una receta existente
     * @param consecutiveNumber número consecutivo de la receta a actualizar
     * @param request datos actualizados de la receta
     * @return respuesta con la receta actualizada
     */
    RecipeResponse updateRecipe(Long consecutiveNumber, UpdateRecipeRequest request);

    /**
     * Elimina una receta del sistema
     * @param consecutiveNumber número consecutivo de la receta a eliminar
     */
    void deleteRecipe(Long consecutiveNumber);
}


