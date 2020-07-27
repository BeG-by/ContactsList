package by.itechart.web.exception;

public class RequestParseException extends Exception {

    public RequestParseException() {
        super();
    }

    public RequestParseException(String message) {
        super(message);
    }

    public RequestParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestParseException(Throwable cause) {
        super(cause);
    }

    protected RequestParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
