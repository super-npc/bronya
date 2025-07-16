package bronya.admin.base.exception;

/**
 * 自定义异常
 */
public class CustomizeException extends RuntimeException {

    public CustomizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomizeException(String message) {
        super(message);
    }

}
