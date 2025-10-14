package eci.edu.dosw.Taller.DOSW_TALLER_3;
import eci.edu.dosw.Taller.DOSW_TALLER_3.dto.RecipeResponse;
import eci.edu.dosw.Taller.DOSW_TALLER_3.dto.ViewerRecipeRequest;
import eci.edu.dosw.Taller.DOSW_TALLER_3.exception.RecipeNotFoundException;
import eci.edu.dosw.Taller.DOSW_TALLER_3.model.ChefType;
import eci.edu.dosw.Taller.DOSW_TALLER_3.model.Recipe;
import eci.edu.dosw.Taller.DOSW_TALLER_3.repository.RecipeRepository;
import eci.edu.dosw.Taller.DOSW_TALLER_3.service.impl.RecipeServiceImpl;
import eci.edu.dosw.Taller.DOSW_TALLER_3.util.RecipeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para RecipeService
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Recipe Service Unit Tests")
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeMapper recipeMapper;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private Recipe sampleRecipe;
    private RecipeResponse sampleRecipeResponse;
    private ViewerRecipeRequest sampleViewerRequest;

    @BeforeEach
    void setUp() {
        // Preparar datos de prueba
        sampleRecipe = Recipe.builder()
                .id("123abc")
                .consecutiveNumber(1L)
                .title("Pasta Carbonara")
                .ingredients(Arrays.asList("Pasta", "Eggs", "Bacon", "Parmesan", "Black Pepper"))
                .preparationSteps(Arrays.asList(
                        "Boil pasta",
                        "Cook bacon",
                        "Mix eggs with cheese",
                        "Combine all ingredients"
                ))
                .chefName("Juan Pérez")
                .chefType(ChefType.VIEWER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        sampleRecipeResponse = RecipeResponse.builder()
                .id("123abc")
                .consecutiveNumber(1L)
                .title("Pasta Carbonara")
                .ingredients(sampleRecipe.getIngredients())
                .preparationSteps(sampleRecipe.getPreparationSteps())
                .chefName("Juan Pérez")
                .chefType(ChefType.VIEWER)
                .createdAt(sampleRecipe.getCreatedAt())
                .updatedAt(sampleRecipe.getUpdatedAt())
                .build();

        sampleViewerRequest = ViewerRecipeRequest.builder()
                .title("Pasta Carbonara")
                .ingredients(Arrays.asList("Pasta", "Eggs", "Bacon", "Parmesan", "Black Pepper"))
                .preparationSteps(Arrays.asList(
                        "Boil pasta",
                        "Cook bacon",
                        "Mix eggs with cheese",
                        "Combine all ingredients"
                ))
                .chefName("Juan Pérez")
                .build();
    }

    /**
     * Test A: Validar que se pueda registrar una receta
     */
    @Test
    @DisplayName("Should successfully create a viewer recipe")
    void testCreateViewerRecipe_Success() {
        // Arrange
        when(recipeRepository.findTopByOrderByConsecutiveNumberDesc())
                .thenReturn(Optional.empty());
        when(recipeMapper.toEntity(any(ViewerRecipeRequest.class), anyLong()))
                .thenReturn(sampleRecipe);
        when(recipeRepository.save(any(Recipe.class)))
                .thenReturn(sampleRecipe);
        when(recipeMapper.toResponse(any(Recipe.class)))
                .thenReturn(sampleRecipeResponse);

        // Act
        RecipeResponse result = recipeService.createViewerRecipe(sampleViewerRequest);

        // Assert
        assertNotNull(result, "Recipe response should not be null");
        assertEquals("Pasta Carbonara", result.getTitle(), "Recipe title should match");
        assertEquals(1L, result.getConsecutiveNumber(), "Consecutive number should be 1");
        assertEquals(ChefType.VIEWER, result.getChefType(), "Chef type should be VIEWER");
        assertEquals("Juan Pérez", result.getChefName(), "Chef name should match");
        assertEquals(5, result.getIngredients().size(), "Should have 5 ingredients");

        // Verify interactions
        verify(recipeRepository, times(1)).findTopByOrderByConsecutiveNumberDesc();
        verify(recipeMapper, times(1)).toEntity(any(ViewerRecipeRequest.class), eq(1L));
        verify(recipeRepository, times(1)).save(any(Recipe.class));
        verify(recipeMapper, times(1)).toResponse(any(Recipe.class));
    }

    /**
     * Test B: Validar que la búsqueda por ingrediente devuelva resultados correctos
     */
    @Test
    @DisplayName("Should return recipes containing the specified ingredient")
    void testSearchRecipesByIngredient_ReturnsCorrectResults() {
        // Arrange
        String searchIngredient = "Eggs";

        Recipe recipe1 = Recipe.builder()
                .id("1")
                .consecutiveNumber(1L)
                .title("Pasta Carbonara")
                .ingredients(Arrays.asList("Pasta", "Eggs", "Bacon"))
                .chefType(ChefType.VIEWER)
                .build();

        Recipe recipe2 = Recipe.builder()
                .id("2")
                .consecutiveNumber(2L)
                .title("Spanish Omelette")
                .ingredients(Arrays.asList("Eggs", "Potatoes", "Onion"))
                .chefType(ChefType.CONTESTANT)
                .season(3)
                .build();

        List<Recipe> expectedRecipes = Arrays.asList(recipe1, recipe2);

        RecipeResponse response1 = RecipeResponse.builder()
                .id("1")
                .title("Pasta Carbonara")
                .ingredients(recipe1.getIngredients())
                .build();

        RecipeResponse response2 = RecipeResponse.builder()
                .id("2")
                .title("Spanish Omelette")
                .ingredients(recipe2.getIngredients())
                .build();

        when(recipeRepository.findByIngredientsContaining(searchIngredient))
                .thenReturn(expectedRecipes);
        when(recipeMapper.toResponse(recipe1)).thenReturn(response1);
        when(recipeMapper.toResponse(recipe2)).thenReturn(response2);

        // Act
        List<RecipeResponse> results = recipeService.searchRecipesByIngredient(searchIngredient);

        // Assert
        assertNotNull(results, "Results should not be null");
        assertEquals(2, results.size(), "Should return 2 recipes");
        assertTrue(results.stream().anyMatch(r -> r.getTitle().equals("Pasta Carbonara")),
                "Should contain Pasta Carbonara");
        assertTrue(results.stream().anyMatch(r -> r.getTitle().equals("Spanish Omelette")),
                "Should contain Spanish Omelette");

        // Verify all recipes contain the ingredient
        results.forEach(recipe ->
                assertTrue(recipe.getIngredients().stream()
                                .anyMatch(ing -> ing.toLowerCase().contains(searchIngredient.toLowerCase())),
                        "Each recipe should contain the searched ingredient")
        );

        // Verify interactions
        verify(recipeRepository, times(1)).findByIngredientsContaining(searchIngredient);
        verify(recipeMapper, times(2)).toResponse(any(Recipe.class));
    }

    /**
     * Test C: Validar que se devuelva error si se consulta una receta inexistente
     */
    @Test
    @DisplayName("Should throw RecipeNotFoundException when recipe does not exist")
    void testGetRecipeByConsecutiveNumber_NotFound() {
        // Arrange
        Long nonExistentNumber = 999L;
        when(recipeRepository.findByConsecutiveNumber(nonExistentNumber))
                .thenReturn(Optional.empty());

        // Act & Assert
        RecipeNotFoundException exception = assertThrows(
                RecipeNotFoundException.class,
                () -> recipeService.getRecipeByConsecutiveNumber(nonExistentNumber),
                "Should throw RecipeNotFoundException"
        );

        // Verify exception message
        assertTrue(exception.getMessage().contains("999"),
                "Exception message should contain the consecutive number");
        assertTrue(exception.getMessage().toLowerCase().contains("not found"),
                "Exception message should indicate recipe was not found");

        // Verify interactions
        verify(recipeRepository, times(1)).findByConsecutiveNumber(nonExistentNumber);
        verify(recipeMapper, never()).toResponse(any(Recipe.class));
    }

    /**
     * Test adicional: Validar generación de número consecutivo
     */
    @Test
    @DisplayName("Should generate consecutive numbers correctly")
    void testConsecutiveNumberGeneration() {
        // Arrange
        Recipe lastRecipe = Recipe.builder()
                .consecutiveNumber(5L)
                .build();

        when(recipeRepository.findTopByOrderByConsecutiveNumberDesc())
                .thenReturn(Optional.of(lastRecipe));
        when(recipeMapper.toEntity(any(ViewerRecipeRequest.class), eq(6L)))
                .thenReturn(sampleRecipe);
        when(recipeRepository.save(any(Recipe.class)))
                .thenReturn(sampleRecipe);
        when(recipeMapper.toResponse(any(Recipe.class)))
                .thenReturn(sampleRecipeResponse);

        // Act
        recipeService.createViewerRecipe(sampleViewerRequest);

        // Assert
        verify(recipeMapper, times(1)).toEntity(any(ViewerRecipeRequest.class), eq(6L));
    }

    /**
     * Test adicional: Validar obtención de todas las recetas
     */
    @Test
    @DisplayName("Should return all recipes")
    void testGetAllRecipes_Success() {
        // Arrange
        List<Recipe> recipes = Arrays.asList(sampleRecipe, sampleRecipe);
        when(recipeRepository.findAll()).thenReturn(recipes);
        when(recipeMapper.toResponse(any(Recipe.class)))
                .thenReturn(sampleRecipeResponse);

        // Act
        List<RecipeResponse> results = recipeService.getAllRecipes();

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size());
        verify(recipeRepository, times(1)).findAll();
        verify(recipeMapper, times(2)).toResponse(any(Recipe.class));
    }

    /**
     * Test adicional: Validar filtrado por tipo de chef
     */
    @Test
    @DisplayName("Should return only contestant recipes")
    void testGetContestantRecipes_Success() {
        // Arrange
        Recipe contestantRecipe = Recipe.builder()
                .consecutiveNumber(2L)
                .title("Gourmet Dish")
                .chefType(ChefType.CONTESTANT)
                .season(5)
                .build();

        when(recipeRepository.findByChefType(ChefType.CONTESTANT))
                .thenReturn(Collections.singletonList(contestantRecipe));
        when(recipeMapper.toResponse(any(Recipe.class)))
                .thenReturn(sampleRecipeResponse);

        // Act
        List<RecipeResponse> results = recipeService.getContestantRecipes();

        // Assert
        assertNotNull(results);
        assertFalse(results.isEmpty());
        verify(recipeRepository, times(1)).findByChefType(ChefType.CONTESTANT);
    }
}