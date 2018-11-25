package pocketestore.infrastructure.exceptions;

public class BusinessException extends RuntimeException {
    private String message;

    public BusinessException(String message) {
        super(message);
    }
}
