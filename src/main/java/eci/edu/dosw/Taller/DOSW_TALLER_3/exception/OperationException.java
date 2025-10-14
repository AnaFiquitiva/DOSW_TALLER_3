package eci.edu.dosw.Taller.DOSW_TALLER_3.exception;

/**
 * Excepción lanzada cuando hay un error en la operación
 */
public class OperationException extends RuntimeException {

    public OperationException(String message) {
        super(message);
    }

    public OperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
