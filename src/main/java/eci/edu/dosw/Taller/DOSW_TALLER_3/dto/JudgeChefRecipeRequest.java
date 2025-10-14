package eci.edu.dosw.Taller.DOSW_TALLER_3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * DTO para crear una receta de un chef jurado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgeChefRecipeRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotEmpty(message = "Ingredients list cannot be empty")
    private List<String> ingredients;

    @NotEmpty(message = "Preparation steps cannot be empty")
    private List<String> preparationSteps;

    @NotBlank(message = "Chef name is required")
    private String chefName;
}
