package eci.edu.dosw.Taller.DOSW_TALLER_3.exception;
/**
        * Excepción lanzada cuando no se encuentra una receta
 */
public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(String message) {
        super(message);
    }

    public RecipeNotFoundException(Long consecutiveNumber) {
        super(String.format("Recipe with consecutive number %d not found", consecutiveNumber));
    }

    public RecipeNotFoundException(String field, String value) {
        super(String.format("Recipe with %s '%s' not found", field, value));
    }
}