package eci.edu.dosw.Taller.DOSW_TALLER_3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO genérico para actualizar una receta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecipeRequest {

    private String title;
    private List<String> ingredients;
    private List<String> preparationSteps;
    private String chefName;
    private Integer season;
}