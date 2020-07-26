package by.itechart.logic.exception;

public class LoadPropertiesException extends Exception {

    public LoadPropertiesException() {
    }

    public LoadPropertiesException(String message) {
        super(message);
    }

    public LoadPropertiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadPropertiesException(Throwable cause) {
        super(cause);
    }

    public LoadPropertiesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
