package eci.edu.dosw.Taller.DOSW_TALLER_3.dto;
import eci.edu.dosw.Taller.DOSW_TALLER_3.model.ChefType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta que contiene toda la información de una receta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponse {

    private String id;
    private Long consecutiveNumber;
    private String title;
    private List<String> ingredients;
    private List<String> preparationSteps;
    private String chefName;
    private ChefType chefType;
    private Integer season;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

