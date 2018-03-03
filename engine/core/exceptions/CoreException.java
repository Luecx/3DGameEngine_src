package engine.core.exceptions;


/**
 * Created by Luecx on 02.01.2017.
 */
public class CoreException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 333094036624967709L;

	public CoreException() {
    }

    public CoreException(String message) {
        super(message);
    }

    public CoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreException(Throwable cause) {
        super(cause);
    }

    public CoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
