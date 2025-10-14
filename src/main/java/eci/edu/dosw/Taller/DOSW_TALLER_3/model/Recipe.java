package eci.edu.dosw.Taller.DOSW_TALLER_3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad que representa una receta en el sistema MasterChef Celebrity.
 * Puede ser creada por televidentes, participantes del programa o chefs jurado.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recipes")
public class Recipe {

    /**
     * Identificador único de la receta en MongoDB
     */
    @Id
    private String id;

    /**
     * Número consecutivo de la receta para fácil referencia
     */
    private Long consecutiveNumber;

    /**
     * Título de la receta
     */
    private String title;

    /**
     * Lista de ingredientes necesarios para preparar la receta
     */
    private List<String> ingredients;

    /**
     * Pasos detallados de preparación de la receta
     */
    private List<String> preparationSteps;

    /**
     * Nombre del chef/persona que creó la receta
     */
    private String chefName;

    /**
     * Tipo de creador de la receta: VIEWER (televidente), CONTESTANT (participante), JUDGE_CHEF (chef jurado)
     */
    private ChefType chefType;

    /**
     * Temporada en la que apareció la receta (solo aplica para participantes)
     */
    private Integer season;

    /**
     * Fecha de creación de la receta
     */
    private LocalDateTime createdAt;

    /**
     * Fecha de última actualización de la receta
     */
    private LocalDateTime updatedAt;
}