package eci.edu.dosw.Taller.DOSW_TALLER_3.repository;

import eci.edu.dosw.Taller.DOSW_TALLER_3.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para operaciones CRUD de recetas en MongoDB
 */
@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {

    /**
     * Encuentra una receta por su número consecutivo
     * @param consecutiveNumber número consecutivo de la receta
     * @return Optional con la receta si existe
     */
    Optional<Recipe> findByConsecutiveNumber(Long consecutiveNumber);

    /**
     * Encuentra todas las recetas creadas por un tipo específico de chef
     * @param chefType tipo de chef (VIEWER, CONTESTANT, JUDGE_CHEF)
     * @return lista de recetas del tipo especificado
     */
    List<Recipe> findByChefType(ChefType chefType);

    /**
     * Encuentra todas las recetas de una temporada específica
     * @param season número de temporada
     * @return lista de recetas de esa temporada
     */
    List<Recipe> findBySeason(Integer season);

    /**
     * Encuentra recetas que contengan un ingrediente específico (búsqueda case-insensitive)
     * @param ingredient ingrediente a buscar
     * @return lista de recetas que contienen ese ingrediente
     */
    @Query("{ 'ingredients': { $regex: ?0, $options: 'i' } }")
    List<Recipe> findByIngredientsContaining(String ingredient);

    /**
     * Encuentra el último número consecutivo usado
     * @return Optional con la receta que tiene el mayor número consecutivo
     */
    Optional<Recipe> findTopByOrderByConsecutiveNumberDesc();

    /**
     * Verifica si existe una receta con un número consecutivo específico
     * @param consecutiveNumber número consecutivo a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByConsecutiveNumber(Long consecutiveNumber);
}
