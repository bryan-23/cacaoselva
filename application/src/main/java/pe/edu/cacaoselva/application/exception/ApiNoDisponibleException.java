package pe.edu.cacaoselva.application.exception;

public class ApiNoDisponibleException extends RuntimeException {

    public ApiNoDisponibleException(String message) {
        super(message);
    }

    public ApiNoDisponibleException(String message, Throwable cause) {
        super(message, cause);
    }
}