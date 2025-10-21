package eci.edu.dosw.Taller.DOSW_TALLER_3.exception;

/**
 * Excepción lanzada cuando hay un error de validación
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}