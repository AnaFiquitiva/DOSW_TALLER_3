package eci.edu.dosw.Taller.DOSW_TALLER_3;

import eci.edu.dosw.Taller.DOSW_TALLER_3.dto.*;
import eci.edu.dosw.Taller.DOSW_TALLER_3.exception.RecipeNotFoundException;
import eci.edu.dosw.Taller.DOSW_TALLER_3.exception.ValidationException;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas unitarias para MasterChef
 */
@ExtendWith(MockitoExtension.class)
class RecipeTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeMapper recipeMapper;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private Recipe sampleRecipe;
    private RecipeResponse sampleRecipeResponse;
    private ViewerRecipeRequest viewerRequest;

    /**
     * Configura los objetos de prueba comunes antes de cada método de prueba.
     * Inicializa una solicitud de receta de televidente, una entidad de receta
     * y su correspondiente DTO de respuesta para ser utilizados en los escenarios de prueba.
     */
    @BeforeEach
    void setUp() {
        viewerRequest = ViewerRecipeRequest.builder()
                .title("Arroz con leche")
                .ingredients(List.of("Arroz", "Leche", "Azúcar"))
                .preparationSteps(List.of("Poner a hervir la leche", "Añadir el arroz"))
                .chefName("Juan Televidente")
                .build();

        sampleRecipe = Recipe.builder()
                .id("123")
                .consecutiveNumber(1L)
                .title(viewerRequest.getTitle())
                .ingredients(viewerRequest.getIngredients())
                .preparationSteps(viewerRequest.getPreparationSteps())
                .chefName(viewerRequest.getChefName())
                .chefType(ChefType.VIEWER)
                .season(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        sampleRecipeResponse = RecipeResponse.builder()
                .id(sampleRecipe.getId())
                .consecutiveNumber(sampleRecipe.getConsecutiveNumber())
                .title(sampleRecipe.getTitle())
                .ingredients(sampleRecipe.getIngredients())
                .preparationSteps(sampleRecipe.getPreparationSteps())
                .chefName(sampleRecipe.getChefName())
                .chefType(sampleRecipe.getChefType())
                .season(sampleRecipe.getSeason())
                .createdAt(sampleRecipe.getCreatedAt())
                .updatedAt(sampleRecipe.getUpdatedAt())
                .build();
    }

    /**
     * A. Validar que se pueda registrar una receta.
     * Verifica que el resultado no es nulo y que los métodos del repositorio y mapeador fueron invocados.
     */
    @Test
    @DisplayName("A. Validar que se pueda registrar una receta")
    void validarQueSePuedaRegistrarUnaReceta() {
        when(recipeRepository.findTopByOrderByConsecutiveNumberDesc()).thenReturn(Optional.empty());
        when(recipeMapper.toEntity(any(ViewerRecipeRequest.class), anyLong())).thenReturn(sampleRecipe);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(sampleRecipe);
        when(recipeMapper.toResponse(any(Recipe.class))).thenReturn(sampleRecipeResponse);

        RecipeResponse result = recipeService.createViewerRecipe(viewerRequest);

        assertNotNull(result);
        assertEquals(sampleRecipeResponse.getConsecutiveNumber(), result.getConsecutiveNumber());
        assertEquals(sampleRecipeResponse.getTitle(), result.getTitle());
        assertEquals(ChefType.VIEWER, result.getChefType());

        verify(recipeRepository, times(1)).findTopByOrderByConsecutiveNumberDesc();
        verify(recipeMapper, times(1)).toEntity(viewerRequest, 1L);
        verify(recipeRepository, times(1)).save(sampleRecipe);
        verify(recipeMapper, times(1)).toResponse(sampleRecipe);
    }

    /**
     * B. Validar que la búsqueda por ingrediente devuelva resultados correctos.
     * Verifica que la lista de resultados no está vacía y contiene la receta esperada.
     */
    @Test
    @DisplayName("B. Validar que la búsqueda por ingrediente devuelva resultados correctos")
    void validarQueLaBusquedaPorIngredienteDevuelvaResultadosCorrectos() {
        String ingredient = "Arroz";
        List<Recipe> foundRecipes = List.of(sampleRecipe);
        List<RecipeResponse> expectedResponses = List.of(sampleRecipeResponse);

        when(recipeRepository.findByIngredientsContaining(anyString())).thenReturn(foundRecipes);
        when(recipeMapper.toResponse(sampleRecipe)).thenReturn(sampleRecipeResponse);

        List<RecipeResponse> result = recipeService.searchRecipesByIngredient(ingredient);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(expectedResponses.get(0).getTitle(), result.get(0).getTitle());

        verify(recipeRepository, times(1)).findByIngredientsContaining(ingredient);
        verify(recipeMapper, times(1)).toResponse(sampleRecipe);
    }

    /**
     * C. Validar que se devuelva error si se consulta una receta inexistente.
     * Verifica que se lanza la excepción correcta con el mensaje descriptivo y que el mapeador
     * no es invocado, ya que no se encontró la entidad.
     */
    @Test
    @DisplayName("C. Validar que se devuelva error si se consulta una receta inexistente")
    void validarQueSeDevuelvaErrorSiSeConsultaUnaRecetaInexistente() {
        Long nonExistentId = 999L;
        when(recipeRepository.findByConsecutiveNumber(nonExistentId)).thenReturn(Optional.empty());

        RecipeNotFoundException exception = assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.getRecipeByConsecutiveNumber(nonExistentId);
        });

        assertEquals("Recipe with consecutive number 999 not found", exception.getMessage());
        verify(recipeRepository, times(1)).findByConsecutiveNumber(nonExistentId);
        verify(recipeMapper, never()).toResponse(any(Recipe.class));
    }


    /**
     * Prueba que se lanza una {@link ValidationException} al buscar recetas con un parámetro de ingrediente vacío.
     */
    @Test
    @DisplayName("Buscar recetas con ingrediente vacío lanza ValidationException")
    void testSearchRecipesByIngredient_EmptyIngredient() {
        String emptyIngredient = "   ";

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            recipeService.searchRecipesByIngredient(emptyIngredient);
        });

        assertEquals("Ingredient cannot be empty", exception.getMessage());
        verify(recipeRepository, never()).findByIngredientsContaining(anyString());
    }

    /**
     * Prueba que una receta existente se puede eliminar exitosamente.
     */
    @Test
    @DisplayName("Eliminar una receta existente exitosamente")
    void testDeleteRecipe_Success() {
        Long existingId = 1L;
        when(recipeRepository.findByConsecutiveNumber(existingId)).thenReturn(Optional.of(sampleRecipe));
        doNothing().when(recipeRepository).delete(sampleRecipe);

        assertDoesNotThrow(() -> recipeService.deleteRecipe(existingId));

        verify(recipeRepository, times(1)).findByConsecutiveNumber(existingId);
        verify(recipeRepository, times(1)).delete(sampleRecipe);
    }

    /**
     * Prueba que se lanza una {@link RecipeNotFoundException} al intentar eliminar una receta que no existe.
     */
    @Test
    @DisplayName("Eliminar una receta inexistente lanza RecipeNotFoundException")
    void testDeleteRecipe_NotFound() {
        Long nonExistentId = 999L;
        when(recipeRepository.findByConsecutiveNumber(nonExistentId)).thenReturn(Optional.empty());

        RecipeNotFoundException exception = assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.deleteRecipe(nonExistentId);
        });

        assertEquals("Recipe with consecutive number 999 not found", exception.getMessage());
        verify(recipeRepository, times(1)).findByConsecutiveNumber(nonExistentId);
        verify(recipeRepository, never()).delete(any(Recipe.class));
    }
}