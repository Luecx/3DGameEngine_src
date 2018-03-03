package engine.core.exceptions;

/**
 * Created by Luecx on 20.12.2016.
 */
public class DivergenceFormatException extends Exception{
    public DivergenceFormatException() {
    }

    public DivergenceFormatException(String message) {
        super(message);
    }

    public DivergenceFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public DivergenceFormatException(Throwable cause) {
        super(cause);
    }

    public DivergenceFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
